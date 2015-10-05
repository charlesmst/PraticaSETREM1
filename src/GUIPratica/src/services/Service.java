/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.type.Type;
import utils.HibernateUtil;

/**
 *
 * @author Charles
 */
public abstract class Service<T> {

    private final Class<T> classRef;

    protected boolean autoClose = true;

    private void autoClose(Session s) {
        if (autoClose) {
            s.close();
        }
    }

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
        return findById(id, false);
    }

    public T findById(Serializable id, boolean full) {
        Session s = getSession();
        T obj = (T) s.get(classRef, id);
        if (full) {
            //@todo implementar load full
        }
        autoClose(s);
        return obj;
    }

    public void insert(T obj) throws ServiceException {
        Session s = getSession();
        try {
            Transaction t = s.beginTransaction();
            s.save(obj);
            t.commit();

        } catch (ConstraintViolationException e) {
            throw new ServiceException("Erro ao inserir " + classRef.getSimpleName(), e);
        } finally {
            autoClose(s);

        }
//        s.close();
    }

    public void update(T obj) throws ServiceException {
        Session s = getSession();

        try {
            Transaction t = s.beginTransaction();
            s.merge(obj);
            t.commit();
        } catch (ConstraintViolationException e) {
            throw new ServiceException("Erro ao alterar " + classRef.getSimpleName(), e);
        } finally {
            autoClose(s);

        }
//        s.close();
    }

    public void delete(Serializable key) throws ServiceException {
        Session s = getSession();

        try {
            Transaction t = s.beginTransaction();
            Object persistentInstance = s.load(classRef, key);
            s.delete(persistentInstance);
            t.commit();
        } catch (ConstraintViolationException e) {
            throw new ServiceException("Erro ao excluir " + classRef.getSimpleName(), e);
        } finally {
            autoClose(s);

        }

    }

    public Collection<T> findAll(String order) {
        return getSession().createQuery("from " + classRef.getSimpleName() + " order by " + order).list();
    }

    public Collection<T> findAll() {
        return getSession().createQuery("from " + classRef.getSimpleName()).list();
    }

    protected Collection<T> findFilter(Criterion[] no) throws ServiceException {
        Session s = getSession();
        try {
            Criteria cr = s.createCriteria(classRef);
            for (Criterion no1 : no) {
                cr.add(no1);
            }
            return cr.list();
        } catch (ConstraintViolationException e) {
            throw new ServiceException("Erro ao selecionar registros de " + classRef.getSimpleName(), e);
        } finally {
            s.close();

        }

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

    public Collection<T> findByMultipleColumns(String valor, String order, String... colunas) throws ServiceException {
        Session s = getSession();
        try {
            Criteria c = s.createCriteria(classRef);
            if (!valor.equals("")) {
                ArrayList<Criterion> cr = new ArrayList<>();
                boolean isInteger = false;
                try {
                    Integer.parseInt(valor);
                    isInteger = true;
                } catch (Exception e) {
                }
                for (int i = 0; i < colunas.length; i++) {
                    Type t = HibernateUtil.getColumnType(classRef, colunas[i]);
                    System.out.println(t);
                    if (t instanceof org.hibernate.type.IntegerType) {
                        if (isInteger) {
                            cr.add(Restrictions.eq(colunas[i], new Integer(valor)));
                        }
                    } else {
                        cr.add(Restrictions.like(colunas[i], "%" + valor.replaceAll("\\s+", "%") + "%"));

                    }
                }
                Criterion[] cra = new Criterion[cr.size()];
                for (int i = 0; i < cr.size(); i++) {
                    cra[i] = cr.get(i);
                }
                c.add(Restrictions.or(cra));
            }
            if (order != null) {
                c.addOrder(Order.asc(order));
            }
            Collection<T> r = c.list();
            return r;
        } catch (HibernateException e) {
            throw new ServiceException("Erro ao selecionar dados", e);
        } finally {
            autoClose(s);
        }

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
