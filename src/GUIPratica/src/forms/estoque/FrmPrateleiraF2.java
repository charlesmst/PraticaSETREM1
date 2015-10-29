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
import model.estoque.Prateleira;
import services.ServiceException;
import services.estoque.PrateleiraService;

/**
 *
 * @author Gustavo
 */
public class FrmPrateleiraF2 extends frmF2 {

    protected final PrateleiraService service = new PrateleiraService();

    public FrmPrateleiraF2() {
        super(frmMain.getInstance(), "Buscar Prateleira");
    }

    @Override
    protected void requestNew() {
        new FrmPrateleiraCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<Prateleira>() {

            @Override
            public Collection<Prateleira> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "descricao");

            }

            @Override
            public Object[] addRow(Prateleira dado) {
                return new Object[]{dado.getId(), dado.getDescricao()};

            }
        };
    }

}
