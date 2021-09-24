package lt.codeacademy.repository;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.User;
import lt.codeacademy.entity.UserAnswer;
import lt.codeacademy.entity.UserExam;
import org.hibernate.TransientPropertyValueException;

public class UserAnswerRepository extends AbstractRepository {

    public void create(UserAnswer userAnswer) {
        changeEntity(session -> session.save(userAnswer));
    }

    public long totalAnswers() {
        return (long) getEntityInformation(
                session ->
                        session.createQuery("SELECT COUNT(*) FROM UserAnswer")
                                .getSingleResult()
        );
    }

    public long totalAnswersInExam(Exam exam) {
        return (long) getEntityInformation(
                session ->
                        session.createQuery("SELECT COUNT(*) FROM UserAnswer ua " +
                                "INNER JOIN UserExam ue " +
                                "ON ua.userExam = ue.id " +
                                " WHERE ue.exam = :exam")
                                .setParameter("exam", exam)
                                .getSingleResult()
        );
    }

    public long totalCorrectAnswers() {
        return (long) getEntityInformation(
                session ->
                        session.createQuery("SELECT COUNT(*) FROM UserAnswer ua " +
                                "INNER JOIN Question q " +
                                "ON ua.question = q.id " +
                                " WHERE ua.answer = q.correct")
                                .getSingleResult()
        );
    }

    public long totalCorrectAnswersInExam(Exam exam) {
        return (long) getEntityInformation(
                session ->
                        session.createQuery("SELECT COUNT(*) FROM UserAnswer ua " +
                                "INNER JOIN UserExam ue " +
                                "ON ua.userExam = ue.id " +
                                "INNER JOIN Question q " +
                                "ON ua.question = q.id " +
                                " WHERE ua.answer = q.correct " +
                                "AND ue.exam = :exam")
                                .setParameter("exam", exam)
                                .getSingleResult()
        );
    }

    public long answerSelectedTimes(char answer){
        return (long) getEntityInformation(
                session ->
                        session.createQuery("SELECT COUNT(*) FROM UserAnswer WHERE answer = :answer")
                                .setParameter("answer", answer)
                                .getSingleResult()
        );
    }

    public long answerSelectedTimesInExam(Exam exam, char answer){
        return (long) getEntityInformation(
                session ->
                        session.createQuery("SELECT COUNT(*) FROM UserAnswer ua " +
                                "INNER JOIN UserExam ue " +
                                "ON ua.userExam = ue.id " +
                                "WHERE ue.exam = :exam " +
                                "AND ua.answer = :answer")
                                .setParameter("exam", exam)
                                .setParameter("answer", answer)
                                .getSingleResult()
        );
    }
}