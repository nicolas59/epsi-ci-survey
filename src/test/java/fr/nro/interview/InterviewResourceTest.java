package fr.nro.interview;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

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