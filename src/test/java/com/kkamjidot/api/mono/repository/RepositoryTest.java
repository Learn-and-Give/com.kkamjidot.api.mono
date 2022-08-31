package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.Quiz;
import com.kkamjidot.api.mono.domain.Solve;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("local")
class RepositoryTest {
    @Autowired
    QuizRepository quizRepository;

    @Test
    @DisplayName("Repository Test")
    void repositoryTest() {
        //given
        //when
        //then
        System.out.println(Solve.builder().solveAnswer("안녕").build());
    }
}