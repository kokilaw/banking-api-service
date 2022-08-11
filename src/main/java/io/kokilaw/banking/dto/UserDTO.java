package io.kokilaw.banking.dto;

import io.kokilaw.banking.util.validation.validators.ValidDateFormat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by kokilaw on 2022-08-09
 */
@Data
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Given name cannot be empty")
    private String givenName;

    @NotEmpty(message = "Family name cannot be empty")
    private String familyName;

    @NotEmpty(message = "Date of birth cannot be empty")
    @ValidDateFormat(pattern = "yyyy-MM-dd", message = "Date of birth should be in yyyy-MM-dd format")
    private String dateOfBirth;

    @Size(max = 10, min = 10, message = "Valid NIC should contain 10 characters")
    @NotEmpty(message = "NIC cannot be empty")
    private String nic;

    @Email(message = "A valid email should be entered")
    private String email;


}
