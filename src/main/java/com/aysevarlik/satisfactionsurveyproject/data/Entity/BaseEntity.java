package com.aysevarlik.satisfactionsurveyproject.data.Entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass

@Data
@NoArgsConstructor

@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {}, allowGetters = true)
public class BaseEntity {

    @Column(name = "createdBy")
    @CreatedBy
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "updateBy")
    @LastModifiedBy
    private String updateBy;

    @Column(name = "updateDate")
    @LastModifiedDate
    private Date updateDate;



}
