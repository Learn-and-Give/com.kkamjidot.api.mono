package com.kkamjidot.api.mono.domain;

import com.kkamjidot.api.mono.domain.enumerate.PointType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DynamicInsert
@DynamicUpdate
@Entity(name = "Point")
@Table(name = "point")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "poi_id", nullable = false)
    private Long id;

    @NotNull
    @Min(0)
    @Column(name = "poi_value", nullable = false)
    private Integer poiValue;

    @Size(max = 255)
    @Column(name = "poi_desc")
    private String poiDesc;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "poi_type", nullable = false, length = 20)
    private PointType poiType;

    @NotNull
    @Column(name = "poi_is_increase", nullable = false)
    private Boolean poiIsIncrease;

    @NotNull
    @Column(name = "poi_balance", nullable = false)
    private Integer poiBalance;

    @Size(max = 20)
    @Column(name = "poi_related_id", length = 20)
    private String poiRelatedId;

    @NotNull
    @Column(name = "poi_datetime", nullable = false)
    private LocalDateTime poiDatetime;

    @Column(name = "poi_created_date", nullable = false)
    private LocalDateTime poiCreatedDate;

    @Column(name = "poi_modified_date")
    private LocalDateTime poiModifiedDate;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Point(Integer poiValue, String poiDesc, PointType poiType, Boolean poiIsIncrease, String poiRelatedId, User user, Integer preBalance) {
        this.poiValue = poiValue;
        this.poiDesc = poiDesc;
        this.poiType = poiType;
        this.poiIsIncrease = poiIsIncrease;
        this.poiRelatedId = poiRelatedId;
        this.user = user;

        this.poiDatetime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        this.poiBalance = this.poiIsIncrease ? preBalance + poiValue : preBalance - poiValue;
    }
}