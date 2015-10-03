/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.SessionFactory;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.type.Type;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Charles
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    private static final AnnotationConfiguration conf;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            conf = new AnnotationConfiguration().configure();
            sessionFactory = conf.buildSessionFactory();
            
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static Type getColumnType(Class classRef, String column){
        PersistentClass P = conf.getClassMapping(classRef.getName());
        return P.getProperty(column).getType();
       
    }
}
