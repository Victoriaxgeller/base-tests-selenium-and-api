package listener;

import lombok.SneakyThrows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.Test;


public class TestListener extends TestListenerAdapter {

    private Logger logger = LoggerFactory.getLogger(TestListener.class);

    private static final String TEST_CLASS = "TEST CLASS: ",
            TEST = "TEST: ",
            DIVIDING_LINE = "____________________________________________________________",
            REASON = "FAILURE REASON: ",
            PLATFORM = "PLATFORM: ",
            BASE_URL = "BASE_URL: ";

    private String platform = "";
    private String baseUrl = "";

    public String getNameOfTest(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName();
    }

    @SneakyThrows
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("TEST STARTED" + DIVIDING_LINE + "\n" + "TEST NAME: " + getNameOfTest(result) + "\n" + TEST_CLASS + result.getTestClass().getName());


    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("TEST RESULT: FAILED " + getNameOfTest(result) + "\n" + REASON + result.getThrowable().getMessage() + "\n");

    }

    @SneakyThrows
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("TEST RESULT: SUCCESS " + getNameOfTest(result));
        System.out.println(TEST_CLASS + result.getTestClass().getName() + "\n");

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(TEST + result.getName() + "(" + result.getTestClass().getName() + ")" + DIVIDING_LINE + "SKIPPED " + "\n" +
                TEST_CLASS + result.getTestClass().getName() + "\n" +
                REASON + result.getThrowable().getMessage() + "\n" +
                PLATFORM + platform + "\n" +
                BASE_URL + baseUrl);
    }

    @Override
    public void onFinish(ITestContext result) {
        System.out.println("TEST FINISHED" + DIVIDING_LINE);
    }

}