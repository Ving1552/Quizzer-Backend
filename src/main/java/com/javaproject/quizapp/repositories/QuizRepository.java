package com.javaproject.quizapp.repositories;

import com.javaproject.quizapp.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    Quiz findByTitle(String title);
}
