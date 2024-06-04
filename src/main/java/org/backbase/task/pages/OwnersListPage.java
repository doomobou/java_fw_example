package org.backbase.task.pages;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.WebElementCondition;
import org.backbase.task.dto.Owner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class OwnersListPage {

    /**
        Possibly a bit overcomplicated, but roughly the same code would be needed to do the same using a "simpler" loop,
        just without Selenide-specific wrappings (WebElementCondition, CheckResult)
     */
    private static WebElementCondition matchOwnerData(Owner owner) {
        return new WebElementCondition("matches owner data") {
            @Nonnull
            @Override
            public CheckResult check(Driver driver, WebElement webElement) {
                var nameText = webElement.findElement(By.xpath("./td/a")).getText().trim();
                boolean isNameMatch = nameText.equals(owner.getFirstName() + " " + owner.getLastName());

                var addressText = webElement.findElement(By.xpath("./td[contains(@class,'hidden-sm')]")).getText();
                boolean isAddressMatch = addressText.equals(owner.getAddress());

                var cityText = webElement.findElement(By.xpath("./td[3]")).getText();
                boolean isCityMatch = cityText.equals(owner.getCity());

                var telephoneText = webElement.findElement(By.xpath("./td[4]")).getText();
                boolean isTelephoneMatch = telephoneText.equals(owner.getTelephone());

                return new CheckResult(isNameMatch &&
                        isAddressMatch &&
                        isCityMatch &&
                        isTelephoneMatch, owner.toString());
            }
        };
    }

    public boolean isDisplayed() {
        return $x("//h2[text()='Owners']").isDisplayed();
    }

    private ElementsCollection getListEntries() {
        return $$x("//tr[@class='ng-scope']");
    }

    public int getAllEntriesCount() {
        return getListEntries().size();
    }

    public int getCountOfMatchingOwnerEntriesDisplayed(Owner owner) {
        return getListEntries()
                .filter(matchOwnerData(owner))
                .size();
    }

    public void enterSearchFilterText(String text) {
        $x("//input[@placeholder='Search Filter']").sendKeys(text);
    }
}
