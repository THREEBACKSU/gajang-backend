package cuk.api.User.Request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignInRequest {
    @Size(min=3)
    private String u_id;
    @Size(min=8)
    private String password;
}
