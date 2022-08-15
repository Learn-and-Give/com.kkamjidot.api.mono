package com.kkamjidot.api.mono.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "User")
@Table(name = "user")
public class User extends BaseTimeEntity {
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

}