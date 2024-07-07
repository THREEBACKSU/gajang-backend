package cuk.api.Trinity;

import cuk.api.ResponseEntities.ResponseMessage;
import cuk.api.Trinity.Entity.SecurityTrinityUser;
import cuk.api.Trinity.Entity.TrinityUser;
import cuk.api.Trinity.Request.LoginRequest;
import cuk.api.Trinity.Request.SubjtNoRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/trinity/auth")
@Api(tags = "트리니티 관련 기능")
public class TrinityController {
    private final TrinityService trinityService;

    @Autowired
    public TrinityController(TrinityService trinityService) {
        this.trinityService = trinityService;
    }

    @GetMapping("/grade")
    @ApiOperation("세션 정보 토대로 금학기 성적 조회, 비공개여도 받아올 수 있음")
    public ResponseEntity<ResponseMessage> getGrades(@AuthenticationPrincipal SecurityTrinityUser securityTrinityUser) throws Exception{
        ResponseMessage resp = new ResponseMessage();

        TrinityUser trinityUser = securityTrinityUser.getUser();

        trinityUser = trinityService.getGrades(trinityUser);
        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Success");
        resp.setData(trinityUser.getGrades());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/sujtNo")
    public ResponseEntity<ResponseMessage> getSujtNo(@RequestBody @Valid SubjtNoRequest subjtNoRequest, @AuthenticationPrincipal SecurityTrinityUser securityTrinityUser) throws Exception{
        ResponseMessage resp = new ResponseMessage();

        TrinityUser trinityUser = securityTrinityUser.getUser();

        trinityService.getSujtNo(trinityUser, subjtNoRequest);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
