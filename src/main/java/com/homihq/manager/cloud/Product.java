package com.homihq.manager.cloud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "t_product")
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Type(type = "jsonb")
    @Column(name = "features", columnDefinition = "jsonb")
    private List<String> features;


    @Column(name = "price")
    private int price;

    private Currency currency;

    @Column(name = "active")
    private boolean active;

    @Column(name = "spec_details")
    private String specDetails;

    @Column(name = "description")
    private String description;


    @LastModifiedDate
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToOne
    @JoinColumn(name="cloud_id" , referencedColumnName = "id")
    private Cloud cloud;
}