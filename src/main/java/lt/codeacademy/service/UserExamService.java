package lt.codeacademy.service;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.UserExam;
import lt.codeacademy.repository.UserExamRepository;

public class UserExamService {
    private final UserExamRepository userExamRepository;

    public UserExamService() {
        this.userExamRepository = new UserExamRepository();
    }

    public UserExam getByUserAndExamIds(long user_id, long exam_id){
        return userExamRepository.getByUserAndExamIds(user_id, exam_id);
    }

    public void delete(Exam exam) {
        userExamRepository.delete(exam);
    }



}
