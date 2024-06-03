package org.backbase.task.pages;

import lombok.extern.slf4j.Slf4j;
import org.backbase.task.dto.Owner;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

@Slf4j
public class NewOwnerPage {

    private NewOwnerPage enterFirstName(String firstName) {
        if(Objects.nonNull(firstName)) {
            log.info("Entering first name: " + firstName);
            $x("//input[@name='firstName']").sendKeys(firstName);
        }
        return page(NewOwnerPage.class);
    }

    private NewOwnerPage enterLastName(String lastName) {
        if(Objects.nonNull(lastName)) {
            log.info("Entering last name: " + lastName);
            $x("//input[@name='lastName']").sendKeys(lastName);
        }
        return page(NewOwnerPage.class);
    }

    private NewOwnerPage enterAddress(String address) {
        if(Objects.nonNull(address)) {
            log.info("Entering address: " + address);
            $x("//input[@name='address']").sendKeys(address);
        }
        return page(NewOwnerPage.class);
    }

    private NewOwnerPage enterCity(String city) {
        if(Objects.nonNull(city)) {
            log.info("Entering city: " + city);
            $x("//input[@name='city']").sendKeys(city);
        }
        return page(NewOwnerPage.class);
    }

    private void enterTelephone(String telephone) {
        if(Objects.nonNull(telephone)) {
            log.info("Entering telephone: " + telephone);
            $x("//input[@name='telephone']").sendKeys(telephone);
        }
    }

    public void enterOwnerDataAndSubmit(Owner owner) {
        enterFirstName(owner.getFirstName())
                .enterLastName(owner.getLastName())
                .enterAddress(owner.getAddress())
                .enterCity(owner.getCity())
                .enterTelephone(owner.getTelephone());
        $x("//button[@type='submit']").click();
    }

    public boolean isDisplayed() {
        return $x("//h2[text()='Owner']").isDisplayed();
    }
}
