package com.aysevarlik.satisfactionsurveyproject.data.Repository;

import com.aysevarlik.satisfactionsurveyproject.data.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepo extends JpaRepository<CustomerEntity, Long> {
}
