/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.ordem;

import components.JTableDataBinderListener;
import forms.frmF2;
import forms.frmMain;
import java.util.Collection;
import model.ordem.Cor;
import services.ServiceException;
import services.ordem.CorService;

/**
 *
 * @author Charles
 */
public class FrmCorF2 extends frmF2 {

    protected final CorService service = new CorService();

    public FrmCorF2() {
        super(frmMain.getInstance(), "Buscar Cor");
    }

    @Override
    protected void requestNew() {
        new FrmCorCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<Cor>() {

            @Override
            public Collection<Cor> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca.toUpperCase(), "id", "id", "nome");

            }

            @Override
            public Object[] addRow(Cor dado) {
                return new Object[]{dado.getId(), dado.getNome()};

            }
        };
    }

}
