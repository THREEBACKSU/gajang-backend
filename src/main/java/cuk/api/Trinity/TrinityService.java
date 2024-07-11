package cuk.api.Trinity;

import cuk.api.Trinity.Entity.TrinityUser;
import cuk.api.Trinity.Request.LoginRequest;
import cuk.api.Trinity.Request.SubjtNoRequest;
import cuk.api.Trinity.Response.GradesResponse;
import cuk.api.Trinity.Response.SujtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public TrinityUser login(LoginRequest loginRequest) throws Exception{
        TrinityUser trinityUser = new TrinityUser(loginRequest);

        trinityUser = trinityRepository.loginForm(trinityUser);

        trinityUser = trinityRepository.auth(trinityUser);

        trinityUser = trinityRepository.login(trinityUser);

        trinityUser = trinityRepository.getUserInfo(trinityUser);

        trinityUser = trinityRepository.getSchoolInfo(trinityUser);

        return trinityUser;
    }

    public GradesResponse getGrades(TrinityUser trinityUser) throws Exception {
        return trinityRepository.getGrades(trinityUser);
    }

    public SujtResponse getSujtNo(TrinityUser trinityUser, SubjtNoRequest subjtNoRequest) throws Exception {
        return trinityRepository.getSujtNo(trinityUser, subjtNoRequest);
    }
}
