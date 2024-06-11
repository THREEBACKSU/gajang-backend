package cuk.api.User;

import cuk.api.ResponseEntities.ResponseMessage;
import cuk.api.User.Entities.User;
import cuk.api.User.Request.SignInRequest;
import cuk.api.User.Request.SignUpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Api(tags="사용자 기능")
@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("회원가입")
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<ResponseMessage> signUp(@RequestBody @Valid SignUpRequest signUpRequest, BindingResult bindingResult) throws Exception{
        ResponseMessage resp = new ResponseMessage();
        if (bindingResult.hasErrors()) {
            resp.setStatus(HttpStatus.BAD_REQUEST);
            resp.setMessage("입력된 값이 유효하지 않습니다.");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        userService.signUp(signUpRequest);

        resp.setStatus(HttpStatus.CREATED);
        resp.setMessage("Success");
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @ApiOperation("로그인")
    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<ResponseMessage> login(@RequestBody @Valid SignInRequest signInRequest, BindingResult bindingResult, HttpSession httpSession) throws Exception {
        ResponseMessage resp = new ResponseMessage();
        if (bindingResult.hasErrors()) {
            resp.setStatus(HttpStatus.BAD_REQUEST);
            resp.setMessage("입력된 값이 유효하지 않습니다.");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
        User user = userService.signIn(signInRequest);

        if (user == null) {
            resp.setStatus(HttpStatus.BAD_REQUEST);
            resp.setMessage("ID, PASSWORD가 일치하지 않습니다.");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        httpSession.setAttribute("user", user);

        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Success");
        resp.setData(user);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @ApiOperation("로그아웃")
    @GetMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(HttpSession httpSession) throws Exception{
        ResponseMessage resp = new ResponseMessage();
        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Success");
        httpSession.invalidate();

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
