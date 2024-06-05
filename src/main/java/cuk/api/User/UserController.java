package cuk.api.User;

import cuk.api.ResponseEntities.CommonResponse;
import cuk.api.ResponseEntities.ErrorResponse;
import cuk.api.User.Request.SignUpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class UserController {

    @PostMapping("/signup")
    @ResponseBody
    public CommonResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            return new CommonResponse("Failed", new ErrorResponse(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다"));
        }
        System.out.println(signUpRequest);
        return new CommonResponse("Success", HttpStatus.CREATED);
    }
}
