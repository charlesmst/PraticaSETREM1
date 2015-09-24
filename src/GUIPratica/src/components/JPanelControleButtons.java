/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import forms.frmMain;
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
            frmMain.getInstance().refreshButtons();
        }
    }

    public abstract void btnAddActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnAlterarActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnExcluirActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnSalvarActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnCancelarActionPerformed(java.awt.event.ActionEvent evt);

    public abstract void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt);

}
