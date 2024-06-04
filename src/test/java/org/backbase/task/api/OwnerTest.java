package org.backbase.task.api;

import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.backbase.task.BaseTest;
import org.backbase.task.apiServices.PetClinicService;
import org.backbase.task.dto.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


@Slf4j
public class OwnerTest extends BaseTest {

    private final PetClinicService petClinicService = new PetClinicService();

    @Test
    @Tag("API")
    @Feature("API")
    @Issue("TC-API-1")
    @DisplayName("API - Create a new owner without pets")
    public void createOwnerWithoutPets() {
        var owner = Owner.buildDefault();

        int id = petClinicService.addNewOwnerAndGetId(owner);

        var savedOwner = petClinicService.getOwnerById(id)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Owner.class);

        Assertions.assertTrue(savedOwner.equalsWithoutPets(owner),
                "Saved owner data doesn't match initial request");
    }


    @ParameterizedTest
    @MethodSource("createOwnerWithoutRequiredFieldsData")
    @Tag("API")
    @Feature("API")
    @Issue("TC-API-2")
    @DisplayName("API - Create a new owner without pets, with a missing required field")
    public void createOwnerWithoutRequiredFields(Owner owner) {
        petClinicService.addNewOwner(owner)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }


    @Test
    @Tag("API")
    @Feature("API")
    @Issue("TC-API-3")
    @DisplayName("API - Get list of owners")
    public void getListOfOwners() {
        var owners = petClinicService.getListOfOwners()
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Owner[].class);
        Assertions.assertTrue(owners.length > 0,
                "No owners returned");
    }
}
