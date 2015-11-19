package services.estoque;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import model.estoque.Estoque;
import model.estoque.EstoqueMovimentacao;
import model.estoque.Item;
import model.estoque.MovimentacaoTipo;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.spi.ServiceException;

import services.Service;

public class ItemService extends Service<Item> {

    private List<EstoqueMovimentacao> lista = new ArrayList<>();

    @Override
    public void update(Item obj) throws services.ServiceException {
        obj.setDescricao(obj.getDescricao().toUpperCase());
        super.update(obj);
    }

    @Override
    public void insert(Item obj) throws services.ServiceException {
        obj.setDescricao(obj.getDescricao().toUpperCase());
        super.insert(obj);
    }

    public boolean unico(int id, String descricao) throws ServiceException {
        return findFilter(Restrictions.ne("id", id), Restrictions.eq("descricao", descricao)).isEmpty();
    }

    public ItemService() {
        super(Item.class);
    }

    public int verificaQuantidadeDisp(Item i) {
        List<Estoque> estoqueDoItem = new EstoqueService().findBy("item.id", i.getId());
        int quantidade = 0;
        for (Estoque e : estoqueDoItem) {
            quantidade = quantidade + e.getQuantidadeDisponivel();
        }
        return quantidade;
    }

    private List<EstoqueMovimentacao> getEstoqueMovimentacaoAll(Item i) {
        lista = new EstoqueMovimentacaoService().movimentosDeEstoqueAll(i);
        return lista;
    }

    public Item MPM(Item i) {
        getEstoqueMovimentacaoAll(i);
        //----------Saldo----------
        int qtdSaldo = 0;
        double valorUniSaldo = 0d;
        double valorTotal = 0d;
        //-------------------------
        double venda = 0d;
        int cont = 0;
        Date date = null;
        for (EstoqueMovimentacao estMov : lista) {
            if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.entrada)
                    || estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE ENTRADA")
                    || estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE SAIDA")) {
                if (estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE SAIDA")) {
                    valorTotal -= (estMov.getQuantidade() * estMov.getValorUnitario());
                    qtdSaldo -= estMov.getQuantidade();
                    valorUniSaldo = valorTotal / qtdSaldo;
                } else {
                    valorTotal += (estMov.getQuantidade() * estMov.getValorUnitario());
                    qtdSaldo += estMov.getQuantidade();
                    valorUniSaldo = valorTotal / qtdSaldo;
                }
            }
            if (estMov.getMovimentacaoTipo().getTipo().equals(MovimentacaoTipo.TipoMovimentacao.saida)
                    && !estMov.getMovimentacaoTipo().getDescricao().contains("AJUSTE SAIDA")) {
                valorTotal -= (estMov.getQuantidade() * valorUniSaldo);
                venda += estMov.getQuantidade() * valorUniSaldo;
                qtdSaldo -= estMov.getQuantidade();
            }
//            System.out.println("------------------" + cont + "-------------------");
//            System.out.println("ID -> " + estMov.getId());
//            System.out.println("Tipo de Mov -> " + estMov.getMovimentacaoTipo().getDescricao());
//            System.out.println("Qtd -> " + qtdSaldo);
//            System.out.println("Valor Unitario -> " + valorUniSaldo);
//            System.out.println("Valor Total -> " + valorTotal);
//            System.out.println("Saldo -> " + venda);
//            System.out.println("----------------------------------------------");
            //cont++;
        }
        i.setQtdDisponivel(qtdSaldo);
        i.setCustoMedio(valorUniSaldo);
        i.setValotTotal(valorTotal);
        return i;
    }

    public Collection<Item> findAllWithDisp() {
        Collection<Item> lista = this.findAll();
        Collection<Item> lista2 = new ArrayList();
        for (Item i : lista) {
            i.setQtdDisponivel(this.verificaQuantidadeDisp(i));
            lista2.add(i);
        }
        return lista2;
    }
}
