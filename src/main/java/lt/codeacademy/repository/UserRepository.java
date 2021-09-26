package lt.codeacademy.repository;

import lt.codeacademy.entity.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserRepository extends AbstractRepository {

    public void createUser(User user) {
        changeEntity(session -> session.save(user));
    }

    public User getUser(String name) {
        return getEntityInformation(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), name));
            return session.createQuery(criteriaQuery).uniqueResult();
        });
    }
}