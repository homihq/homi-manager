package com.homihq.manager.product;

import com.vladmihalcea.hibernate.type.money.MonetaryAmountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.money.MonetaryAmount;
import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "t_product_variant")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(
        name="monetaryAmount",
        typeClass = MonetaryAmountType.class,
        defaultForType = MonetaryAmount.class
)
public class ProductVariant {
    @Id
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "version")
    private int version;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Type(type = "monetaryAmount")
    @Columns(columns = {
            @Column(name = "price_amount_monthly"),
            @Column(name = "price_currency_monthly")
    })
    private MonetaryAmount monthlyPrice;


    @LastModifiedDate
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;




}
