package com.aysevarlik.satisfactionsurveyproject.Business.Services.ServicesImpl;

import com.aysevarlik.satisfactionsurveyproject.Business.Dto.CustomerDto;
import com.aysevarlik.satisfactionsurveyproject.Business.Services.IServices;
import com.aysevarlik.satisfactionsurveyproject.data.Entity.BaseEntity;
import com.aysevarlik.satisfactionsurveyproject.data.Entity.CustomerEntity;
import com.aysevarlik.satisfactionsurveyproject.data.Repository.ICustomerRepo;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Log4j2
public class ServicesImpl implements IServices {

    @Autowired
    ICustomerRepo customerRepo;

    @Autowired
    ModelMapper mapper;

    @Override
    public CustomerDto EntityToDto(CustomerEntity customerEntity) {
        CustomerDto customerDto = mapper.map(customerEntity, CustomerDto.class);
        return customerDto;
    }

    @Override
    public CustomerEntity DtoToEntity(CustomerDto customerDto) {
        CustomerEntity entity = mapper.map(customerDto, CustomerEntity.class);
        return entity;
    }

    //http://localhost:8081/create/customer
    @Override
    @PostMapping("/create/customer")
    public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerEntity entity = new CustomerEntity();
//        CustomerEntity entity = DtoToEntity(customerDto);
        entity.setCustomerName(customerDto.getCustomerName());
        entity.setCustomerSurname(customerDto.getCustomerSurname());
        entity.setCustomerEmail(customerDto.getCustomerEmail());
        entity.setCustomerMessage(customerDto.getCustomerMessage());
        customerRepo.save(entity);
        log.info("başarılı");
        return customerDto;
    }

    //http://localhost:8081/list/customer
    @Override
    @GetMapping("/list/customer")
    public List<CustomerDto> getAllCustomers() {
        List<CustomerDto> list = new ArrayList<>();
        Iterable<CustomerEntity> myList = customerRepo.findAll();
        for (CustomerEntity entity : myList){
            CustomerDto customerDto = EntityToDto(entity);
            list.add(customerDto);
        }
        return list;
    }


    //http://localhost:8081/find/customer/1
    @Override
    @GetMapping("/find/customer/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(name = "id") Long id) {
        CustomerEntity entity = customerRepo.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException(id+"bulunamadı"));
        CustomerDto customerDto = EntityToDto(entity);
        return ResponseEntity.ok(customerDto);
    }

    //http://localhost:8081/update/customer/1
    @Override
    @PutMapping("/update/customer/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable(name = "id") Long id,@RequestBody CustomerDto customerDto) {
        CustomerEntity customerEntity=DtoToEntity(customerDto);
        CustomerEntity customerFind=customerRepo.findById(id)
                .orElseThrow( ()->new ResourceNotFoundException(id+ "bulunamadı"));
        customerFind.setCustomerName(customerEntity.getCustomerName());
        customerFind.setCustomerSurname(customerEntity.getCustomerSurname());
        customerFind.setCustomerEmail(customerEntity.getCustomerEmail());
        customerFind.setCustomerMessage(customerEntity.getCustomerMessage());
        CustomerEntity entity = customerRepo.save(customerFind);

        CustomerDto customerDto1 = EntityToDto(entity);
        return ResponseEntity.ok(customerDto1);
    }

    //http://localhost:8081/customer/delete/2
    @Override
    @DeleteMapping("/customer/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable(name = "id") Long id) {
        CustomerEntity entity = customerRepo.findById(id)
                .orElseThrow( ()-> new ResourceNotFoundException(id+"bulunamadı"));
        customerRepo.delete(entity);
        Map<String,Boolean> response=new HashMap<>();
        response.put("silindi",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
