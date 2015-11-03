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
import model.ordem.Veiculo;
import services.ServiceException;
import services.ordem.VeiculoService;

/**
 *
 * @author Charles
 */
public class FrmVeiculoF2 extends frmF2 {

    protected final VeiculoService service = new VeiculoService();

    public FrmVeiculoF2() {
        super(frmMain.getInstance(), "Buscar Veiculo");
    }

    @Override
    protected void requestNew() {
        new FrmVeiculoCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<Veiculo>() {

            @Override
            public Collection<Veiculo> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca.toUpperCase(), "id", "id", "placa","cor.nome","modelo.nome");

            }

            @Override
            public Object[] addRow(Veiculo dado) {
                return new Object[]{dado.getId(), dado.toString()};

            }
        };
    }

}
