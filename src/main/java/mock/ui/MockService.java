package mock.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;

import java.util.*;

import org.openqa.selenium.devtools.v123.network.Network;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;

import java.util.concurrent.ConcurrentHashMap;

import static org.openqa.selenium.remote.http.Contents.utf8String;

public class MockService {

    // DevTools instance for the current thread
    private ThreadLocal<DevTools> devTools = new ThreadLocal<>();

    // Network Interceptor instance for the current thread
    private ThreadLocal<NetworkInterceptor> interceptor = new ThreadLocal<>();

    // Map to hold mock responses based on parts of their URI
    private ConcurrentHashMap<String, String> mockResponses = new ConcurrentHashMap<>();

    // List to hold details of the requests. It's synchronized as it might be accessed by multiple threads.
    List<Map<String, String>> requestDetails = Collections.synchronizedList(new ArrayList<>());

    // Constructor to initialize DevTools and interceptor for the provided WebDriver
    public MockService(WebDriver driver) {
        devTools.set(((HasDevTools) driver).getDevTools());
        devTools.get().createSession();
        devTools.get().send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        interceptor.set(new NetworkInterceptor(driver, createRoute()));
    }

    // Create a route that intercepts the request if its URI contains a key from mockResponses
    private Route createRoute() {
        return Route.matching(req -> containsKey(req.getUri())).to(() -> req -> {
            String responseContent = getResponse(req.getUri());
            if (responseContent != null) {
                return new HttpResponse()
                        .setStatus(200)
                        .addHeader("Content-Type", "application/json")
                        .setContent(utf8String(responseContent));
            } else {
                return null;
            }
        });
    }

    // Check if the provided URI contains any key from mockResponses
    private boolean containsKey(String uri) {
        return mockResponses.keySet().stream().anyMatch(uri::contains);
    }

    // Retrieve the mock response associated with the provided URI, if exists
    private String getResponse(String uri) {
        return mockResponses.entrySet().stream()
                .filter(entry -> uri.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    // Add a new mock response to mockResponses
    public synchronized void addMock(String uriPart, String responseContent) {
        mockResponses.put(uriPart, responseContent);
    }

    // Clear all mock responses
    public synchronized void clearMocks() {
        mockResponses.clear();
    }

    // Clean-up method to close DevTools session, remove interceptors, and clear mocks for the current thread
    public void remove() {
        // Closes and removes the DevTools sessions associated with the current thread
        Optional.ofNullable(devTools.get()).ifPresent(dt -> {
            dt.close();
            devTools.remove();
        });

        // Removes the NetworkInterceptor associated with the current thread
        Optional.ofNullable(interceptor.get()).ifPresent(in -> {
            in.close();
            interceptor.remove();
        });

        // Clears all mock responses associated with the current thread
        clearMocks();
    }
}