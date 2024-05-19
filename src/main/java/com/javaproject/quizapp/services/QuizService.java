package com.javaproject.quizapp.services;

import com.javaproject.quizapp.models.*;
import com.javaproject.quizapp.repositories.QuestionRepository;
import com.javaproject.quizapp.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuestionRepository questionRepository;
    public ResponseEntity<String> createQuiz(NewQuiz newQuiz) {
        String category = newQuiz.getCategory();
        Integer numQ = newQuiz.getNumQ();
        String title = newQuiz.getTitle();
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);

        quizRepository.save(quiz);

        return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuiz(String title) {
        Quiz quiz = quizRepository.findByTitle(title);
        List<Question> questionsFromDB = quiz.getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q : questionsFromDB) {
            QuestionWrapper qw = new
                    QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }


    public ResponseEntity<Integer> calculateScore(List<Response> responses) {
        Integer score = 0;
        List<Question> questionsFromDB = questionRepository.findAll();
        for(Question q : questionsFromDB) {
            Integer questionId = q.getId();
            String rightAnswer = q.getRightAnswer();
            for(Response response : responses) {
                if(questionId == response.getId()) {
                    if (rightAnswer.equals(response.getResponse())) score++;
                }
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return new ResponseEntity<>( quizRepository.findAll(), HttpStatus.OK);
    }
}
