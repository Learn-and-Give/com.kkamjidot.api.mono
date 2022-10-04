package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@Entity(name = "User")
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_university_email")
    private String userUniversityEmail;

    @Column(name = "user_second_email")
    private String userSecondEmail;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "user_university")
    private String userUniversity;

    @Column(name = "user_major")
    private String userMajor;

    @Column(name = "user_minor")
    private String userMinor;

    @Column(name = "user_image_url")
    private String userImageUrl;

    @Column(name = "user_created_date")
    private LocalDateTime userCreatedDate;

    @Column(name = "user_modified_date")
    private LocalDateTime userModifiedDate;

    @Column(name = "user_deleted_date")
    private LocalDateTime userDeletedDate;

    @ToString.Exclude
    @OneToMany(mappedBy = "tokenUser")
    private List<NotificationToken> notificationTokens = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private Set<Complete> completes = new LinkedHashSet<>();

}