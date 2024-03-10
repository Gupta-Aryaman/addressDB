package microservice.v1.addressDB.address;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddressServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private AddressRepository addressRepository;

    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        addressService = new AddressService(restTemplate, addressRepository);
    }

    @Test
    void fetchAddress_Success() throws Exception {
        // Mocking the response from the external API
        Address a1 = new Address(1,
                "78777 Elise Village",
                "Nicolas Wells",
                "2031",
                "Gersonshire",
                "21877-5737",
                "Guyana",
                "NP",
                18.32663f,
                139.101706f);
        Address a2 = new Address(2,
                "84913 Legro Extension",
                "Stehr Row", "2090",
                "Rutherfordstad",
                "38689-8061",
                "Chile",
                "LV",
                24.731637f,
                70.550021f);

        ResponseEntity<FakerApiTemplate> responseEntity = new ResponseEntity<>(new FakerApiTemplate("OK",
                200,
                10,
                List.of(a1, a2)),
                HttpStatus.OK);
        when(restTemplate.getForEntity(any(URI.class), eq(FakerApiTemplate.class))).thenReturn(responseEntity);


        ResponseEntity<?> result = addressService.fetchAddress(5);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Database updated successfully!", result.getBody());
    }

    @Test
    void fetchAddress_EmptyResponse() throws Exception {
        // Mocking the response from the external API
        ResponseEntity<FakerApiTemplate> responseEntity = new ResponseEntity<>(new FakerApiTemplate("OK",
                200,
                10,
                List.of()),
                HttpStatus.OK);
        when(restTemplate.getForEntity(any(URI.class), eq(FakerApiTemplate.class))).thenReturn(responseEntity);

        ResponseEntity<?> result = addressService.fetchAddress(11);
        System.out.println(result);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertEquals("FakerAPI gave no data in response", result.getBody());
    }

    @Test
    void fetchAddress_HttpError() throws Exception {
        // Mocking the response from the external API with HTTP error
        ResponseEntity<FakerApiTemplate> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        when(restTemplate.getForEntity(any(URI.class), eq(FakerApiTemplate.class))).thenReturn(responseEntity);

        ResponseEntity<?> result = addressService.fetchAddress(5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("HTTP Status Code was not 200", result.getBody());
    }

    @Test
    void fetchAddress_Exception() throws Exception {
        // Mocking an exception from the external API call
        when(restTemplate.getForEntity(any(URI.class), eq(FakerApiTemplate.class))).thenThrow(new RuntimeException("Test Exception"));

        ResponseEntity<?> result = addressService.fetchAddress(5);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
        assertEquals("Error: Test Exception", result.getBody());
    }
}
