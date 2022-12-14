package com.kkamjidot.api.mono.repository.query;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.dto.response.QuizTotalCountByWeekResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkamjidot.api.mono.domain.QChallenge.challenge;
import static com.kkamjidot.api.mono.domain.QQuiz.quiz;
import static com.kkamjidot.api.mono.domain.QUser.user;

@Repository
public class QuizQueryRepository {
    private final EntityManager em;
    private final JPAQueryFactory query;

    public QuizQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Quiz> findByUserAndChallenge_IdAndQuizWeek(Long challengeId, List<Integer> weeks) {
        return query.select(quiz)
                .from(quiz)
//                .join(quiz.user, user)
//                .fetchJoin()
                .join(quiz.challenge, challenge)
                .where(challenge.id.eq(challengeId))
                .where(quiz.quizDeletedDate.isNull())
                .where(weekEq(weeks))
                .orderBy(quiz.quizCreatedDate.asc())
                .fetch();
    }

    private BooleanBuilder weekEq(List<Integer> weeks) {
        System.out.println(weeks);
        BooleanBuilder builder = new BooleanBuilder();
        for (int week : weeks) {
            builder.or(quiz.quizWeek.eq(week));
        }
        return builder;
    }

    public List<QuizTotalCountByWeekResponse> countQuizzesByUserId(Long userId) {
        return query.select(Projections.constructor(QuizTotalCountByWeekResponse.class, quiz.quizWeek.as("week"), quiz.count().as("count")))
                .from(quiz)
                .where(quiz.quizDeletedDate.isNull()
                        .and(user.id.eq(userId)))
                .groupBy(quiz.quizWeek)
                .fetch();
    }
}
