package cuk.api.User;

import cuk.api.User.Request.SignUpRequest;
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
    public String signUp(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult) throws Exception{
        if (bindingResult.hasErrors()) {
            System.out.println("에러");
            return "message: fail";
        }
        System.out.println(signUpRequest);
        return "message: success";
    }
}
