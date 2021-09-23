package lt.codeacademy.service;

import lt.codeacademy.entity.Question;
import lt.codeacademy.entity.User;
import lt.codeacademy.entity.UserAnswer;
import lt.codeacademy.entity.UserExam;
import lt.codeacademy.repository.UserExamRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserExamService {
    private final UserExamRepository userExamRepository;

    public UserExamService() {
        this.userExamRepository = new UserExamRepository();
    }

    public UserExam getByUserAndExamIds(long user_id, long exam_id){
        return userExamRepository.getByUserAndExamIds(user_id, exam_id);
    }

    public void create(UserExam userExam) {
        userExamRepository.create(userExam);
    }

    protected int getResult(UserExam userExam){
        return (int) (10d / userExam.getExam().getQuestions().size() * userExamRepository.getResultSQL(userExam));
    }


}
