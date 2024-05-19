package com.javaproject.quizapp.services;

import com.javaproject.quizapp.models.Question;
import com.javaproject.quizapp.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionRepository.save(question);
            return new ResponseEntity<>("Question added successfully", HttpStatus.CREATED);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Request failed", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(Integer id, Question question) {
        try {
            Optional<Question> questionToBeUpdated = questionRepository.findById(id);
            if(questionToBeUpdated.isPresent()) {
                questionToBeUpdated.get().setQuestionTitle(question.getQuestionTitle());
                questionToBeUpdated.get().setOption1(question.getOption1());
                questionToBeUpdated.get().setOption2(question.getOption2());
                questionToBeUpdated.get().setOption3(question.getOption3());
                questionToBeUpdated.get().setOption4(question.getOption4());
                questionToBeUpdated.get().setCategory(question.getCategory());
                questionToBeUpdated.get().setDifficultyLevel(question.getDifficultyLevel());
                questionToBeUpdated.get().setRightAnswer(question.getRightAnswer());
                return new ResponseEntity<>("Question Successfully Updated", HttpStatus.OK);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to update question", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        try {
            Optional<Question> questionToBeDeleted = questionRepository.findById(id);
            if(questionToBeDeleted.isPresent()) {
                questionRepository.delete(questionToBeDeleted.get());
            }
            return new ResponseEntity<>("Question Successfully Deleted", HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to delete question", HttpStatus.BAD_REQUEST);
    }
}
