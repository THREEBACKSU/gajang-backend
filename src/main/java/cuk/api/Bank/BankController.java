package cuk.api.Bank;

import cuk.api.Bank.Entities.Bank;
import cuk.api.ResponseEntities.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api(tags="은행 관련 기능")
@Controller
@RequestMapping("/api/bank")
public class BankController {
    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }
    @ApiOperation("은행 목록 가져오기")
    @GetMapping("/list")
    public ResponseEntity<ResponseMessage> getBankList() throws Exception{
        ResponseMessage resp = new ResponseMessage();
        List<Bank> bankList = bankService.getBankList();

        resp.setMessage("Success");
        resp.setStatus(HttpStatus.OK);
        resp.setData(bankList);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
