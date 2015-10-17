/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms.aula;

import components.JTableDataBinderListener;
import forms.frmF2;
import forms.frmMain;
import java.util.Collection;
import model.aula.MarcaR;
import services.ServiceException;
import services.aul.MarcaService;

/**
 *
 * @author Charles
 */
public class FrmMarcaF2 extends frmF2 {

    protected final MarcaService service = new MarcaService();

    public FrmMarcaF2() {
        super(frmMain.getInstance(), "Buscar Marca");
    }

    @Override
    protected void requestNew() {
        new FrmMarcaCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<MarcaR>() {

            @Override
            public Collection<MarcaR> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "nome");

            }

            @Override
            public Object[] addRow(MarcaR dado) {
                return new Object[]{dado.getId(), dado.getNome()};

            }
        };
    }

}
