package org.backbase.task;

import org.backbase.task.dto.Owner;

public class BaseTest {

    /**
     * Single method for data for Owner creation scenarios via API and UI
     * @return Owner objects with different required fields == null
     */
    protected static Object[][] createOwnerWithoutRequiredFieldsData() {
        var ownerFirstNameNull = Owner.buildDefault();
        ownerFirstNameNull.setFirstName(null);

        var ownerLastNameNull = Owner.buildDefault();
        ownerLastNameNull.setLastName(null);

        var ownerAddressNull = Owner.buildDefault();
        ownerAddressNull.setAddress(null);

        var ownerCityNull = Owner.buildDefault();
        ownerCityNull.setCity(null);

        var ownerTelephoneNull = Owner.buildDefault();
        ownerTelephoneNull.setTelephone(null);
        return new Object[][] {
                {ownerFirstNameNull},
                {ownerLastNameNull},
                {ownerAddressNull},
                {ownerCityNull},
                {ownerTelephoneNull}
        };
    }
}
