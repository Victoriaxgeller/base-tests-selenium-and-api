package mock;

import browser.Browser;
import com.github.javafaker.Faker;
import com.google.common.net.MediaType;
import mock.ui.MockService;
import mock.ui.ZapRegistrationPage;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.v123.fetch.Fetch;
import org.openqa.selenium.devtools.v123.fetch.model.HeaderEntry;
import org.openqa.selenium.devtools.v123.fetch.model.RequestId;
import org.openqa.selenium.devtools.v123.network.Network;
import org.openqa.selenium.devtools.v123.network.model.Request;
import org.openqa.selenium.remote.http.Contents;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistrationMockTest {
    Browser browser;

    @Test
    public void test() throws InterruptedException, URISyntaxException {
        openTestResource();
        initMock();
        fillFields();
    }

    @Test
    public void test2() {
        createResponseSecondTry();
        fillFields();
    }



    private void createResponseSecondTry() {
        browser = new Browser();
        DevTools devTools = ((HasDevTools) browser.getDriver()).getDevTools();
        devTools.createSession();
        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));

        devTools.addListener(Fetch.requestPaused(), requestConsumer ->
        {
            Request request = requestConsumer.getRequest();
            String currentRequestUrl = request.getUrl();

            if (currentRequestUrl.contains("wp-json/zap/api")) {

                String updateUrl = currentRequestUrl.replace("wp-json/zap/api", "seleznova-test");

                System.out.println("IF BLOCK");
                devTools.send(Fetch.continueRequest(
                        requestConsumer.getRequestId(),
                        Optional.of(updateUrl),
                        Optional.of(request.getMethod()),
                        request.getPostData(),
                        requestConsumer.getResponseHeaders(),
                        Optional.of(Boolean.valueOf(true))));

            } else {
                System.out.println("ELSE BLOCK");

                devTools.send(Fetch.continueRequest(
                        requestConsumer.getRequestId(),
                        Optional.of(currentRequestUrl.replace("wp-json/zap/api", "seleznova-test")),
                        Optional.of(request.getMethod()),
                        request.getPostData(),
                        requestConsumer.getResponseHeaders(),
                        Optional.of(Boolean.valueOf(true))));
            }
        });

        browser.openURL("https://www.zaptest.com/registration");

    }

    private void fillFields() {
        Faker faker = new Faker();
        String email = "taylor.wyman@hotmail.com";
//        String email = faker.internet().emailAddress();
        System.out.println(email);
        ZapRegistrationPage page = new ZapRegistrationPage(browser.getDriver());
        page.fillForm(faker.name().firstName(), faker.name().lastName(), email);
    }

    private void openTestResource() {
        browser = new Browser();
        browser.openURL("https://www.zaptest.com/registration");
    }

    private void initMock() throws URISyntaxException {
        MockService mockService = new MockService(browser.getDriver());
//        mockService.addMock(String.valueOf(new URI("https://www.zaptest.com/registration/wp-json/zap/api")), "{\"status\":0,\"error\":\"E-Mail is already registered, confirmation email was resent...\"}");
        mockService.addMock(String.valueOf(new URI("wp-json/zap/api")), "{\"status\":1,\"msg\":\"preregistered\"}");
        System.out.println(mockService);
    }

    private void createResponseFirstTry() {
        DevTools devTools = ((HasDevTools) browser.getDriver()).getDevTools();
        devTools.createSession();
        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
        devTools.addListener(Fetch.requestPaused(), requestConsumer ->
        {
            Request request = requestConsumer.getRequest();
            String currentRequestUrl = request.getUrl();

            if (currentRequestUrl.contains("wp-json/zap/api")) {
                String updateUrl = currentRequestUrl.replace("wp-json/zap/api", "seleznova-test");

                List<HeaderEntry> headerEntries = new ArrayList<>();
                devTools.send(Fetch.continueRequest(
                        requestConsumer.getRequestId(),
                        Optional.of(updateUrl),
                        Optional.of(request.getMethod()),
                        Optional.of(request.getMethod()),
                        Optional.empty(),
                        Optional.of(Boolean.valueOf(true))));

            } else {
                devTools.send(Fetch.continueRequest(
                        requestConsumer.getRequestId(),
                        Optional.of(currentRequestUrl),
                        Optional.of(request.getMethod()),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Boolean.valueOf(true))));
            }
        });
    }

//    private void validateResponse() {
//        DevTools devTools = ((HasDevTools) browser.getDriver()).getDevTools();
//
//        final RequestId[] requestIds = new RequestId[1];
//        devTools.send(Network.enable(Optional.of(100000000), Optional.empty(), Optional.empty()));
//        devTools.addListener(Network.responseReceived(), responseReceived -> {
////            if (responseReceived.getResponse().getUrl().contains("api.zoomcar.com")) {
//
//                System.out.println("WTF " +responseReceived.getResponse());
//                responseReceived.getResponse().getHeaders().toJson().forEach((k, v) -> System.out.println((k + ":" + v)));
////                requestIds[0] = responseReceived.getRequestId();
//                System.out.println("Response Body: \n" + devTools.send(Network.getResponseBody(responseReceived.getRequestId())).getBody() + "\n");
////            }
//        });
//    }

//    private void tryResponse() {
//        DevTools devTools = ((HasDevTools) browser.getDriver()).getDevTools();
//        devTools.createSession();
//        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
//
//        try (NetworkInterceptor ignored =
//                     new NetworkInterceptor(
//                             browser.getDriver(),
//                             Route.matching(req -> true)
//                                     .to(() -> req -> new HttpResponse()
//                                             .setStatus(201)
//                                             .addHeader("Content-Type", MediaType.HTML_UTF_8.toString())
//                                             .setContent(Contents.utf8String("Creamy, delicious cheese!"))))) {
//
//        }
//    }

}
