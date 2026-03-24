package lab10.api.base;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.lessThan;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ApiBaseTest {

    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;
    protected static String reqresApiKey;

    @BeforeClass(alwaysRun = true)
    public void setUpBaseSpecs() {
        if (requestSpec == null) {
            reqresApiKey = System.getProperty("reqres.api.key");
            if (reqresApiKey == null || reqresApiKey.isBlank()) {
                reqresApiKey = System.getenv("REQRES_API_KEY");
            }

            RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                    .setBaseUri("https://reqres.in")
                    .setContentType(ContentType.JSON)
                    .addFilter(new RequestLoggingFilter())
                    .addFilter(new ResponseLoggingFilter());

            if (reqresApiKey != null && !reqresApiKey.isBlank()) {
                requestSpecBuilder.addHeader("x-api-key", reqresApiKey);
            }

            requestSpec = requestSpecBuilder.build();
        }

        if (responseSpec == null) {
            responseSpec = new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .expectResponseTime(lessThan(3000L), TimeUnit.MILLISECONDS)
                    .build();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void requireApiKey() {
        if (reqresApiKey == null || reqresApiKey.isBlank()) {
            throw new SkipException("Missing Reqres API key. Set REQRES_API_KEY or -Dreqres.api.key to run bai2 tests.");
        }
    }
}
