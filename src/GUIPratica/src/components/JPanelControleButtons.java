/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import forms.frmMain;
import java.awt.LayoutManager;
import java.io.Serializable;
import java.util.function.Consumer;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import services.ServiceException;
import utils.AlertaTipos;
import utils.Mensagens;

/**
 *
 * @author Charles
 */
public abstract class JPanelControleButtons extends JPanel {

    private boolean btnAddEnable;
    private boolean btnAlterarEnable;
    private boolean btnExcluirEnable;
    private boolean btnAtualizarEnable;

    public void addPanelTopo(String text) {
//        JPanel jPanel1 = new frmTopoTexto(text);
//        
//        GroupLayout layout = new javax.swing.GroupLayout(jPanel1);;
//        jPanel1.setLayout(getLayout());
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 0, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGap(0, 25, Short.MAX_VALUE)
//        );
//        
//        layout = (GroupLayout) getLayout();
//        layout.setHorizontalGroup(
//            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        );

    }

    public void OnShow(){
        //Faz nada, a não ser que implemente
    }
    public boolean isBtnAtualizarEnable() {
        return btnAtualizarEnable;
    }

    public void setBtnAtualizarEnable(boolean btnAtualizarEnable) {
        this.btnAtualizarEnable = btnAtualizarEnable;
    }

    public final boolean isAtivo() {
        frmMain frm = frmMain.getInstance();
        return frm.getCurrentComponent() == this;
    }

    public boolean isBtnAddEnable() {
        return btnAddEnable;
    }

    public void setBtnAddEnable(boolean btnAddEnable) {
        this.btnAddEnable = btnAddEnable;
    }

    public boolean isBtnAlterarEnable() {
        return btnAlterarEnable;
    }

    public void setBtnAlterarEnable(boolean btnAlterarEnable) {
        this.btnAlterarEnable = btnAlterarEnable;
    }

    public boolean isBtnExcluirEnable() {
        return btnExcluirEnable;
    }

    public void setBtnExcluirEnable(boolean btnExcluirEnable) {
        this.btnExcluirEnable = btnExcluirEnable;
    }

    public final void fireButtonsChanged() {
        if (isAtivo()) {
            frmMain.getInstance().refreshButtons();
        }
    }

    public abstract void btnAddActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnAlterarActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnExcluirActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt);

    protected void defaultDeleteOperation(JTableDataBinder table, ThrowingConsumer<Serializable> callback) {

        if (table.getSelectedId() > 0) {
            if (!utils.Forms.dialogDelete()) {
                return;
            }
            try {
                callback.acceptThrows(table.getSelectedId());
                utils.Forms.mensagem(Mensagens.excluidoSucesso, AlertaTipos.sucesso);

                table.atualizar();
            } catch (Exception ex) {
                utils.Forms.mensagem(ex.getMessage(), AlertaTipos.erro);
            }
        } else {
            utils.Forms.mensagem(utils.Mensagens.selecioneUmItem, AlertaTipos.erro);
        }
    }

    protected void defaultUpdateOperation(JTableDataBinder table, ThrowingConsumer<Integer> callback) {
        try {
            table.runOnSelected(callback);
        } catch (Exception e) {
            utils.Forms.mensagem(e.getMessage(), AlertaTipos.erro);
        }
    }
}
