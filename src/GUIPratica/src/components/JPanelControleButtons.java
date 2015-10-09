/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import forms.FrmMain;
import forms.FrmTopoTexto;
import java.awt.LayoutManager;
import javax.swing.GroupLayout;
import javax.swing.JPanel;

/**
 *
 * @author Charles
 */
public abstract class JPanelControleButtons extends JPanel {

    private boolean btnAddEnable;
    private boolean btnAlterarEnable;
    private boolean btnExcluirEnable;
    private boolean btnSalvarEnable;
    private boolean btnCancelarEnable;
    private boolean btnAtualizarEnable;

    public void addPanelTopo(String text){
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
    
    public boolean isBtnAtualizarEnable() {
        return btnAtualizarEnable;
    }

    public void setBtnAtualizarEnable(boolean btnAtualizarEnable) {
        this.btnAtualizarEnable = btnAtualizarEnable;
    }

    public final boolean isAtivo() {
        FrmMain frm = FrmMain.getInstance();
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

    public boolean isBtnSalvarEnable() {
        return btnSalvarEnable;
    }

    public void setBtnSalvarEnable(boolean btnSalvarEnable) {
        this.btnSalvarEnable = btnSalvarEnable;
    }

    public boolean isBtnCancelarEnable() {
        return btnCancelarEnable;
    }

    public void setBtnCancelarEnable(boolean btnCancelarEnable) {
        this.btnCancelarEnable = btnCancelarEnable;
    }

    public final void fireButtonsChanged() {
        if (isAtivo()) {
            FrmMain.getInstance().refreshButtons();
        }
    }

    public abstract void btnAddActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnAlterarActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnExcluirActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnSalvarActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnCancelarActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt);

}
