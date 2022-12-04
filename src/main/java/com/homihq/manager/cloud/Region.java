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
@Table(name = "t_region")
@EntityListeners(AuditingEntityListener.class)
public class Region {
    @Id
    private Long id;

    @Column(name = "datacenter")
    private String dataCenter;

    @Column(name = "region")
    private String region;

    @Column(name = "continent")
    private String continent;

    @Column(name = "slug")
    private String slug;

    @Column(name = "deleted")
    private boolean deleted;

    @LastModifiedDate
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
