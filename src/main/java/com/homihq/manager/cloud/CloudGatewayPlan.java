package com.homihq.manager.cloud;

import com.homihq.manager.gateway.digitalocean.DigitalOceanSpec;
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
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "t_cloud_gateway_plan")
@EntityListeners(AuditingEntityListener.class)
public class CloudGatewayPlan {
    @Id
    private Integer id;

    @Column(name = "name")
    private String name;

    @Type(type = "jsonb")
    @Column(name = "features", columnDefinition = "jsonb")
    private List<String> features;


    @Column(name = "price")
    private int price;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "currency_symbol")
    private String currencySymbol;

    @Column(name = "currency_name")
    private int currencyName;

    @Column(name = "deleted")
    private boolean deleted;


    @LastModifiedDate
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToOne
    @JoinColumn(name="cloud_provider_id" , referencedColumnName = "id")
    private CloudProvider cloudProvider;
}
