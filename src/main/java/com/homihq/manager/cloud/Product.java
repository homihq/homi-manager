package com.homihq.manager.cloud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Column(name = "product_type")
    private String name;

    @Column(name = "deleted")
    private boolean deleted;

    @LastModifiedDate
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;


    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductVariant> variants = new ArrayList<>();

    public Product addVariant(ProductVariant variant) {
        variants.add(variant);
        variant.setProduct(this);
        return this;
    }


}
