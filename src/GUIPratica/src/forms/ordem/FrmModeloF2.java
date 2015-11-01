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
import model.ordem.Modelo;
import services.ServiceException;
import services.ordem.ModeloService;

/**
 *
 * @author Charles
 */
public class FrmModeloF2 extends frmF2 {

    protected final ModeloService service = new ModeloService();

    public FrmModeloF2() {
        super(frmMain.getInstance(), "Buscar Modelo");
    }

    @Override
    protected void requestNew() {
        new FrmModeloCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<Modelo>() {

            @Override
            public Collection<Modelo> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca.toUpperCase(), "id", "id", "nome","marca.nome");

            }

            @Override
            public Object[] addRow(Modelo dado) {
                return new Object[]{dado.getId(), dado.getMarca().getNome()+" - "+dado.getNome()};

            }
        };
    }

}
