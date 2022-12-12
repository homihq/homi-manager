package com.homihq.manager.gateway;

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
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "gateway_key")
    private String gatewayKey;

    @LastModifiedDate
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "route_version")
    private long routeVersion;

    @Type(type = "jsonb")
    @Column(name = "api_definitions")
    private List<ApiDefinition> apiDefinitions;

    @Type(type = "jsonb")
    @Column(name = "gateway_instances")
    private List<GatwayInstance> instances;

    public enum Status {
        CREATED("Created"),
        UP("UP"),
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
