package cuk.api.Trinity;

import cuk.api.ResponseEntities.ResponseMessage;
import cuk.api.Trinity.Entity.TrinityUser;
import cuk.api.Trinity.Request.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/api/trinity")
@Api(tags = "트리니티 관련 기능")
public class TrinityController {
    private final TrinityService trinityService;

    @Autowired
    public TrinityController(TrinityService trinityService) {
        this.trinityService = trinityService;
    }
    @PostMapping("/login")
    @ApiOperation("트리니티 로그인 정보 얻어온 뒤, 세션에 저장")
    public ResponseEntity<ResponseMessage> login(@RequestBody @Valid LoginRequest loginRequest, HttpSession httpSession) throws Exception{
        ResponseMessage resp = new ResponseMessage();

        TrinityUser trinityUser = trinityService.login(loginRequest);

        httpSession.setAttribute("trinityUser", trinityUser);

        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Success");
        resp.setData(trinityUser.getTrinityInfo());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/grade")
    @ApiOperation("세션 정보 토대로 금학기 성적 조회, 비공개여도 받아올 수 있음")
    public ResponseEntity<ResponseMessage> getGrades(HttpSession httpSession) throws Exception{
        ResponseMessage resp = new ResponseMessage();

        TrinityUser trinityUser = (TrinityUser) httpSession.getAttribute("trinityUser");

        if (trinityUser == null) {
            resp.setMessage("Need Login");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        trinityUser = trinityService.getGrades(trinityUser);
        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Success");
        resp.setData(trinityUser.getGrades());
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
