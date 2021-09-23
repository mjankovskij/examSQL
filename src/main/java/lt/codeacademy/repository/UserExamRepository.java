package lt.codeacademy.repository;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.User;
import lt.codeacademy.entity.UserExam;

import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class UserExamRepository extends AbstractRepository {

    public void delete(Exam exam) {
        changeEntity(session -> {
            Query query = session.createQuery("delete UserExam where exam_id=:exam");
            query.setParameter("exam", exam);
            query.executeUpdate();
        });
    }

    public void create(UserExam userExam) {
        changeEntity(session -> session.save(userExam));
    }

    public UserExam getByUserAndExamIds(long user_id, long exam_id) {
        return getEntityInformation(session ->
                session.createQuery("FROM UserExam WHERE user_id=:user_id AND exam_id=:exam_id", UserExam.class)
                        .setParameter("user_id", user_id)
                        .setParameter("exam_id", exam_id)
                        .getResultList()
                        .stream()
                        .findFirst()
                        .orElse(null)
        );
    }

    public long getResultSQL(UserExam userExam) {
        return (long) getEntityInformation(
                session ->
                        session.createQuery(
                                "SELECT COUNT(*) " +
                                        "FROM UserExam ue " +
                                        "INNER JOIN UserAnswer ua " +
                                        "ON ue.id = ua.userExam " +
                                        "INNER JOIN Question q " +
                                        "ON ue.exam = q.exam " +
                                        "WHERE ue.user = :user AND ue.exam = :exam AND ua.answer = q.correct AND ua.questionId = q.id"
                        )
                                .setParameter("user", userExam.getUser())
                                .setParameter("exam", userExam.getExam())
                                .getSingleResult()
        );
    }

}