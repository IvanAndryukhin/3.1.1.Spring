package net.javacode.spring_311.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import net.javacode.spring_311.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private final EntityManager entityManager;

    @Autowired
    UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //Добавляю юзера и показываю таблицу

    @Override
    public List<User> getUsersList() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    ////МЕТОДЫ ИЗМЕНЕНИЯ////

    @Override
    public void updateUser(User user) {
        User existingUser = getUser(user.getId());
        if (existingUser == null) {
            throw new EntityNotFoundException("User not found with id: " + user.getId());
        }
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setAddress(user.getAddress());
    }

    //Удаляю юзера

    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        } else {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

}


