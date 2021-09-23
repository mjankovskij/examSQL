package lt.codeacademy.repository;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.Question;
import lt.codeacademy.entity.User;

import java.util.List;

public class QuestionRepository extends AbstractRepository {

    public void createUpdate(Question question) {
        changeEntity(session -> session.saveOrUpdate(question));
    }

//    public List<Exam> getQuestions() {
//        return getEntityInformation(session -> session.createQuery("FROM Exams", Exam.class).list());
//    }

    public Question get(Long id) {
        return getEntityInformation(session -> session.get(Question.class, id));
    }

    public long countQuestions(Long exam_id) {
        return (long) getEntityInformation(
                session ->
                        session.createQuery("SELECT COUNT(*) FROM Question WHERE exam_id = " + exam_id)
                        .getSingleResult()
        );
    }

}