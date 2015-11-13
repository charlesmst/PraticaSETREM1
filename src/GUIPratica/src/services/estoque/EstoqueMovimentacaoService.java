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

//Estoque est = new EstoqueService().verificaMaisAntigo(item);
//            List<EstoqueMovimentacao> lista = new EstoqueMovimentacaoService().buscaMovimentacoes(est);
//            EstoqueMovimentacao estMov = new EstoqueMovimentacao();
//            int cont = 0;
//            for (EstoqueMovimentacao eM : lista) {
//                if (cont > 0) {
//                    JOptionPane.showMessageDialog(null, "Possui mais de uma movimentação-estoque relacionada ao Estoque");
//                } else {
//                    estMov = eM;
//                }
//                cont++;
//            }
//            estMov.setDataLancamento(new Date());
//            estMov.setPessoa(ordem.getPessoa());
//            estMov.setDescricao("SAIDA DE -> " + txtQuantidade.getValue() + " -> " + item.toString());
//            estMov.setMovimentacaoTipo((MovimentacaoTipo) txtOrigem.getModel().getSelectedItem());
//            estMov.setQuantidade(qtd * -1);
//            est.getItem().setUltimoValorVenda(getValorTotal());
//            est.setQuantidadeDisponivel(est.getQuantidadeDisponivel() + estMov.getQuantidade());
//            estMov.setEstoque(est);
//            estMov.setValorUnitario(txtValor.getValue());
//            //estMov.getOrdem().add(ordem);
//            ordem.getEstoqueMovimentacaos().add(estMov);
//            eM = estMov;
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
                    eM.setDescricao("SAIDA DE " + restantes + "->" + i.getDescricao());
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
                    eM.setDescricao("SAIDA DE " + e.getQuantidadeDisponivel() + "->" + i.getDescricao());
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
//
//    public boolean unico(int id, String descricao) throws ServiceException {
//        return findFilter(Restrictions.ne("id", id), Restrictions.eq("descricao", descricao)).isEmpty();
//    }

    public List<EstoqueMovimentacao> buscaMovimentacoes(Estoque e) {
        List<EstoqueMovimentacao> estMov = findBy("estoque.id", e.getId());
        return estMov;
    }

    public EstoqueMovimentacaoService() {
        super(EstoqueMovimentacao.class);
    }

}
