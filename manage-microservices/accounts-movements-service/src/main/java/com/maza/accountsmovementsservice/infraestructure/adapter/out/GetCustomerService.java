package com.maza.accountsmovementsservice.infraestructure.adapter.out;

import com.maza.accountsmovementsservice.infraestructure.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@Slf4j
public class GetCustomerService {
    @Value("${customerService.url}")
    private String url;
    @Value("${urlKafka}")
    private String urlKafka;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Method that call a api rest and obtain info customer
     *
     * @param id id customer
     * @return Customer information
     */
    public CustomerDTO getCustomer(String id) throws Exception {
        String builder =  UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("id", id).toUriString();
        ResponseEntity<CustomerDTO> customerDTO =restTemplate.getForEntity(builder, CustomerDTO.class);

        if(customerDTO.getBody()!=null){
            return customerDTO.getBody();
        } else {
            throw new Exception("Customer record not found:: " + id);
        }
    }

    /**
     * Method that call a kafka producer
     *
     * @param transactionsDTO list of transactions
     */
    public void callProducer(String transactionsDTO) {
        String responseBody = restTemplate.postForObject(urlKafka, transactionsDTO, String.class);
        log.info("Responsa async call " + responseBody);
    }
}
