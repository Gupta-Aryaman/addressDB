package microservice.v1.addressDB.address;

//import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import microservice.v1.addressDB.address.Exceptions.*;


@Service
public class AddressService {
    private RestTemplate restTemplate;
    private AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository){
        this.restTemplate = new RestTemplate();
        this.addressRepository = addressRepository;
    }
    public AddressService(RestTemplate restTemplate, AddressRepository addressRepository){
        this.restTemplate = restTemplate;
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<?> fetchAddress(Integer number){
        try{
            URI url = new URI("https://fakerapi.it/api/v1/addresses?_quantity=" + number);


            ResponseEntity<FakerApiTemplate> result = restTemplate.getForEntity(url, FakerApiTemplate.class);

            if (result.getStatusCode() != HttpStatus.OK) {
                throw new HttpErrorException("FakerAPI response status Code was not 200", result.getStatusCode());
            }

            List<Address> task = result.getBody().getData();

            if (result.getBody() == null || ((result.getBody().getData() == null || result.getBody().getData().isEmpty()) && number != 0) ) {
                throw new EmptyResponseException("FakerAPI gave no data in response");
            }

            for(int i = 0; i < task.size(); i++){
                addressRepository.save(task.get(i));
            }

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Database updated successfully!");

        } catch(HttpErrorException e){
            return ResponseEntity
                    .status(e.getStatus_code())
                    .body(e.getMessage());

        } catch(EmptyResponseException e){
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(e.getMessage());

        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }

    }

    public ResponseEntity<?> listContentsDB(){
        try{
            List<Address> q = addressRepository.findAll();
            Integer len = q.size();

            Map<String, Object> mp = new HashMap<>();
            mp.put("Status", "Success");
            mp.put("Output_size", len);
            mp.put("Addresses", q);

            // Convert the Map to JSON format
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonBody = objectMapper.writeValueAsString(mp);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(jsonBody);

        } catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}
