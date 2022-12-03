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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "t_cloud")
@EntityListeners(AuditingEntityListener.class)
public class Cloud {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "serial_no")
    private int serialNo;

    @Column(name = "active")
    private boolean active;

    @LastModifiedDate
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Version
    private int version;
}
