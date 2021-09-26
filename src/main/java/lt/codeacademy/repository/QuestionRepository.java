package lt.codeacademy.repository;

import lt.codeacademy.entity.Exam;
import lt.codeacademy.entity.Question;

public class QuestionRepository extends AbstractRepository {

    public void createUpdate(Question question) {
        changeEntity(session -> session.saveOrUpdate(question));
    }

    public Question get(Long id) {
        return getEntityInformation(session -> session.get(Question.class, id));
    }

    public long countQuestions(Exam exam) {
        return (long) getEntityInformation(
                session ->
                        session.createQuery("SELECT COUNT(*) FROM Question WHERE exam = :exam")
                                .setParameter("exam", exam)
                                .getSingleResult()
        );
    }

}