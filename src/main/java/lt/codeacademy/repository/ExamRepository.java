package lt.codeacademy.repository;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.Question;
import lt.codeacademy.entity.User;

import javax.persistence.Query;
import java.util.List;

public class ExamRepository extends AbstractRepository {

    public void createUpdate(Exam exam) {
        changeEntity(session -> session.saveOrUpdate(exam));
    }
    public void delete(Exam exam) {
        changeEntity(session -> session.delete(exam));
    }

    public List<Exam> getExams() {
        return getEntityInformation(session -> session.createQuery("FROM Exam", Exam.class).list());
    }

    public Exam getExam(Long id) {
        return getEntityInformation(session -> session.get(Exam.class, id));
    }

}