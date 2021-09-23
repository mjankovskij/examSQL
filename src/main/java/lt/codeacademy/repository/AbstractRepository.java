package lt.codeacademy.repository;

import lt.codeacademy.provider.SessionFactoryProvider;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractRepository {

    protected void changeEntity(Consumer<Session> consumer) {
        Session session = null;
        Transaction transaction = null;
        try
        {
            session = SessionFactoryProvider.getInstance().getSessionFactory().openSession();
            transaction = session.beginTransaction();

            consumer.accept(session);

            transaction.commit();
        } catch(Exception e) {
            System.out.println(e);
            if(transaction != null){
                transaction.rollback();
            }
        }finally
        {
            if(session != null) {
                session.close();
            }
        }
    }

    protected  <T> T getEntityInformation(Function<Session, T> function){
        try(Session session = SessionFactoryProvider.getInstance().getSessionFactory().openSession()) {
            return function.apply(session);
        }catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
}