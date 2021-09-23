package lt.codeacademy.repository;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.User;
import lt.codeacademy.entity.UserExam;

import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class UserExamRepository extends AbstractRepository {

//    public void delete(Exam exam) {
//        changeEntity(session -> session.delete(exam));
//    }

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
                session.createQuery("FROM UserExam where user_id="+user_id+" and exam_id="+exam_id, UserExam.class)
                        .getResultList()
                        .stream()
                        .findFirst()
                        .orElse(null)
        );
    }
}