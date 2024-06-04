package org.backbase.task.apiServices;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.backbase.task.config.Config;
import org.backbase.task.dto.Owner;
import org.junit.jupiter.api.Assertions;

import java.util.List;

@Slf4j
public class PetClinicService {

    private final RequestSpecification BASE_REQUEST = RestAssured.given()
            .log().all()
            .contentType(ContentType.JSON)
            .baseUri(Config.getInstance().getApiBaseUrl());

    private static final String OWNERS = "/owners";

    public PetClinicService() {
    }

    //// base methods

    public Response getListOfOwners() {
        return RestAssured.given()
                .spec(BASE_REQUEST)
                .get(OWNERS + "/list");
    }

    public Response addNewOwner(Owner owner) {
        log.info(owner.getFirstName());
        return RestAssured.given()
                .spec(BASE_REQUEST)
                .body(owner)
                .post(OWNERS);
    }

    public Response getOwnerById(int id) {
        return RestAssured.given()
                .spec(BASE_REQUEST)
                .pathParam("ownerId", id)
                .get(OWNERS + "/{ownerId}");
    }


    //// utility

    private List<Integer> getAllOwnerIds() {
        return getListOfOwners()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getList("id");
    }

    public int addNewOwnerAndGetId(Owner owner) {
        var initialIds = getAllOwnerIds();

        addNewOwner(owner)
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        var diffIds = getAllOwnerIds().stream()
                .filter(id -> !initialIds.contains(id))
                .toList();

        Assertions.assertEquals(1, diffIds.size(),
                "Expected exactly 1 new owner id after adding a new owner");

        return diffIds.getFirst();
    }
}
