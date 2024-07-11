package cuk.api.Trinity.Entity;

import cuk.api.Trinity.Request.LoginRequest;
import cuk.api.User.Entities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.json.simple.JSONObject;
import org.springframework.security.core.parameters.P;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
@Data
@ToString
public class TrinityUser implements Serializable {
    private static final long serialVersionUID = 123L;

    private String samlRequest;
    private String SAMLResponse;
    private String _csrf;
    private String trinityId;
    private String password;
    private Role role;

    // 트리니티 실제 정보
    private TrinityInfo trinityInfo;

    public TrinityUser(LoginRequest loginRequest) {
        this.trinityInfo = new TrinityInfo();
        this.trinityId = loginRequest.getTrinityId();
        this.password = loginRequest.getPassword();
    }
}
