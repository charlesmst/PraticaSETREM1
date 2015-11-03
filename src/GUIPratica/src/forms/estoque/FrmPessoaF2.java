/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.estoque;
import components.JTableDataBinderListener;
import forms.frmF2;
import forms.frmMain;
import java.util.Collection;
import model.estoque.ItemTipo;
import services.ServiceException;
import services.estoque.ItemTipoService;

/**
 *
 * @author Gustavo
 */
public class FrmPessoaF2 extends frmF2 {

    protected final ItemTipoService service = new ItemTipoService();

    public FrmPessoaF2() {
        super(frmMain.getInstance(), "Buscar Tipo de Item");
    }

    @Override
    protected void requestNew() {
        new FrmItemTipoCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<ItemTipo>() {

            @Override
            public Collection<ItemTipo> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "nome");

            }

            @Override
            public Object[] addRow(ItemTipo dado) {
                return new Object[]{dado.getId(), dado.getNome()};

            }
        };
    }

}
