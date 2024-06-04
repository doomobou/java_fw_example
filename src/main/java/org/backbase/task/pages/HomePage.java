package org.backbase.task.pages;


import lombok.extern.slf4j.Slf4j;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

@Slf4j
public class HomePage {

    private void openOwnersDropdown() {
        log.info("Clicking 'Owners' in top panel");
        $x("//a[@data-toggle]").click();
    }

    public OwnersListPage openOwnersList() {
        openOwnersDropdown();

        log.info("Clicking 'All' in Owners dropdown menu");
        $x("//li[./a[@ui-sref='owners']]").click();

        return page(OwnersListPage.class);
    }

    public NewOwnerPage openNewOwnerPage() {
        openOwnersDropdown();

        log.info("Clicking 'Register' in Owners dropdown menu");
        $x("//li[./a[@ui-sref='ownerNew']]").click();

        return page(NewOwnerPage.class);
    }
}
