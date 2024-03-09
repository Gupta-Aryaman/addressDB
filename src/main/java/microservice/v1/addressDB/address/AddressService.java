package microservice.v1.addressDB.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressService {
    private final RestTemplate response;

    @Autowired
    public AddressService(RestTemplate response) {
        this.response = response;
    }

    public ResponseEntity<?> fetchAddress(Integer number){

        String url = "https://fakerapi.it/api/v1/addresses?_quantity=" + number;
        ResponseEntity<FakerApiTemplate> responseEntity = response.getForEntity(url, FakerApiTemplate.class);

        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body("Ok");
    }
}
