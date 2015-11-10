/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.estoque;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import model.estoque.Item;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.spi.ServiceException;
import services.Service;

public class EstoqueService extends Service<Estoque> {

//    @Override
//    public void update(Estoque obj) throws services.ServiceException {
//        obj.setDescricao(obj.getDescricao().toUpperCase());
//        super.update(obj); 
//    }
//
    public void insert(List<Estoque> estoque, List<EstoqueMovimentacao> estoqueMovimentacoes) throws services.ServiceException {
        executeOnTransaction((s, t) -> {

            for (Estoque estoque1 : estoque) {
                s.save(estoque1);
            }
            for (EstoqueMovimentacao estoqueMovimentacoe : estoqueMovimentacoes) {
                estoqueMovimentacoe.setDataLancamento(new Date());
                s.save(estoqueMovimentacoe);
            }
            t.commit();
        });
    }

    public void update(Estoque est, EstoqueMovimentacao estMov) throws services.ServiceException {
        executeOnTransaction((s, t) -> {
            if (est.getId() > 0) {
                s.merge(est);
            } else {
                s.save(est);
            }
            if (estMov.getId() > 0) {
                s.merge(estMov);
            } else {
                s.save(estMov);
            }
            t.commit();
        });
    }

    public List<EstoqueMovimentacao> todasMovimentacaos() {
        return new EstoqueMovimentacaoService().findAll();
    }

    public Estoque verificaMaisAntigo(Item i) {
        List<Estoque> estoqueDoItem = new EstoqueService().findBy("item.id", i.getId());
        Estoque estoqueOld = new Estoque();
        
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 100);
        estoqueOld.setDataCompra(c.getTime());
        JOptionPane.showMessageDialog(null, estoqueOld.getDataCompra());
        for (Estoque e : estoqueDoItem) {
            if (e.getDataCompra().before(estoqueOld.getDataCompra())) {
                estoqueOld = e;
            }
        }
        return estoqueOld;
    }

    public EstoqueService() {
        super(Estoque.class);
    }
}
