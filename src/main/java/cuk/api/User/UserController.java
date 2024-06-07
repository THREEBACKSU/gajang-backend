package cuk.api.User;

import cuk.api.ResponseEntities.ResponseMessage;
import cuk.api.User.Entities.User;
import cuk.api.User.Request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<ResponseMessage> signUp(@RequestBody @Valid User user, BindingResult bindingResult) throws Exception{
        ResponseMessage resp = new ResponseMessage();
        if (bindingResult.hasErrors()) {
            resp.setStatus(HttpStatus.BAD_REQUEST);
            resp.setMessage("입력된 값이 유효하지 않습니다.");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        resp.setStatus(HttpStatus.CREATED);
        resp.setMessage("회원가입이 성공했습니다.");
        resp.setData(user);
        userService.signUp(user);
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }
}
