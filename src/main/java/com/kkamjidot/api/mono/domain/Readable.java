package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Readable")
@Table(name = "readable")
public class Readable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "readable_id", nullable = false)
    private Long id;

    @Column(name = "is_readable", nullable = false)
    private Boolean isReadable = false;

    @Column(name = "week", nullable = false)
    private Integer week;

    @Column(name = "readable_created_date")
    private LocalDateTime readableCreatedDate;

    @Column(name = "readable_modified_date")
    private LocalDateTime readableModifiedDate;

    @Column(name = "readable_deleted_date")
    private LocalDateTime readableDeletedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chall_id", nullable = false)
    private Challenge chall;

}