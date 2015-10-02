/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components.treemenu;

import components.JPanelControleButtons;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import utils.AlertaTipos;


/**
 *
 * @author Charles
 */
public class TreeNodeMenu extends DefaultMutableTreeNode {

    //Singleton
    private JPanel instance;
    private String classRef;

    public TreeNodeMenu(String title, String classRef) {
        super(title);
        this.classRef = classRef;
    }

    public JPanel getPanel() {
        if (instance == null) {
            try {
                Class c = Class.forName(classRef);
                instance = (JPanel)c.newInstance();
                if(instance instanceof JPanelControleButtons){
                    ((JPanelControleButtons)instance).addPanelTopo(getUserObject().toString());
                }

            } catch (Exception e) {
                utils.Forms.mensagem("Erro ao abrir janela " + classRef, AlertaTipos.erro);
                return null;
            }
        }
        return instance;
    }
}
