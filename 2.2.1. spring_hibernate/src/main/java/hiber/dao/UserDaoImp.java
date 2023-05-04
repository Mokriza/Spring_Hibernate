package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    private final static String HQL_GET_USER_BY_CAR = "FROM User as user " +
            "INNER JOIN FETCH user.car as userCar " +
            "WHERE userCar.model=:carModel AND userCar.series=:carSeries";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsersList() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCarModelAndSeries(String model, int series) {
        try {
            TypedQuery<User> result = sessionFactory.getCurrentSession()
                    .createQuery(HQL_GET_USER_BY_CAR)
                    .setParameter("carModel", model)
                    .setParameter("carSeries", series);
            return result.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Пользователя с указанной моделью и серией машины найдено не было");
        } catch (NonUniqueResultException e) {
            System.out.println("Было найдено несколько пользователей с указанной моделью и серией машины");
        }
        return null;
    }
}
