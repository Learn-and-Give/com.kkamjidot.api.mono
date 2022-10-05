package com.kkamjidot.api.mono.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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
@Entity(name = "NotificationToken")
@Table(name = "notification_token")
public class NotificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", nullable = false)
    private Long id;

    @Size(max = 256)
    @Column(name = "token_value", nullable = false, length = 256)
    private String tokenValue;

    @Size(max = 256)
    @Column(name = "token_platform", nullable = false, length = 256)
    private String platform;

    @Column(name = "token_created_date", nullable = false)
    private LocalDateTime tokenCreatedDate;

    @Column(name = "token_modified_date")
    private LocalDateTime tokenModifiedDate;

    @Column(name = "token_deleted_date")
    private LocalDateTime tokenDeletedDate;

    @Size(max = 10)
    @Column(name = "token_desc", nullable = false, length = 10)
    private String tokenDesc;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_user_id")
    private User tokenUser;
}