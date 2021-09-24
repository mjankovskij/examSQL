package lt.codeacademy.service;

import lt.codeacademy.entity.Exam;
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

    public long totalAnswers(){
        return userAnswerRepository.totalAnswers();
    }

    public long totalAnswersInExam(Exam exam){
        return userAnswerRepository.totalAnswersInExam(exam);
    }

    public long totalCorrectAnswers(){
        return userAnswerRepository.totalCorrectAnswers();
    }

    public long totalCorrectAnswersInExam(Exam exam){
        return userAnswerRepository.totalCorrectAnswersInExam(exam);
    }

    public long averageCorrectAnswers(){
        ExamService examService = new ExamService();
        int totalExams = examService.getExams().size();
        int totalCorrectAnswers = examService.getExams().stream().mapToInt(e -> (int) totalCorrectAnswersInExam(e)).sum();
        return totalCorrectAnswers/totalExams;
    }

    public long answerSelectedTimes(char answer){
        return userAnswerRepository.answerSelectedTimes(answer);
    }

    public long answerSelectedTimesInExam(Exam exam, char answer){
        return userAnswerRepository.answerSelectedTimesInExam(exam, answer);
    }

}
