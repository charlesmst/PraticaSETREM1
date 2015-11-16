/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import utils.Globals;
import static utils.Globals.iconeError;
import static utils.Globals.iconeWarning;

/**
 *
 * @author charles
 */
public class JTreeMenuRenderer extends DefaultTreeCellRenderer {

    private static ImageIcon estoque = new ImageIcon(Globals.class.getClassLoader().getResource("resources/estoque.png"));
    private static ImageIcon caixa = new ImageIcon(Globals.class.getClassLoader().getResource("resources/caixa.png"));
    private static ImageIcon ordem = new ImageIcon(Globals.class.getClassLoader().getResource("resources/ordem.png"));
    private static ImageIcon gerenciamento = new ImageIcon(Globals.class.getClassLoader().getResource("resources/gerenciamento.png"));

    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,
                hasFocus);

        if (leaf) {
            setIcon(null);
        }
        switch (value + "") {
            case "Estoque":
                setIcon(estoque);
                break;
            case "Fluxo de Caixa":
                setIcon(caixa);
                break;
            case "Ordem de Serviço":
                setIcon(ordem);
                break;
            case "Gerenciamento":
                setIcon(gerenciamento);
                break;
        }

        return this;
    }

}
