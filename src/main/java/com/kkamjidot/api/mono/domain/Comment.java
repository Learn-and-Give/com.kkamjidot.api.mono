package com.kkamjidot.api.mono.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@DynamicInsert
@DynamicUpdate
@Entity(name = "Comment")
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @Column(name = "comment_content", nullable = false, length = 4000)
    private String commentContent;

    @Column(name = "comment_created_date")
    private LocalDateTime commentCreatedDate;

    @Column(name = "comment_modified_date")
    private LocalDateTime commentModifiedDate;

    @Column(name = "comment_deleted_date")
    private LocalDateTime commentDeletedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    @ToString.Exclude
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    public boolean isMine(User user) {
        return Objects.equals(this.user.getId(), user.getId());
    }
}