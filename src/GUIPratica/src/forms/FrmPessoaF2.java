/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import forms.aula.*;
import components.JTableDataBinderListener;
import forms.frmF2;
import forms.frmMain;
import java.util.Collection;
import model.Pessoa;
import services.ServiceException;
import services.PessoaService;

/**
 *
 * @author Charles
 */
public class FrmPessoaF2 extends frmF2 {

    protected final PessoaService service = new PessoaService();

    public FrmPessoaF2() {
        super(frmMain.getInstance(), "Buscar Pessoas");
    }

    @Override
    protected void requestNew() {
        new FrmPessoaCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<Pessoa>() {

            @Override
            public Collection<Pessoa> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "nome");

            }

            @Override
            public Object[] addRow(Pessoa dado) {
                return new Object[]{dado.getId(), dado.getNome()};

            }
        };
    }

}
