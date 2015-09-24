/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guipratica;

import com.alee.laf.WebLookAndFeel;
import forms.frmMain;
import javax.swing.UIManager;

/**
 *
 * @author Charles
 */
public class GUIPratica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            WebLookAndFeel.install ();

//         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        frmMain frm = new frmMain();
        frm.setVisible(true);
    }

}
