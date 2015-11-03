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
import model.estoque.Item;
import services.ServiceException;
import services.estoque.ItemService;

/**
 *
 * @author Gustavo
 */
public class FrmItemF2 extends frmF2 {

    protected final ItemService service = new ItemService();

    public FrmItemF2() {
        super(frmMain.getInstance(), "Buscar Item de Estoque");
    }

    @Override
    protected void requestNew() {
        new FrmItemCadastro().setVisible(true);
    }

    @Override
    protected JTableDataBinderListener getListener() {
        return new JTableDataBinderListener<Item>() {

            @Override
            public Collection<Item> lista(String busca) throws ServiceException {

                return service.findByMultipleColumns(busca, "id", "id", "descricao");

            }

            @Override
            public Object[] addRow(Item dado) {
                return new Object[]{dado.getId(), dado.getDescricao()};

            }
        };
    }

}
