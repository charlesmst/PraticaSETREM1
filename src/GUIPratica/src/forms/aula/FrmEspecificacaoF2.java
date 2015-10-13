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
import model.aula.Especificacao;
import services.ServiceException;
import services.aul.EspecificacaoService;

/**
 *
 * @author Charles
 */
public class FrmEspecificacaoF2 extends frmF2 {

    protected final EspecificacaoService service = new EspecificacaoService();

    public FrmEspecificacaoF2() {
        super(frmMain.getInstance(), "Buscar Especificacao");
    }

    @Override
    protected void requestNew() {
        new FrmEspecificacaoCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<Especificacao>() {

            @Override
            public Collection<Especificacao> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "titulo");

            }

            @Override
            public Object[] addRow(Especificacao dado) {
                return new Object[]{dado.getId(), dado.getTitulo()};

            }
        };
    }

}
