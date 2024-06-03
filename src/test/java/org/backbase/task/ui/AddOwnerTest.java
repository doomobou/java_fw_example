package org.backbase.task.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.backbase.task.BaseUiTest;
import org.backbase.task.dto.Owner;
import org.backbase.task.pages.NewOwnerPage;
import org.backbase.task.pages.OwnersListPage;
import org.backbase.task.utils.WaitUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AddOwnerTest extends BaseUiTest {

    @Test
    @Tag("UI")
    @Feature("UI")
    @Issue("TC-UI-1")
    @DisplayName("UI - Add new owner with valid data")
    public void addOwnerTest() {
        var owner = Owner.buildDefault();

        homePage.openNewOwnerPage()
                .enterOwnerDataAndSubmit(owner);

        var ownersListPage = new OwnersListPage();

        Assertions.assertTrue(ownersListPage.isDisplayed(),
                "Owners list page not displayed after creating new owner with valid data");

        //safe to check for exactly 1, since every field is randomized text and all fields must match
        Assertions.assertEquals(1, ownersListPage.getCountOfMatchingOwnerEntriesDisplayed(owner),
                "Created owner entry not displayed in list");
    }

    /**
     * Error message after 'Submit' is not present in DOM tree, so checking that same page keeps being displayed instead
     * @param owner - data from BaseTest method, objects with different required fields == null
     */
    @ParameterizedTest
    @MethodSource("createOwnerWithoutRequiredFieldsData")
    @Tag("UI")
    @Feature("UI")
    @Issue("TC-UI-2")
    @DisplayName("UI - Add new owner with empty required field")
    public void addOwnerWithoutRequiredFieldTest(Owner owner) {
        homePage.openNewOwnerPage()
                .enterOwnerDataAndSubmit(owner);

        var newOwnerPage = new NewOwnerPage();

        WaitUtils.waitForConditionToMaintainOver(newOwnerPage::isDisplayed, 5);
    }
}
