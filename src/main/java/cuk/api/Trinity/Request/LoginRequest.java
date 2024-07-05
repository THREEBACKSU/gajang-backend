package cuk.api.Trinity.Request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String trinityId;
    @NotBlank
    private String password;
}
