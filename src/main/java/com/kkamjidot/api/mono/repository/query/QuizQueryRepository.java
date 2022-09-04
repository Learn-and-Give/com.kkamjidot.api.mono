package com.kkamjidot.api.mono.repository.query;

import com.kkamjidot.api.mono.domain.Challenge;
import com.kkamjidot.api.mono.domain.QUser;
import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.kkamjidot.api.mono.domain.QQuiz.*;
import static com.kkamjidot.api.mono.domain.QUser.*;
import static com.kkamjidot.api.mono.domain.QChallenge.*;

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
                .join(quiz.user, user)
                .join(quiz.challenge, challenge)
                .on(challenge.id.eq(challengeId))
                .where(weekEq(weeks))
                .where(quiz.quizDeletedDate.isNull())
                .orderBy(quiz.quizWeek.asc())
                .fetch();
    }

    private BooleanBuilder weekEq(List<Integer> weeks) {
        BooleanBuilder builder = new BooleanBuilder();
        for (int week : weeks) {
            builder.or(quiz.quizWeek.eq(week));
        }
        return builder;
    }
}
