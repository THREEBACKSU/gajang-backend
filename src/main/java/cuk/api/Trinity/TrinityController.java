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
    public ResponseEntity<ResponseMessage> login(@RequestBody @Valid LoginRequest loginRequest, HttpSession httpSession) throws Exception{
        ResponseMessage resp = new ResponseMessage();

        TrinityUser trinityUser = trinityService.login(loginRequest);

        httpSession.setAttribute("trinityUser", trinityUser);

        resp.setMessage("Success");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/grade")
    public ResponseEntity<ResponseMessage> getGrades(HttpSession httpSession) throws Exception{
        ResponseMessage resp = new ResponseMessage();

        TrinityUser trinityUser = (TrinityUser) httpSession.getAttribute("trinityUser");

        if (trinityUser == null) {
            resp.setMessage("Need Login");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        trinityService.getGrades(trinityUser);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
