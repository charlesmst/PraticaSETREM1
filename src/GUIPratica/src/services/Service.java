/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import utils.HibernateUtil;

/**
 *
 * @author Charles
 */
public abstract class Service<T> {

    private final Class<T> classRef;

    public Service(Class<T> classRef) {
        this.classRef = classRef;
    }

    private Session session;

    public Session getSession() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public T findById(Serializable id) {
        Session s = getSession();
        T obj = (T) s.get(classRef, id);

        return obj;
    }

    public T loadById(Serializable id) {
        Session s = getSession();
        T obj = (T) s.load(classRef, id);
        return obj;
    }

    public void insert(T obj) throws ServiceException{
        try{
        Session s = getSession();
        Transaction t = s.beginTransaction();
        s.save(obj);
        t.commit();
         } catch (ConstraintViolationException e) {
            throw new ServiceException("Erro ao inserir " + classRef.getSimpleName());
        }
//        s.close();
    }

    public void update(T obj) throws ServiceException {
        try {
            Session s = getSession();
            Transaction t = s.beginTransaction();
            s.merge(obj);
            t.commit();
        } catch (ConstraintViolationException e) {
            throw new ServiceException("Erro ao alterar " + classRef.getSimpleName());
        }
//        s.close();
    }

    public void delete(Serializable key) throws ServiceException {
        try {
            Session s = getSession();
            Transaction t = s.beginTransaction();
            Object persistentInstance = s.load(classRef, key);
            s.delete(persistentInstance);
            t.commit();
        } catch (ConstraintViolationException e) {
            throw new ServiceException("Erro ao excluir " + classRef.getSimpleName());
        }

    }

    public Collection<T> findAll() {
        return getSession().createQuery("from " + classRef.getSimpleName()).list();
    }

    protected Collection<T> findFilter(Criterion... no) {
        Criteria c = getSession().createCriteria(classRef);
        for (Criterion no1 : no) {
            c.add(no1);
        }
        return c.list();
    }

    public Iterator findAllIterator() {
        return getSession().createQuery("from " + classRef.getSimpleName()).iterate();
    }

    public Collection<T> findBy(String column, Serializable value) {
        return findBy(column, value, "=");
    }

    public Collection<T> findBy(String column, Serializable value, String operador) {
        return getSession().createQuery("from " + classRef.getSimpleName() + " where " + column + " = ").list();
    }

    public void close() {

        if (session != null || session.isOpen()) {
            session.close();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }
}
