package lt.codeacademy.repository;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.User;
import lt.codeacademy.entity.UserExam;

import java.util.List;

public class UserExamRepository extends AbstractRepository {

    public List<UserExam> getAllSameUserExamsByExam(User user, Exam exam) {
        return getEntityInformation(session ->
                session.createQuery("FROM UserExam WHERE user=:user AND exam=:exam", UserExam.class)
                        .setParameter("user", user)
                        .setParameter("exam", exam)
                        .getResultList()
        );
    }

    public void create(UserExam userExam) {
        changeEntity(session -> session.save(userExam));
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
                                        "WHERE ue.user = :user " +
                                        "AND ue.exam = :exam " +
                                        "AND ua.answer = q.correct " +
                                        "AND ua.questionId = q.id " +
                                        "AND ue.id = :id"
                        )
                                .setParameter("id", userExam.getId())
                                .setParameter("user", userExam.getUser())
                                .setParameter("exam", userExam.getExam())
                                .getSingleResult()
        );
    }

}