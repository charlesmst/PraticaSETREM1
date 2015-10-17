/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.util.Iterator;
import services.aul.MarcaService;
import services.Service;
import java.util.List;
import model.aula.MarcaR;
import model.Usuario;
import model.aula.Produto;
import model.aula.Segmento;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import services.UsuarioService;
import services.aul.SegmentoService;
import utils.HibernateUtil;

/**
 *
 * @author Charles
 */
public class Tests {

    public static void main(String[] args) throws Exception {
//        Type t = HibernateUtil.getColumnType(Usuario.class, "id_pessoa");
//        System.out.println(t);
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        
//        UsuarioService service = new UsuarioService();
//        for (int i = 0; i < 10; i++) {
//            Usuario u = new Usuario();
//            u.setUsuario("usuario"+i);
//            u.setNivel(1);
//            u.setAtivo(true);
//            service.insert(u);
//        }
//        
//        
//        Marca m = new Marca();
//        m.setId(1);
//        m.setNome("Olá");
//        
//        Service<Marca> r = new MarcaService() ;
//        
//        
////        Iterator i = r.findAll().iterator();
////        while (i.hasNext()) {
////            Object[] next = (Object[]) i.next();
////            System.out.println(next[0]);
////        }
//        
//        Marca m2 =null;
//        
//        Transaction t = session.beginTransaction();
////        session.
//        session.save(m);
//        
//        m = (Marca)session.load(Marca.class, m.getId());
//        t.commit();
//        System.out.println(m.getId());
////        Marca m2 = (Marca)session.load(Marca.class, 2);
////        session.delete(t);
//        List<Marca> kusta = session.createQuery("from Marca").list();
//        
//        
//        List<Marca> lista2 = session.createQuery("from Marca where id > :val")
//                .setParameter("val", 1)
//                .list();
//        
//        kusta.stream().forEach((_item) -> {
//            System.out.println(_item.getId()+" "+_item.getNome());
//        });
//        session.close();

//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Criteria c = session.createCriteria(Produto.class);
//        c.createAlias("marca", "m");
//        c.add(Restrictions.eq("m.nome", "sadsadasdasfasf"));
//        for (Object list : c.list()) {
//            Produto p = (Produto) list;
//            System.out.println(p.getId());
//                        System.out.println(p.getMarca().getNome());
//
//        }
//        MarcaService service = new MarcaService();
//        for (int i = 0; i < 10000; i++) {
//            Marca m = new Marca();
//            m.setNome("Marca "+i);
//            service.insert(m);
//        }
//        
        SegmentoService serviceSegmento = new SegmentoService();
        for (int i = 0; i < 100000; i++) {
            Segmento m = new Segmento();
            m.setNome("Segmento "+i);
            serviceSegmento.insert(m);
        }
    }
}
