package com.kkamjidot.api.mono.domain;

import lombok.*;

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
@Entity(name = "NotificationContent")
@Table(name = "notification_content")
public class NotificationContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nc_id", nullable = false)
    private Long id;

    @Size(max = 1000)
    @NotNull
    @Column(name = "nc_content", nullable = false, length = 1000)
    private String ncContent;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ns_id", nullable = false)
    private NotificationScheduler ns;

    @NotNull
    @Column(name = "nc_created_date", nullable = false)
    private LocalDateTime ncCreatedDate;

    @Column(name = "nc_modified_date")
    private LocalDateTime ncModifiedDate;

}