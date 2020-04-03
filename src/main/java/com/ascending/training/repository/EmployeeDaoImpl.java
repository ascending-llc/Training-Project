/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.repository;

import com.ascending.training.model.Department;
import com.ascending.training.model.Employee;
import com.ascending.training.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    @Autowired private Logger logger;
    @Autowired private DepartmentDao departmentDao;

    @Override
    public Employee getBy(Long id) {
        String hql = "FROM Employee e where e.id=:Id";
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("Id",id);
            Employee result = query.uniqueResult();
            session.close();
            return result;
        }catch (HibernateException e) {
            logger.error("failure to retrieve data record", e);
            session.close();
            return null;
        }
    }

    @Override
    public Employee save(Employee employee, String deptName) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Department department = departmentDao.getDepartmentByName(deptName);

            if (department != null) {
                transaction = session.beginTransaction();
                employee.setDepartment(department);
                session.save(employee);
                transaction.commit();
                return employee;
            }
            else {
                logger.debug(String.format("The department [%s] doesn't exist.", deptName));
                return null;
            }
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("The employee is not able to be saved",e);
            return null;
        }
    }

    @Override
    public Employee save(Employee employee) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            return employee;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("The employee is not able to be saved",e);
            return null;
        }
    }

    @Override
    public boolean delete(Employee employee) {
        String hql = "DELETE Employee as emp where emp.id = :Id";
        int deletedCount = 0;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Department> query = session.createQuery(hql);
            query.setParameter("Id", employee.getId());
            deletedCount = query.executeUpdate();
            transaction.commit();
            return deletedCount >= 1 ? true : false;
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("unable to delete record",e);
        }
        return false;
    }

    //    @Override
//    public int updateEmployeeAddress(String name, String address) {
//        String hql = "UPDATE Employee as em set em.address = :address where em.name = :name";
//        int updatedCount = 0;
//        Transaction transaction = null;
//
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            Query<Employee> query = session.createQuery(hql);
//            query.setParameter("name", name);
//            query.setParameter("address", address);
//
//            transaction = session.beginTransaction();
//            updatedCount = query.executeUpdate();
//            transaction.commit();
//        }
//        catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            logger.error(e.getMessage());
//        }
//
//        logger.debug(String.format("The employee %s was updated, total updated record(s): %d", name, updatedCount));
//
//        return updatedCount;
//    }
    @Override
    public Employee update(Employee employee){
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
            return employee;
        }
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("not able to save employee",e);
        }
//        logger.debug(String.format("The employee %s was updated, total updated record(s): %d", name, updatedCount));
        return employee;
    }

    @Override
    public List<Employee> getEmployees() {
        String hql = "FROM Employee em left join fetch em.accounts";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery(hql);
            return query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
    }

    @Override
    public Employee getEmployeeByName(String name) {
        String hql = "FROM Employee as em left join fetch em.accounts where em.name = :name";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Employee> query = session.createQuery(hql);
            query.setParameter("name", name);

            return query.uniqueResult();
        }
    }
}
