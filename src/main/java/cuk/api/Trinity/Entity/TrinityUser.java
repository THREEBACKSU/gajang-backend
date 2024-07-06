package cuk.api.Trinity.Entity;

import cuk.api.Trinity.Request.LoginRequest;
import lombok.Data;
import org.springframework.security.core.parameters.P;

import java.io.Serializable;
import java.util.HashMap;
@Data
public class TrinityUser implements Serializable {
    private static final long serialVersionUID = 123L;
    private String samlRequest;
    private String SAMLResponse;
    private String _csrf;
    private HashMap<String, String> cookies;
    private String trinityId;
    private String password;

    public TrinityUser(LoginRequest loginRequest) {
        this.cookies = new HashMap<>();
        this.trinityId = loginRequest.getTrinityId();
        this.password = loginRequest.getPassword();
    }

    public void addCookie(String key, String value) {
        cookies.put(key, value);
    }
    public String getCookie() {
        String cookie = "";
        for (String key : cookies.keySet()) {
            cookie += (key + "=" + cookies.get(key) + ";");
        }
        return cookie;
    }
}
