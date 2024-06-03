package org.backbase.task.ui;

import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.backbase.task.BaseUiTest;
import org.backbase.task.dto.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class SearchOwnerTest extends BaseUiTest {

    private static final Owner defaultOwner = Owner.buildWithExistingData();

    private static Object[][] searchFullFieldTextData() {
        return new Object[][] {
                {defaultOwner.getFirstName()},
                {defaultOwner.getLastName()},
                {defaultOwner.getAddress()},
                {defaultOwner.getCity()},
                {defaultOwner.getTelephone()}
        };
    }

    private static Object[][] searchPartialFieldTextData() {
        return new Object[][] {
                {defaultOwner.getFirstName().substring(0, 3)},
                {defaultOwner.getLastName().substring(0, 3)},
                {defaultOwner.getAddress().substring(0, 3)},
                {defaultOwner.getCity().substring(0, 3)},
                {defaultOwner.getTelephone().substring(0, 3)}
        };
    }

    @ParameterizedTest
    @MethodSource("searchFullFieldTextData")
    @Tag("UI")
    @Feature("UI")
    @Issue("TC-UI-5")
    @DisplayName("UI - Search for existing owner by full single field data")
    public void searchExistingOwnerByFullFieldTextTest(String searchText) {
        var ownersListPage = homePage.openOwnersList();

        ownersListPage.enterSearchFilterText(searchText);

        Assertions.assertTrue(ownersListPage.getCountOfMatchingOwnerEntriesDisplayed(defaultOwner) >= 1,
                "No owner entries found after searching for existing owner data");
    }

    /**
     * Exact same steps as searching by full field data, just logically separated into different cases
     * @param searchText partial data from various fields
     */
    @ParameterizedTest
    @MethodSource("searchPartialFieldTextData")
    @Tag("UI")
    @Feature("UI")
    @Issue("TC-UI-6")
    @DisplayName("UI - Search for existing owner by partial single field data")
    public void searchExistingOwnerByPartialFieldTextTest(String searchText) {
        var ownersListPage = homePage.openOwnersList();

        ownersListPage.enterSearchFilterText(searchText);

        Assertions.assertTrue(ownersListPage.getCountOfMatchingOwnerEntriesDisplayed(defaultOwner) >= 1,
                "No owner entries found after searching for existing owner data");
    }

    @ParameterizedTest
    @ValueSource(strings = {"...", "?#$%", "abcdef", "9999999"})
    @Tag("UI")
    @Feature("UI")
    @Issue("TC-UI-7")
    @DisplayName("UI - Search for owner by non-existing data")
    public void searchOwnerByNonExistingDataTest(String searchText) {
        var ownersListPage = homePage.openOwnersList();

        ownersListPage.enterSearchFilterText(searchText);

        Assertions.assertEquals(0, ownersListPage.getCountOfMatchingOwnerEntriesDisplayed(defaultOwner),
                "Found owner entries after searching for non-existing data");
    }


    /**
     * Test fails. Searching for only first name or only last name works, but "firstname lastname" doesn't,
     * despite it being displayed this way. Acting without requirements and on "common sense" I'd consider it a bug
     */
    @Test
    @Tag("UI")
    @Feature("UI")
    @Issue("TC-UI-8")
    @DisplayName("UI - Search for existing owner by first name + last name")
    public void searchOwnerByFirstAndLastNameTest() {
        var ownersListPage = homePage.openOwnersList();
        var searchText = defaultOwner.getFirstName() + " " + defaultOwner.getLastName();

        ownersListPage.enterSearchFilterText(searchText);

        Assertions.assertTrue(ownersListPage.getCountOfMatchingOwnerEntriesDisplayed(defaultOwner) >= 1,
                "No owner entries found after searching for existing owner data");
    }


    /**
     * Test fails. Filter appears to work by some non-displayed data in owner list (e.g. pet data), but not all of it
     * Again, no requirements, "common sense" only, considering existence of results specifically for '2010' and '04'
     *      (with default owner list data) bugs.
     * @param searchText hidden text to search by
     */
    @ParameterizedTest
    @ValueSource(strings = {"2010", "04", "hamster", "Apr", "March"})
    @Tag("UI")
    @Feature("UI")
    @Issue("TC-UI-9")
    @DisplayName("UI - Search for existing owner by non-displayed field data")
    public void searchOwnerByHiddenPetDataTest(String searchText) {
        var ownersListPage = homePage.openOwnersList();

        ownersListPage.enterSearchFilterText(searchText);

        Assertions.assertEquals(0, ownersListPage.getAllEntriesCount(),
                "Found owner entries after searching for non-existing data");
    }
}
