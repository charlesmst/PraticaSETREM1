/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import forms.frmMain;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import org.apache.log4j.LogManager;

/**
 *
 * @author Charles
 */
public class Forms {

    /**
     * Mostra mensagem na tela
     */
    public static void mensagem(String text, AlertaTipos tipo) {
        mensagem(text, tipo,true);
    }

    public static void mensagem(String text, AlertaTipos tipo,boolean beepOnError) {
        frmMain.getInstance().alert(text);
        frmMain.getInstance().setIcone(tipo);

        if (tipo == AlertaTipos.erro && beepOnError) {
            beep();
        }
    }

    public static void beep() {
        Toolkit.getDefaultToolkit().beep();

    }

    public static void iniciaProgress() {
        frmMain.getInstance().iniciaProgress();
    }

    public static void paraProgress() {
        frmMain.getInstance().paraProgress();
    }

    private static final KeyStroke escapeStroke
            = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    public static final String dispatchWindowClosingActionMapKey
            = "com.spodding.tackline.dispatch:WINDOW_CLOSING";

    public static void installEscapeCloseOperation(final JDialog dialog) {
        Action dispatchClosing = new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                dialog.dispatchEvent(new WindowEvent(
                        dialog, WindowEvent.WINDOW_CLOSING
                ));
            }
        };
        JRootPane root = dialog.getRootPane();
        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                escapeStroke, dispatchWindowClosingActionMapKey
        );
        root.getActionMap().put(dispatchWindowClosingActionMapKey, dispatchClosing
        );
    }

    public static boolean dialogDelete() {
        int dialogResult = JOptionPane.showConfirmDialog(null, Mensagens.exclusaoConfirmacao);
        return (dialogResult == JOptionPane.YES_OPTION);
    }
    
    public static void log(Exception ex){
         LogManager.getLogger(Forms.class).error(ex.getMessage(), ex);
    }
}
