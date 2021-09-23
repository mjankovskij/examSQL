package lt.codeacademy.repository;

import lt.codeacademy.entity.User;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//    public User getUser(Long id) {
//        return getEntityInformation(session -> session.get(User.class, id));
//    }
//    public List<User> getUsers() {
//        return getEntityInformation(session -> session.createQuery("FROM User", User.class).list());
//    }
//    public List<String> getUserNames() {
//        return getEntityInformation(session -> {
//            Query query = session.createQuery("select name from User", String.class);
//            return query.list();
//        });
//    }

//    public void updateUser(User user) {
//        changeEntity(session -> session.update(user));
//    }
//
//    public void updateUserNameById(Long id, String name) {
//        changeEntity(session -> {
//            Query query = session.createQuery("update User set name=:name where id=:id");
//            query.setParameter("name", name);
//            query.setParameter("id", id);
//
//            query.executeUpdate();
//        });
//    }
//
//    public void delete(User user) {
//        changeEntity(session -> session.delete(user));
//    }
//
//    public void deleteById(Long id) {
//        User user = new User();
//        user.setId(id);
//        changeEntity(session -> session.delete(user));
//    }
//
//    public void deleteByName(String name) {
//        changeEntity(session -> {
//            Query query = session.createQuery("delete User where name=:name");
//            query.setParameter("name", name);
//            query.executeUpdate();
//        });
//    }

//    public User getUser(String name) {
//        return getEntityInformation(session -> session.get(User.class, name));
//    }

//    public Set<User> getCriteriaUsers() {
//        return getEntityInformation(session -> {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//
//            CriteriaQuery<Company> criteriaQuery = criteriaBuilder.createQuery(Company.class);
//            Root<Company> root = criteriaQuery.from(Company.class);
//            //criteriaQuery.select(root); //visu irasu istraukimas
//            //criteriaQuery.select(root).where(criteriaBuilder.like(root.get("name"), "%as")); // istrauksime visus userius kuriu vardai baigaisi as
//            Predicate surname = criteriaBuilder.equal(root.get("name"), "Kazkas");
//            //Predicate name = criteriaBuilder.like(root.get("name"), "%as");
//            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("name"), "Kazkas"));
//
//            List<Company> list = session.createQuery(criteriaQuery).list();
//
//            Set<User> user = new HashSet<>();
//            list.forEach(k-> user.addAll(k.getUsers()));
//            return user;
//        });
//    }
}