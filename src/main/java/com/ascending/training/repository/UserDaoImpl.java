/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.model.Role;
import com.ascending.training.model.User;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
    private Logger logger=LoggerFactory.getLogger(getClass());

    @Override
    public boolean save(User user) {
        Transaction transaction = null;
        boolean isSuccess = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        }
        catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error("error saving record",e);
        }finally {
            session.close();
        }

        if (isSuccess) logger.debug(String.format("The user %s was inserted into the table.", user.toString()));

        return isSuccess;
    }

    @Override
    public User getUserById(Long Id) {
        String hql = "FROM User as u where u.id = :id";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("id", Id);
            return query.uniqueResult();
        }
    }


    @Override
    public User getUserByEmail(String email) {
        String hql = "FROM User as u where lower(u.email) = :email";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase());

            return query.uniqueResult();
        }
    }

    @Override
    public User getUserByCredentials(String email, String password) throws Exception {
        String hql = "FROM User as u where (lower(u.email) = :email or lower(u.name) =:email) and u.password = :password";
        logger.debug(String.format("User email: %s, password: %s", email, password));

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery(hql);
            query.setParameter("email", email.toLowerCase().trim());
            query.setParameter("password", password);

            return query.uniqueResult();
        }
        catch (Exception e){
            throw new Exception("can't find user record or session");
        }
    }


    @Override
    public User addRole(User user, Role role) {
        List<Role> roleSet;
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = s.beginTransaction();
        try {
            roleSet = user.getRoles();
            roleSet.add(role);
            user.setRoles(roleSet);
            s.saveOrUpdate(user);
            transaction.commit();
        } catch (HibernateException e) {
            logger.error("session exception, try again");
            transaction.rollback();
        } finally {
            s.close();
        }
        return user;
    }
    @Override
    public void delete(User user) {
        String hql = "DELETE FROM User as u where u.id=:Id";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Query<User> query = session.createQuery(hql);
            query.setParameter("Id", user.getId());
            query.executeUpdate();
            transaction.commit();
        }catch (HibernateException he){
            logger.error("error",he);
        } finally{
            session.close();
        }
    }
}
