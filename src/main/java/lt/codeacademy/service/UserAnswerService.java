package lt.codeacademy.service;

import lt.codeacademy.entity.UserAnswer;
import lt.codeacademy.repository.QuestionRepository;
import lt.codeacademy.repository.UserAnswerRepository;

public class UserAnswerService {


    private final UserAnswerRepository userAnswerRepository;

    public UserAnswerService() {
        this.userAnswerRepository = new UserAnswerRepository();
    }

    public void create(UserAnswer userAnswer) {
        userAnswerRepository.create(userAnswer);
    }
}
