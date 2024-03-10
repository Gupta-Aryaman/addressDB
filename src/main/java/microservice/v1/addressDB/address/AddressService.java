package microservice.v1.addressDB.address;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class AddressService {
    private URI url;
    private RestTemplate restTemplate;

    public AddressService(){
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<?> fetchAddress(Integer number){

        try{
            this.url = new URI("https://fakerapi.it/api/v1/addresses?_quantity=" + number);
        } catch (Exception e){
            System.out.println(e);
        }

        ResponseEntity<FakerApiTemplate> result = restTemplate.getForEntity(this.url, FakerApiTemplate.class);
        FakerApiTemplate task = result.getBody();

        System.out.println(task);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(task);
    }
}
