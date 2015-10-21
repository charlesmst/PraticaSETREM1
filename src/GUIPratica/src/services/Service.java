/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import components.ThrowingConsumer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.log4j.LogManager;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
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

    protected final Class<T> classRef;

    protected final org.apache.log4j.Logger logger;

    protected boolean autoClose = true;

    private void autoClose(Session s) {
        if (autoClose) {
            s.close();
        }
    }

    public Service(Class<T> classRef) {
        this.classRef = classRef;
        logger = LogManager.getLogger(classRef);
    }

    private Session session;

    public Session getSession() {
        if (session == null || !session.isConnected()) {
            session = HibernateUtil.getSessionFactory().openSession();
        }
        return session;
    }

    public T findById(Serializable id) {
        return findById(id, false);
    }
//

    protected Object selectOnSession(Function<Session, Object> f) throws ServiceException {
        Session s = getSession();
        synchronized (s) {
            try {
                return f.apply(s);
//            return r;
            } catch (Exception e) {
                throw new ServiceException(e.getMessage(), e);

            } finally {
                s.close();
            }
        }
    }

    protected void executeOnSession(Consumer<Session> f) throws ServiceException {
        Session s = getSession();
        synchronized (s) {
            try {
                f.accept(s);
            } catch (Exception e) {
                throw new ServiceException(e.getMessage(), e);

            } finally {
                s.close();
            }
        }
    }

    protected void executeOnTransaction(BiConsumer<Session, Transaction> f) throws ServiceException {
        Session s = getSession();
        synchronized (s) {
            Transaction t = s.beginTransaction();
            synchronized (t) {
                try {
                    f.accept(s, t);

                } catch (Exception e) {
                    t.rollback();
                    throw new ServiceException(e.getMessage(), e);

                } finally {
                    s.close();
                }
            }
        }
    }
//
//    protected <L> L executeOnTransaction(BiFunction<Session, Transaction, L> f) throws ServiceException {
//        Session s = getSession();
//        Transaction t = s.beginTransaction();
//        try {
//            L r = f.apply(s, t);
//            return r;
//        } catch (Exception e) {
//            t.rollback();
//            throw new ServiceException(e.getMessage(), e);
//
//        } finally {
//            s.close();
//        }
//    }

    public T findById(Serializable id, boolean full) {
        Session s = getSession();
        synchronized (s) {
            T obj;
            try {
                if (full) {
                    //@todo implementar load full
                    obj = (T) s.load(classRef, id);

                } else {
                    obj = (T) s.get(classRef, id);
                }

                return obj;
            } finally {
                autoClose(s);
            }
        }

    }

    public void insert(T obj) throws ServiceException {
        Session s = getSession();
        synchronized (s) {
            Transaction t = s.beginTransaction();
            try {
                s.save(obj);
                t.commit();

            } catch (ConstraintViolationException e) {
                t.rollback();
                throw new ServiceException("Erro ao inserir " + classRef.getSimpleName(), e, logger);
            } finally {
                autoClose(s);

            }
        }
//        s.close();
    }

    public void saveOrUpdate(T obj) throws ServiceException {
        Session s = getSession();
        synchronized (s) {
            Transaction t = s.beginTransaction();

            try {
                s.saveOrUpdate(obj);
                t.commit();
            } catch (ConstraintViolationException e) {
                t.rollback();
                throw new ServiceException("Erro ao alterar " + classRef.getSimpleName(), e, logger);
            } finally {
                autoClose(s);

            }
        }
//        s.close();
    }

    public void update(T obj) throws ServiceException {
        Session s = getSession();
        synchronized (s) {
            Transaction t = s.beginTransaction();

            try {
                s.merge(obj);
                t.commit();
            } catch (ConstraintViolationException e) {
                t.rollback();
                throw new ServiceException("Erro ao alterar " + classRef.getSimpleName(), e, logger);
            } finally {
                autoClose(s);

            }
        }
//        s.close();
    }

    public void delete(Serializable key) throws ServiceException {
        Session s = getSession();
        synchronized (s) {
            Transaction t = s.beginTransaction();

            try {
                Object persistentInstance = s.load(classRef, key);
                s.delete(persistentInstance);
                t.commit();
            } catch (ConstraintViolationException e) {
                t.rollback();
                throw new ServiceException("Erro ao excluir " + classRef.getSimpleName(), e, logger);
            } finally {
                autoClose(s);

            }
        }

    }

    public List<T> findAll(String order) {
        Session s = getSession();
        synchronized (s) {
            try {
                return s.createQuery("from " + classRef.getSimpleName() + " order by " + order).list();
            } finally {
                autoClose(s);
            }
        }
    }

    public List<T> findAll() {
        Session s = getSession();
        synchronized (s) {
            try {
                return s.createQuery("from " + classRef.getSimpleName()).list();
            } finally {
                autoClose(s);
            }
        }

    }

    protected List<T> findFilter(Criterion... no) throws ServiceException {
        Session s = getSession();
        synchronized (s) {
            try {
                Criteria cr = s.createCriteria(classRef);
                for (Criterion no1 : no) {
                    cr.add(no1);
                }
                return cr.list();
            } catch (ConstraintViolationException e) {
                throw new ServiceException("Erro ao selecionar registros de " + classRef.getSimpleName(), e, logger);
            } finally {
                autoClose(s);

            }
        }
    }
//
//    public Iterator findAllIterator() {
//        return getSession().createQuery("from " + classRef.getSimpleName()).iterate();
//    }

    public List<T> findBy(String column, Serializable value) {
        return findBy(column, value, "=");
    }

    public List<T> findBy(String column, Serializable value, String operador) {
        Session s = getSession();
        synchronized (s) {
            try {

                Criteria c = s.createCriteria(classRef);

                for (int i = 0; i < operador.length(); i++) {
                    char operador1 = operador.charAt(i);

                    switch (operador1) {
                        case '=':
                            c.add(Restrictions.eq(column, value));
                            break;
                        case '>':
                            c.add(Restrictions.gt(column, value));
                            break;
                        case '<':
                            c.add(Restrictions.lt(column, value));
                            break;
                    }
                }
                return c.list();
//                return s.createQuery("from " + classRef.getSimpleName() + " where " + column + " = ").list();
            } finally {
                autoClose(s);
            }
        }
//        return getSession();
    }

    public List<T> findByMultipleColumns(String valor, String order, String... colunas) throws ServiceException {
        Session s = getSession();
        synchronized (s) {
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
                    ArrayList<String> gone = new ArrayList<>();

                    for (int i = 0; i < colunas.length; i++) {
                        Type t = HibernateUtil.getColumnType(classRef, colunas[i]);
                        String coluna = colunas[i];
                        //Verify if is necessary to create an alias
                        if (coluna.contains(".")) {
                            String table = coluna.substring(0, coluna.indexOf("."));
                            if (!gone.contains(table)) {
                                gone.add(table);
                                String nome = Character.toString((char) ((char) 'A' + (char) gone.indexOf(table)));

                                c.createAlias(table, "tab" + nome);
                            }
                            //Adjust column name
                            colunas[i] = coluna.replaceAll("^[^\\.]+(\\..+)$", "tab" + Character.toString((char) ((char) 'A' + (char) gone.indexOf(table))) + "$1");

                        }
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

                List<T> r = c.list();
                return r;
            } catch (HibernateException e) {
                throw new ServiceException("Erro ao selecionar dados", e, logger);
            } finally {
                autoClose(s);
            }
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
