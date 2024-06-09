package cuk.api;

import cuk.api.ResponseEntities.ResponseMessage;
import cuk.api.User.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WelcomeController {
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity<ResponseMessage> welcome() {
        ResponseMessage resp = new ResponseMessage();
        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Welcome to gajang~");

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}