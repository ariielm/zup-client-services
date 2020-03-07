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
    void shouldReturnAllClientsPaginated() {
        given().port(port)
                .get("rs/clients")
                .then()
                .statusCode(OK.value())
                .contentType(HAL_JSON_VALUE)
                .body("_embedded.clientResourceList", notNullValue())
                .body("_links.self.href", endsWith("/rs/clients?page=0&size=20"))
                .body("page.size", is(20))
                .body("page.totalElements", greaterThanOrEqualTo(1))
                .body("page.totalPages", greaterThanOrEqualTo(1))
                .body("page.number", is(0));
    }

    @Test
    void shouldReturnCreatedWithLocationAndCreateClientWhenCreatingNewClient() {
        String location =
                given().port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(getFile("scenarios/post-client.json"))
                .post("rs/clients")
                .then()
                .statusCode(CREATED.value())
                .header(LOCATION, matchesRegex("/rs/clients/[0-9]*"))
                .extract()
                .header(LOCATION);

        given().port(port)
                .get(location)
                .then()
                .statusCode(OK.value())
                .contentType(HAL_JSON_VALUE)
                .body("name", is("Sargento Bigode"))
                .body("cpf", is("45365884000"))
                .body("birthDate", is("2020-03-06"))
                .body("postalCode", is("01001000"))
                .body("number", is(1))
                .body("complement", is("Apto. 417"))
                .body("_links.self.href", endsWith(location));

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
                .body("cpf", is("01234567890"))
                .body("birthDate", is("1960-10-31"))
                .body("postalCode", is("01001000"))
                .body("number", is(22))
                .body("complement", is("Biblioteca"))
                .body("_links.self.href", endsWith("/rs/clients/1"));
    }

    @Test
    void shouldReturnOkWithLocationAndUpdateClientWhenUpdatingClientThree() {
        String location = given()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(getFile("scenarios/put-client.json"))
                .put("rs/clients/3")
                .then()
                .statusCode(OK.value())
                .header(LOCATION, is("/rs/clients/3"))
                .extract()
                .header(LOCATION);

        given().port(port)
                .get("rs/clients/3")
                .then()
                .statusCode(OK.value())
                .contentType(HAL_JSON_VALUE)
                .body("id", is(3))
                .body("name", is("Tony Gourmet"))
                .body("cpf", is("27434439874"))
                .body("birthDate", is("2000-01-01"))
                .body("postalCode", is("01001000"))
                .body("number", is(1))
                .body("complement", is("Apto. 010"))
                .body("_links.self.href", endsWith(location));
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
    void shouldReturnBadRequestWhenCreatingClientWithInvalidCPF() {
        given().port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(getFile("scenarios/post-invalid-client-cpf.json"))
                .post("rs/clients")
                .then()
                .statusCode(BAD_REQUEST.value())
                .body("errors[0]", is("cpf: invalid Brazilian individual taxpayer registry number (CPF)"));
    }

    @Test
    void shouldReturnBadRequestWhenCreatingClientWithAlreadyExistingCPF() {
        given().port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(getFile("scenarios/post-invalid-client-existing-cpf.json"))
                .post("rs/clients")
                .then()
                .statusCode(BAD_REQUEST.value())
                .body("errors[0]", is("cpf: already exists"));
    }

    @Test
    void shouldReturnBadRequestWhenCreatingClientWithInvalidBirthDate() {
        given().port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(getFile("scenarios/post-invalid-client-birthDate.json"))
                .post("rs/clients")
                .then()
                .statusCode(BAD_REQUEST.value())
                .body("errors[0]", is("birthDate: must be before than today"));
    }

    private final File getFile(String path) {
        return new File(getClass().getClassLoader().getResource(path).getFile());
    }

}
