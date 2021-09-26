package lt.codeacademy.service;

import lt.codeacademy.entity.*;
import lt.codeacademy.repository.UserExamRepository;

import java.util.List;

public class UserExamService {
    private final UserExamRepository userExamRepository;

    public UserExamService() {
        this.userExamRepository = new UserExamRepository();
    }

    public void create(UserExam userExam) {
        userExamRepository.create(userExam);
    }

    protected int getResult(UserExam userExam) {
        return (int) (10d / userExam.getExam().getQuestions().size() * userExamRepository.getResultSQL(userExam));
    }

    public int validate(UserExam userExam, List<UserAnswer> userAnswers) {
        int correctAnswers = (int) userAnswers.stream().filter(e -> e.getAnswer() == e.getQuestion().getCorrect()).count();
        return (int) (10d / userExam.getExam().getQuestions().size() * correctAnswers);
    }

    public List<UserExam> getAllSameUserExamsByExam(User user, Exam exam){
        return userExamRepository.getAllSameUserExamsByExam(user, exam);
    }
}
