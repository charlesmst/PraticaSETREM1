/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.estoque;

import java.util.Date;
import java.util.List;
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
            s.delete(estMov);
            s.merge(ordem);
            t.commit();
        });
    }

    public void descontaEstoque(EstoqueMovimentacao estMov, Item i, Ordem ordem) {
        executeOnTransaction((s, t) -> {
            List<Estoque> estoques = buscaEstoqueOrdenado(s, i);
            int restantes = estMov.getQuantidade();
            int indiceEstoque = 0;
            while (restantes > 0 && indiceEstoque < estoques.size()) {
                Estoque e = estoques.get(indiceEstoque);
                int qtd = e.getQuantidadeDisponivel();
                if (qtd >= restantes) {
                    EstoqueMovimentacao eM = new EstoqueMovimentacao();
                    eM.setDataLancamento(new Date());
                    eM.setDescricao("SAIDA DE " + restantes + " -> " + i.getItemTipo().getNome() + " -> " + i.getDescricao());
                    eM.setQuantidade(restantes);
                    eM.setMovimentacaoTipo(estMov.getMovimentacaoTipo());
                    eM.setOrdem(estMov.getOrdem());
                    eM.setPessoa(estMov.getPessoa());
                    eM.setValorUnitario(e.getValorUnitario());
                    eM.setValorUnitarioVenda(estMov.getValorUnitarioVenda());
                    e.setQuantidadeDisponivel(qtd - restantes);
                    eM.setEstoque(e);
                    ordem.getEstoqueMovimentacaos().add(eM);
                    s.merge(e);
                    s.merge(i);
                    s.save(eM);
                    s.merge(ordem);
                    break;
                } else {
                    EstoqueMovimentacao eM = new EstoqueMovimentacao();
                    eM.setDataLancamento(new Date());
                    eM.setDescricao("SAIDA DE " + e.getQuantidadeDisponivel() + " -> " + i.getItemTipo().getNome() + " -> " + i.getDescricao());
                    eM.setQuantidade(e.getQuantidadeDisponivel());
                    eM.setMovimentacaoTipo(estMov.getMovimentacaoTipo());
                    eM.setOrdem(estMov.getOrdem());
                    eM.setPessoa(estMov.getPessoa());
                    eM.setValorUnitario(e.getValorUnitario());
                    eM.setValorUnitarioVenda(estMov.getValorUnitarioVenda());
                    e.setQuantidadeDisponivel(0);
                    restantes = restantes - qtd;
                    eM.setEstoque(e);
                    ordem.getEstoqueMovimentacaos().add(eM);
                    s.merge(e);
                    s.merge(i);
                    s.save(eM);
                    s.merge(ordem);
                }
                s.merge(e);
                indiceEstoque++;
            }
            t.commit();
        });
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
