package com.kkamjidot.api.mono.domain;

import com.kkamjidot.api.mono.domain.enumerate.RateValue;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@ToString
@DynamicInsert
@DynamicUpdate
@Entity(name = "Rate")
@Table(name = "rate")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id", nullable = false)
    private Long id;

    @Column(name = "rate", length = 10)
    @Enumerated(EnumType.STRING)
    private RateValue rate;

    @Column(name = "rate_created_date", nullable = false)
    private Instant rateCreatedDate;

    @Column(name = "rate_modified_date")
    private Instant rateModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id", nullable = false)
    @ToString.Exclude
    private Quiz quiz;

    public void update(Rate rate) {
        setRate(rate.getRate());
    }
}