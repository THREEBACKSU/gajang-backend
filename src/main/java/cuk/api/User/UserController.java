package cuk.api.User;

import cuk.api.Address.AddressService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Api(tags="사용자 기능")
@Controller
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AddressService addressService;

    @Autowired
    public UserController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @ApiOperation("아이디 중복확인")
    @PostMapping("/u_id/{u_id}")
    public ResponseEntity<ResponseMessage> isDuplicatedID(@PathVariable("u_id") String u_id) throws Exception {
        ResponseMessage resp = new ResponseMessage();

        User user = userService.isDuplicatedId(u_id);

        if (user != null) {
            resp.setStatus(HttpStatus.OK);
            resp.setMessage("Unusable");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }

        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Usable");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @ApiOperation("이름 중복확인")
    @PostMapping("/name/{name}")
    public ResponseEntity<ResponseMessage> isDuplicatedName(@PathVariable("name") String name) throws Exception {
        ResponseMessage resp = new ResponseMessage();

        User user = userService.isDuplicatedName(name);

        if (user != null) {
            resp.setStatus(HttpStatus.OK);
            resp.setMessage("Unusable");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }

        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Usable");
        return new ResponseEntity<>(resp, HttpStatus.OK);
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

        // 주소 확인
        int address_id = addressService.getAddressId(signUpRequest.getProvince(), signUpRequest.getCity(), signUpRequest.getTown());
        if (address_id == -1) {
            resp.setStatus(HttpStatus.BAD_REQUEST);
            resp.setMessage("입력된 주소가 유효하지 않습니다.");
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }

        userService.signUp(signUpRequest, address_id);

        resp.setStatus(HttpStatus.CREATED);
        resp.setMessage("Success");
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
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
