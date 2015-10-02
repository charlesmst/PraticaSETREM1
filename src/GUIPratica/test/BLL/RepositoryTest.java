/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BLL;

import services.Service;
import java.io.Serializable;
import java.util.Collection;
import model.Marca;
import org.hibernate.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import services.ServiceException;
/**
 *
 * @author Charles
 */
public class RepositoryTest {
    
    public RepositoryTest() {
    }   
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insert method, of class Repository.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
        
        Marca m = new Marca();
        m.setNome("Teste");
        Service instance = new RepositoryImpl();
//        instance.insert(m);
        // TODO review the generated test code and remove the default call to fail.
        assertNotNull(instance.findById(m.getId()));
    }
    /**
     * Test of findById method, of class Repository.
     */
    @Test
    public void testFindById() {
        System.out.println("findById");
        int id = 1;
        Service instance = new RepositoryImpl();
        
        Object result = instance.findById(id);
        assertNotNull(result);
    }
    /**
     * Test of update method, of class Repository.
     */
    @Test
    public void testUpdate() throws ServiceException{
        System.out.println("update");
        RepositoryImpl instance = new RepositoryImpl();
                Marca obj = instance.findById(1);
                obj.setNome("Teste2");

        instance.update(obj);
    }

    /**
     * Test of delete method, of class Repository.
     */
    @Test
    public void testDelete() throws ServiceException {
        System.out.println("delete");
        Serializable key = 10;
        Service instance = new RepositoryImpl();
        instance.delete(key);
    }

    /**
     * Test of getAll method, of class Repository.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        Service instance = new RepositoryImpl();
        Collection result = instance.findAll();
        assertTrue(result.size() > 0);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    

    public class RepositoryImpl extends Service<Marca> {

        public RepositoryImpl() {
            super(Marca.class);
        }
    }
    
}
