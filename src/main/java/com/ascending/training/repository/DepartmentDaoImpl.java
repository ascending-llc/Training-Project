/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.model.Account;
import com.ascending.training.model.Department;
import com.ascending.training.model.Employee;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

//@Repository
public class DepartmentDaoImpl implements DepartmentDao {
//    @Autowired private Logger logger;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Department save(Department department) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            session.save(department);
            transaction.commit();
            session.close();
            return department;
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("failure to insert record",e);
            session.close();
            return null;
        }
//        if (department!=null) logger.debug(String.format("The department %s was inserted into the table.", department.toString()));
    }

    @Override
    public Department update(Department department) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(department);
            transaction.commit();
            return department;
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("failure to update record",e.getMessage());
            return null;
        }
//        if (isSuccess) logger.debug(String.format("The department %s was updated.", department.toString()));
    }

    @Override
    public boolean delete(String deptName) {
        String hql = "DELETE Department where name = :deptName1";
        int deletedCount = 0;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Department> query = session.createQuery(hql);
            query.setParameter("deptName1", deptName);
            deletedCount = query.executeUpdate();
            transaction.commit();
            return deletedCount >= 1 ? true : false;
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Department dep) {
        //:Id placeholder
        String hql = "DELETE Department as dep where dep.id = :Id";
        int deletedCount = 0;
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            Query<Department> query = session.createQuery(hql);
            query.setParameter("Id", dep.getId());
            deletedCount = query.executeUpdate();
            transaction.commit();
            session.close();
            return deletedCount >= 1 ? true : false;
        }
        catch (HibernateException e) {
            if (transaction != null) transaction.rollback();
            session.close();
            logger.error("unable to delete record",e);
        }
//        logger.debug(String.format("The department %s was deleted", dep.getName()));
        return false;
    }

    @Override
    public List<Department> getDepartments() {
        //String hql = "FROM Department as dept left join fetch dept.employees as em left join fetch em.accounts";
        //String hql = "FROM Department as dept left join fetch dept.employees";
        List<Department> deps = new ArrayList<>();
        String hql = "FROM Department";
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Department> query = session.createQuery(hql);
            deps = query.list();
            //return query.list().stream().distinct().collect(Collectors.toList());
            //return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            session.close();
            return deps;
        }catch (HibernateException e){
            logger.error("failure to retrieve data record",e);
            session.close();
            return deps;
        }
    }

    public Department getDepartmentEagerBy(Long id){
//        select * from departments as dep left join employees as e on a.employee_id=dep.id where dep.id=:Id
        String hql = "FROM Department d LEFT JOIN FETCH d.employees where d.id=:Id";
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("Id",id);
            Department result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("failure to retrieve data record",e);
            session.close();
            return null;
        }
    }
    public Department getDepartmentBy(Long id){
        String hql = "FROM Department d where d.id=:Id";
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("Id",id);
            Department result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e){
            logger.error("failure to retrieve data record",e);
            session.close();
            return null;
        }
    }

    public List<Department> getDepartmentsWithChildren() {
        String hql = "FROM Department as dept left join fetch dept.employees as em left join fetch em.accounts";
        //String hql = "FROM Department as dept left join fetch dept.employees";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            //return query.list();
            //return query.list().stream().distinct().collect(Collectors.toList());
            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
    }

    @Override
    public Department getDepartmentByName(String deptName) {
        if (deptName == null) return null;
        String hql = "FROM Department as dept where lower(dept.name) = :name";
        //String hql = "FROM Department as dept where lower(dept.name) = :name";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());
            return query.uniqueResult();
        }
    }

    @Override
    //HR, hr, Hr deptName.toLowerCase() = hr
    //HR,hr,Hr lower(:name) = hr
    public Department getDepartmentAndEmployeesBy(String deptName) {
        if (deptName == null) return null;
        String hql = "FROM Department as dept left join fetch dept.employees where lower(dept.name) = :name";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Department> query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            Department result = query.uniqueResult();
            return result;
        }
    }

    @Override
    public List<Object[]> getDepartmentAndEmployeesAndAccounts(String deptName) {
        if (deptName == null) return null;

        String hql = "FROM Department as dept " +
                     "left join dept.employees as ems " +
                     "left join ems.accounts as acnts " +
                     "where lower(dept.name) = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query query = session.createQuery(hql);
            query.setParameter("name", deptName.toLowerCase());

            List<Object[]> resultList = query.list();

            for (Object[] obj : resultList) {
                logger.debug(((Department)obj[0]).toString());
                logger.debug(((Employee)obj[1]).toString());
                logger.debug(((Account)obj[2]).toString());
            }

            return resultList;
        }
    }
}
