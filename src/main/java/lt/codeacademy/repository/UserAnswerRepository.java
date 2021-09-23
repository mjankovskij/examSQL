package lt.codeacademy.repository;

import lt.codeacademy.entity.User;
import lt.codeacademy.entity.UserAnswer;
import lt.codeacademy.entity.UserExam;
import org.hibernate.TransientPropertyValueException;

public class UserAnswerRepository extends AbstractRepository {

    public void create(UserAnswer userAnswer) {
        changeEntity(session -> session.save(userAnswer));
    }
}