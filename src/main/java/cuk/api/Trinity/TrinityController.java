package cuk.api.Trinity;

import cuk.api.ResponseEntities.ResponseMessage;
import cuk.api.Trinity.Request.LoginRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseEntity<ResponseMessage> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception{
        ResponseMessage resp = new ResponseMessage();

        trinityService.login(loginRequest);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
