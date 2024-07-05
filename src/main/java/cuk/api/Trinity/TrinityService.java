package cuk.api.Trinity;

import cuk.api.Trinity.Entity.TrinityUser;
import cuk.api.Trinity.Request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TrinityService {
    private final TrinityRepository trinityRepository;

    @Autowired
    public TrinityService(TrinityRepository trinityRepository) {
        this.trinityRepository = trinityRepository;
    }
    public void login(LoginRequest loginRequest) throws Exception{
        TrinityUser trinityUser = new TrinityUser(loginRequest);

        trinityUser = trinityRepository.loginForm(trinityUser);

        trinityUser = trinityRepository.auth(trinityUser);

        trinityUser = trinityRepository.login(trinityUser);
//
//        System.out.println("쿠키2: " + trinityUser.getCookie());
//        System.out.println("_csrf: " + trinityUser.get_csrf());
    }
}
