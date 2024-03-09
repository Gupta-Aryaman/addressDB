package microservice.v1.addressDB.address;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/address")
public class AddressController {
    @GetMapping
    public String getAddress(@RequestParam Integer number){
        return "hi";
    }
}
