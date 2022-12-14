package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "NotificationScheduler")
@Table(name = "notification_scheduler")
public class NotificationScheduler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ns_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "ns_day", nullable = false)
    private Integer nsDay;

    @NotNull
    @Column(name = "ns_hour", nullable = false)
    private Integer nsHour;

    @NotNull
    @Column(name = "ns_created_date", nullable = false)
    private LocalDateTime nsCreatedDate;

    @Column(name = "ns_modified_date")
    private LocalDateTime nsModifiedDate;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nc_id", nullable = false)
    private NotificationContent notificationContent;

    @ToString.Exclude
    @OneToMany(mappedBy = "ns")
    private List<Notify> notifies = new LinkedList<>();
}