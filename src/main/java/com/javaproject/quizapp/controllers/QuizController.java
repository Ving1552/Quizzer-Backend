package com.javaproject.quizapp.controllers;

import com.javaproject.quizapp.models.NewQuiz;
import com.javaproject.quizapp.models.QuestionWrapper;
import com.javaproject.quizapp.models.Quiz;
import com.javaproject.quizapp.models.Response;
import com.javaproject.quizapp.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("https://quizzer-flax.vercel.app")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping("/createQuiz")
    public ResponseEntity<String> createQuiz(@RequestBody NewQuiz newQuiz) {
        return quizService.createQuiz(newQuiz);
    }

    @GetMapping("/getQuiz/{title}")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(@PathVariable String title) {
        return quizService.getQuiz(title);
    }

    @GetMapping("/getAllQuizzes")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @PostMapping("submitResponses")
    public ResponseEntity<Integer> submitResponses(@RequestBody List<Response> responses) {
        return quizService.calculateScore(responses);
    }
}
