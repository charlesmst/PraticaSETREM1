/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.estoque;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import model.estoque.Item;
import model.estoque.MovimentacaoTipo;
import model.ordem.Ordem;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import services.Service;
import services.ordem.OrdemService;

public class EstoqueMovimentacaoService extends Service<EstoqueMovimentacao> {

    public void insertPersonalizado(EstoqueMovimentacao estMov, Item i) throws services.ServiceException {
        Session s = getSession();
        synchronized (s) {
            Transaction t = s.beginTransaction();
            try {
                s.save(estMov);
                s.merge(i);
                t.commit();

            } catch (Exception e) {
                t.rollback();
                throw new services.ServiceException("Erro ao inserir " + classRef.getSimpleName(), e, logger);
            } finally {
                s.close();
            }
        }
    }

    public void excluirEstoque(Ordem ordem, EstoqueMovimentacao estMov) {
        executeOnTransaction((s, t) -> {
            Estoque e = estMov.getEstoque();
            int qtd = e.getQuantidadeDisponivel();
            e.setQuantidadeDisponivel(qtd + estMov.getQuantidade());
            ordem.getEstoqueMovimentacaos().remove(estMov);
            s.merge(e);
            s.merge(ordem);
            EstoqueMovimentacao mov = (EstoqueMovimentacao) s.get(classRef, estMov.getId());
            s.delete(mov);

            t.commit();
        });
        new OrdemService().refreshCollection(ordem);
    }

    public void descontaEstoque(EstoqueMovimentacao estMov, Item i, Ordem ordem) {
        executeOnTransaction((s, t) -> {
            List<Estoque> estoques = buscaEstoqueOrdenado(s, i);
            int restantes = estMov.getQuantidade();
            int indiceEstoque = 0;
            while (restantes > 0 && indiceEstoque < estoques.size()) {
                Estoque e = estoques.get(indiceEstoque);
                int qtd = e.getQuantidadeDisponivel();

                EstoqueMovimentacao eM = new EstoqueMovimentacao();
                eM.setDataLancamento(new Date());
                eM.setDescricao("SAIDA DE " + restantes + " -> " + i.getItemTipo().getNome() + " -> " + i.getDescricao());
                eM.setMovimentacaoTipo(estMov.getMovimentacaoTipo());
                eM.setPessoa(estMov.getPessoa());
                eM.setValorUnitario(e.getValorUnitario());
                eM.setValorUnitarioVenda(estMov.getValorUnitarioVenda());

                eM.setEstoque(e);

                if (qtd >= restantes) {
                    eM.setQuantidade(restantes);
                    e.setQuantidadeDisponivel(qtd - restantes);
                    restantes = 0;
                } else {
                    e.setQuantidadeDisponivel(0);
                    eM.setQuantidade(qtd);
                    restantes -= qtd;

                }
                s.merge(e);
                s.merge(i);
//                s.save(eM);
                ordem.getEstoqueMovimentacaos().add(eM);
                
                s.merge(ordem);
                indiceEstoque++;

            }

            t.commit();
//            s.refresh(ordem);

        });
        new OrdemService().refreshCollection(ordem);
    }

    private List<Estoque> buscaEstoqueOrdenado(Session s, Item i) {
        String hql = "from Estoque e "
                + "where (e.item.id)=:item_id and (e.quantidadeDisponivel) > 0 "
                + "order by (e.dataCompra)";
        Query query = s.createQuery(hql);
        query.setInteger("item_id", (int) i.getId());
        return query.list();
    }

    public List<EstoqueMovimentacao> buscaMovimentacoes(Estoque e) {
        List<EstoqueMovimentacao> estMov = findBy("estoque.id", e.getId());
        return estMov;
    }

    public EstoqueMovimentacaoService() {
        super(EstoqueMovimentacao.class);
    }

}
