/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Charles
 */
public class JDialogController extends javax.swing.JDialog {

    public JDialogController(Frame owner, String title) {
        super(owner, title, true);

        utils.Forms.installEscapeCloseOperation(this);
        

    }

    protected void setDefaultButton(JButton btnDefault) {

        if (btnDefault != null) {
            JRootPane rootPane = SwingUtilities.getRootPane(btnDefault);
            rootPane.setDefaultButton(btnDefault);
        }
    }
}
