package com.kkamjidot.api.mono.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@DynamicInsert
@DynamicUpdate
@Entity(name = "Complete")
@Table(name = "complete")
public class Complete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complete_id", nullable = false)
    private Long id;

    @Column(name = "week", nullable = false)
    private Integer week;

    @Column(name = "complete_created_date")
    private LocalDateTime completeCreatedDate;

    @Column(name = "complete_modified_date")
    private LocalDateTime completeModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chall_id", nullable = false)
    @ToString.Exclude
    private Challenge chall;

}