package com.homihq.manager.gateway;


import com.homihq.manager.gateway.deployment.digitalocean.DigitalOceanRedis;
import com.homihq.manager.product.Region;
import com.homihq.manager.gateway.deployment.digitalocean.DigitalOceanApp;
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

    @Column(name = "do_project_id")
    private String doProjectId;

    @Column(name = "do_api_token")
    private String doApiToken;

    @Type(type = "jsonb")
    @Column(name = "do_app_spec", columnDefinition = "jsonb")
    private DigitalOceanApp doAppSpec;

    @Type(type = "jsonb")
    @Column(name = "do_redis_spec", columnDefinition = "jsonb")
    private DigitalOceanRedis doRedis;

    @Column(name = "do_app_id")
    private String doAppId;

    @Column(name = "do_db_id")
    private String doDbId;

    @Type(type = "jsonb")
    @Column(name = "region", columnDefinition = "jsonb")
    private Region region;

    @Enumerated(EnumType.STRING)
    @Column(name = "do_redis_status")
    private Status redisStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "do_app_status")
    private Status appStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @LastModifiedDate
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "container_id")
    private Integer containerId;

    @Column(name = "db_id")
    private Integer dbId;

    @Column(name = "no_of_container_instances")
    private Integer containerCount;

    @Column(name = "db_standby")
    private boolean dbStandBy;

    public enum Status {
        SUBMITTED("Submitted"),
        PROVISIONED("Provisioned"),
        READY("Ready"),
        DOWN("Down"),
        DELETED("Deleted");

        private final String value;

        Status(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }



}
