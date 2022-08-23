package com.kkamjidot.api.mono.domain;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "TakeAClass")
@Table(name = "take_a_class")
public class TakeAClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tc_id", nullable = false)
    private Long id;

    @Column(name = "tc_approval", nullable = false)
    private Boolean tcApproval = false;

    @Column(name = "tc_created_date")
    private LocalDateTime tcCreatedDate;

    @Column(name = "tc_modified_date")
    private LocalDateTime tcModifiedDate;

    @Column(name = "tc_deleted_date")
    private LocalDateTime tcDeletedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chall_id", nullable = false)
    private Challenge chall;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}