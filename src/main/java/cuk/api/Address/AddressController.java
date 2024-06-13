package cuk.api.Address;

import cuk.api.Address.Entities.Address;
import cuk.api.ResponseEntities.ResponseMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="주소 관련 기능")
@Controller
@RequestMapping("/api/address")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @ApiOperation("시/도 가져오기")
    @GetMapping("/province")
    public ResponseEntity<ResponseMessage> getProvince() throws Exception {
        ResponseMessage resp = new ResponseMessage();
        List<String> provinceList = addressService.getProvince();

        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Success");
        resp.setData(provinceList);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @ApiOperation("특정 시/도에 속한 시/군/구 가져오기")
    @GetMapping("/city")
    public ResponseEntity<ResponseMessage> getCityByProvince(@RequestParam(required = true) String province) throws Exception {
        ResponseMessage resp = new ResponseMessage();
        List<String> provinceList = addressService.getCityByProvince(province);

        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Success");
        resp.setData(provinceList);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @ApiOperation("특정 시/도 그리고 특정 시/군/구에 속한 읍/면/동 가져오기")
    @GetMapping("/town")
    public ResponseEntity<ResponseMessage> getTownByProvinceAndCity(@RequestParam(required = true) String province, @RequestParam(required = true) String city) throws Exception {
        ResponseMessage resp = new ResponseMessage();
        List<String> provinceList = addressService.getTownByProvinceAndCity(province, city);

        resp.setStatus(HttpStatus.OK);
        resp.setMessage("Success");
        resp.setData(provinceList);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
