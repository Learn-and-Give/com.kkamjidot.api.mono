package com.kkamjidot.api.mono.domain;

import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.context.properties.bind.DefaultValue;

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
@Entity(name = "TakeAClass")
@Table(name = "take_a_class")
public class TakeAClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tc_id", nullable = false)
    private Long id;

    @Column(name = "tc_application_status", nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus tcApplicationstatus;

    @Column(name = "tc_created_date")
    private LocalDateTime tcCreatedDate;

    @Column(name = "tc_modified_date")
    private LocalDateTime tcModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chall_id", nullable = false)
    private Challenge chall;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}