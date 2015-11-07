/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import components.JTableDataBinderListener;
import java.util.Collection;
import model.Cidade;
import model.Pessoa;
import services.CidadeService;
import services.ServiceException;
import services.PessoaService;

/**
 *
 * @author Gustavo
 */
public class FrmCidadeF2 extends frmF2 {

    protected final CidadeService service = new CidadeService();

    public FrmCidadeF2() {
        super(frmMain.getInstance(), "Buscar Cidade");
    }

    @Override
    protected void requestNew() {
        new FrmCidadeCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<Cidade>() {

            @Override
            public Collection<Cidade> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "nome", "cep");

            }

            @Override
            public Object[] addRow(Cidade dado) {
                return new Object[]{dado.getId(), dado.getNome()};

            }
        };
    }

}
