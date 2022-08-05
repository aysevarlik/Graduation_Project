package com.aysevarlik.satisfactionsurveyproject.Business.Services;

import com.aysevarlik.satisfactionsurveyproject.Business.Dto.CustomerDto;
import com.aysevarlik.satisfactionsurveyproject.data.Entity.CustomerEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IServices {
    public CustomerDto EntityToDto(CustomerEntity customerEntity);
    public CustomerEntity DtoToEntity(CustomerDto customerDto);

    public CustomerDto createCustomer(CustomerDto customerDto);

    public List<CustomerDto> getAllCustomers();

    public ResponseEntity<CustomerDto> getCustomerById(Long id);

    public ResponseEntity<CustomerDto> updateCustomer(Long id, CustomerDto customerDto);

    public ResponseEntity<Map<String,Boolean>> deleteCustomer(Long id);

}
