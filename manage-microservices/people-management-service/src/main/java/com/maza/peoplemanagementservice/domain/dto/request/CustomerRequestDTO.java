package com.maza.peoplemanagementservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerRequestDTO {
    @NotBlank(message = "The name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "The name must only contain letters and spaces")
    private String name;
    @NotNull(message = "Gender is required")
    private Gender gender;
    private int age;
    @Pattern(regexp = "^[0-9]+$", message = "The id card must only contain numbers")
    @Size(min = 10, max = 10, message = "IdCard must be 10 characters long")
    private String idCard;
    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 20, message = "Address must be between 8 and 20 characters long")
    private String address;
    @Pattern(regexp = "^[0-9]+$", message = "The phone number only contain numbers")
    private String phone;
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters long")
    private String password;

    private boolean status;

    public Gender getGender() {
        return gender;
    }

    public void setGenero(Gender gender) {
        this.gender = gender;
    }
}
