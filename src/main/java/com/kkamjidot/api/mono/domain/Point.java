package com.kkamjidot.api.mono.domain;

import com.kkamjidot.api.mono.domain.enumerate.PointType;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
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
    @Column(name = "poi_value", nullable = false)
    private Integer value;

    @Size(max = 255)
    @Column(name = "poi_content")
    private String content;

    @Size(max = 20)
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "poi_type", nullable = false, length = 20)
    private PointType poiType;

    @Size(max = 20)
    @Column(name = "poi_related_id", length = 20)
    private String relatedId;

    @Column(name = "poi_created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "poi_modified_date")
    private LocalDateTime modifiedDate;

    @ToString.Exclude
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}