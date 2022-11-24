package com.homihq.manager.gateway;


import com.homihq.manager.cloud.CloudGatewayPlan;
import com.homihq.manager.cloud.CloudProvider;
import com.homihq.manager.cloud.CloudRegion;
import com.homihq.manager.gateway.digitalocean.DigitalOceanSpec;
import com.homihq.manager.project.domain.Project;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "t_gateway")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({

        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),

})

public class Gateway {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted")
    private boolean deleted;

    @OneToOne
    @JoinColumn(name = "project_id",referencedColumnName = "id")
    private Project project;

    @Type(type = "jsonb")
    @Column(name = "cloud_provider")
    private CloudProvider cloudProvider; //cannot be changed from UI after creation

    @Type(type = "jsonb")
    @Column(name = "cloud_region")
    private CloudRegion cloudRegion; //cannot be changed from UI after creation

    @Type(type = "jsonb")
    @Column(name = "cloud_gateway_plan")
    private CloudGatewayPlan cloudGatewayPlan; //cannot be changed from UI after creation

    @Type(type = "jsonb")
    @Column(name = "digital_ocean_app_spec", columnDefinition = "jsonb")
    private DigitalOceanSpec digitalOceanSpec;

    @Column(name = "digital_ocean_app_id")
    private String digitalOceanAppId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GatewayStatus status;

    @LastModifiedDate
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public enum GatewayStatus {
        CREATED("Created"),
        PROVISIONED("Provisioned"),
        READY("Ready"),
        DOWN("Down"),
        DELETED("Deleted");

        private final String value;

        GatewayStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }



}
