package org.backbase.task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

@Data
@Builder
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    private Integer id;

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    private List<Pet> pets;

    public static Owner buildDefault() {
        return Owner.builder()
                .firstName(RandomStringUtils.randomAlphabetic(10))
                .lastName(RandomStringUtils.randomAlphabetic(10))
                .address(RandomStringUtils.randomAlphanumeric(10))
                .city(RandomStringUtils.randomAlphabetic(8))
                .telephone(RandomStringUtils.randomNumeric(10))
                .build();
    }

    /**
     * @return Owner object with data present in initial app
     * Not the best approach, but a compromise with current options to handle test data - see general evaluation doc for more info
     */
    public static Owner buildWithExistingData() {
        return Owner.builder()
                .firstName("Harold")
                .lastName("Davis")
                .address("563 Friendly St.")
                .city("Windsor")
                .telephone("6085553198")
                .build();
    }

    public boolean equalsWithoutPets(Owner that) {
        return this.firstName.equals(that.firstName) &&
                this.lastName.equals(that.lastName) &&
                this.address.equals(that.address) &&
                this.city.equals(that.city) &&
                this.telephone.equals(that.telephone);
    }
}
