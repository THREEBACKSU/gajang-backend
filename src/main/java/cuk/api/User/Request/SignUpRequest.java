package cuk.api.User.Request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    @Size(min=3)
    private String u_id;
    @Size(min=8)
    private String password;
    @NotBlank
    private String name;
    @NotNull
    private int address_id;
    @NotBlank
    private String province;
    @NotBlank
    private String city;
    @NotBlank
    private String town;
    @NotBlank
    private String phone;
    @NotNull
    private int bank_id;
    @NotBlank
    private String account_number;
}
