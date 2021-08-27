package com.machineCoding.parkingLot.services;

import com.machineCoding.parkingLot.DTOs.CustomerDTO;
import com.machineCoding.parkingLot.models.Customer;
import com.machineCoding.parkingLot.models.User;
import com.machineCoding.parkingLot.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class CustomerService {
    private List<Customer> customers = new ArrayList<>();
    private Map<String, Integer> customerIdToCustomerIndex = new HashMap<>();

    // Will Add DTOs like this in other functions also. Not adding due to time constraint
    public Customer addCustomer(CustomerDTO customerDTO) {
        Customer addedCustomer = Customer
                .builder()
                .id(TokenUtil.generateRandomTokenDefaultLength())
                .email(customerDTO.getEmail())
                .name(customerDTO.getName())
                .build();
        customers.add(addedCustomer);
        customerIdToCustomerIndex.put(addedCustomer.getId(), customers.size() - 1);
        log.info("addedCustomer: {}" , addedCustomer);
        return addedCustomer;
    }

    public Optional<Customer> getCustomerById(String customerId) {
        if (!customerIdToCustomerIndex.containsKey(customerId)) {
            return Optional.empty();
        }
        int customerInd = customerIdToCustomerIndex.get(customerId);
        return Optional.of(customers.get(customerInd));
    }
}
