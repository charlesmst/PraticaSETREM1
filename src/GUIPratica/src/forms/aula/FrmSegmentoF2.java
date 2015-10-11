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
import model.aula.Segmento;
import services.ServiceException;
import services.aul.SegmentoService;

/**
 *
 * @author Charles
 */
public class FrmSegmentoF2 extends frmF2 {

    SegmentoService service = new SegmentoService();

    public FrmSegmentoF2() {
        super(frmMain.getInstance(), "Buscar Segmento");
    }

    @Override
    protected void requestNew() {
        new FrmSegmentoCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<Segmento>() {

            @Override
            public Collection<Segmento> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "nome");

            }

            @Override
            public Object[] addRow(Segmento dado) {
                return new Object[]{dado.getId(), dado.getNome()};

            }
        };
    }

}
