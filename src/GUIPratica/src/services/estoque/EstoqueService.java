/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.estoque;

import java.util.List;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
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
    public void insert(List<Estoque> estoque) throws services.ServiceException {
        executeOnTransaction((s, t) -> {
            Estoque est = new Estoque();
            EstoqueMovimentacao estMov = new EstoqueMovimentacao();

            for (int x = 0; x < estoque.size(); x++) {
                est = estoque.get(x);
                int w = 0;
                for (EstoqueMovimentacao eM : estoque.get(x).getMovimentacoes()) {
                    estMov = estoque.get(x).getMovimentacoes().get(w);
                    w++;
                }

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

    public EstoqueService() {
        super(Estoque.class);
    }
}
