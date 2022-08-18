package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Week")
@Table(name = "week")
public class Week {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "week_id", nullable = false)
    private Long id;

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @Column(name = "week_start_date", nullable = false)
    private LocalDateTime weekStartDate;

    @Column(name = "`week_ period`", nullable = false)
    private Integer weekPeriod;

    @Column(name = "week_limit_num", nullable = false)
    private Integer weekLimitNum;

    @Column(name = "week_created_date")
    private LocalDateTime weekCreatedDate;

    @Column(name = "week_modified_date")
    private LocalDateTime weekModifiedDate;

    @Column(name = "week_deleted_date")
    private LocalDateTime weekDeletedDate;

}