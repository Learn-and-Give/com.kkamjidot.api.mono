package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "Notify")
@Table(name = "notify")
public class Notify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notify_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ns_id", nullable = false)
    @ToString.Exclude
    private NotificationScheduler ns;

    @NotNull
    @Column(name = "ns_created_date", nullable = false)
    private LocalDateTime nsCreatedDate;

    @Column(name = "ns_modified_date")
    private LocalDateTime nsModifiedDate;

}