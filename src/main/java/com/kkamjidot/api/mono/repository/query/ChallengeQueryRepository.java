package com.kkamjidot.api.mono.repository.query;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.enumerate.ApplicationStatus;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.kkamjidot.api.mono.domain.QChallenge.challenge;
import static com.kkamjidot.api.mono.domain.QTakeAClass.takeAClass;
import static com.kkamjidot.api.mono.domain.QUser.user;

@Repository
public class ChallengeQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public ChallengeQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    /**
     * 한 유저가 수강 중(진행 중)인 모든 챌린지 조회
     * @param userId
     * @return
     */
    public List<Challenge> findRunningChallengesByUserId(Long userId) {
        return query.select(challenge)
                .from(challenge)
                .where(challenge.in(query.select(takeAClass.chall)
                        .from(takeAClass)
                        .where(user.id.eq(userId)
                                .and(takeAClass.tcApplicationstatus.eq(ApplicationStatus.ACCEPTED))))
                        .and(challenge.challStartDate.before(LocalDateTime.now(ZoneId.of("Asia/Seoul"))))
                        .and(challenge.challEndDate.after(LocalDateTime.now(ZoneId.of("Asia/Seoul")))))
                .fetch();
                
    }
}
