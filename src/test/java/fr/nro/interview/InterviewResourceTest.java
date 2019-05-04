package fr.nro.interview;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class InterviewResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/interview")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}