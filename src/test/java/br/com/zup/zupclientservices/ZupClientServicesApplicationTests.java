package br.com.zup.zupclientservices;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Sql(scripts = "/sql/clients.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ZupClientServicesApplicationTests {

    @LocalServerPort
    int port;

    @Test
    void shouldReturnAllClients() {
        given().port(port)
                .get("rs/clients")
                .then()
                .statusCode(OK.value())
                .contentType(HAL_JSON_VALUE);
    }

    @Test
    void shouldReturnCreatedWithLocationToTheCreatedClient() {
        given().port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(getFile("scenarios/post-client.json"))
                .post("rs/clients")
                .then()
                .statusCode(CREATED.value())
                .header(LOCATION, matchesRegex("/rs/clients/[0-9]*"));
    }

    @Test
    void shouldReturnClientWithIdOne() {
        given().port(port)
                .get("rs/clients/1")
                .then()
                .statusCode(OK.value())
                .contentType(HAL_JSON_VALUE)
                .body("id", is(1))
                .body("name", is("Dona Rosa"))
                .body("cpf", is("01234567890"));
    }

    @Test
    void shouldReturnOkWithLocationAndUpdateClientWhenUpdatingClientThree() {
        given().port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(getFile("scenarios/put-client.json"))
                .put("rs/clients/3")
                .then()
                .statusCode(OK.value())
                .header(LOCATION, is("/rs/clients/3"));

        given().port(port)
                .get("rs/clients/3")
                .then()
                .statusCode(OK.value())
                .contentType(HAL_JSON_VALUE)
                .body("id", is(3))
                .body("name", is("Tony Gourmet"))
                .body("cpf", is("98765432109"));
    }

    @Test
    void shouldDeleteClientWithIdTwo() {
        given().port(port)
                .delete("rs/clients/2")
                .then()
                .statusCode(NO_CONTENT.value());

        given().port(port)
                .get("rs/clients/2")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void shouldReturnNotFoundWhenGettingNonExistingClient() {
        given().port(port)
                .get("rs/clients/999")
                .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void shouldReturnBadRequestWhenCreatingClientWithInvalidData() {
        given().port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(getFile("scenarios/post-invalid-client.json"))
                .post("rs/clients")
                .then()
                .statusCode(BAD_REQUEST.value())
                .body("errors[0].field", is("cpf"))
                .body("errors[0].defaultMessage", is("must not be null"));
    }

    private final File getFile(String path) {
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }

}
