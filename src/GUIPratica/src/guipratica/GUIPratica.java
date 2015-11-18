/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guipratica;

import com.alee.laf.WebLookAndFeel;
import forms.FrmLoading;
import forms.frmLogin;
import forms.frmMain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.hibernate.JDBCException;

/**
 *
 * @author Charles
 */
public class GUIPratica {

    public GUIPratica() {
        FrmLoading loading = new FrmLoading();
        loading.setAoFinalizar((e) -> {
            LoginListener evento = new LoginListener();
            frmLogin login = new frmLogin();
            login.setAutenticadoListener(evento);
            login.setListener((e2) -> {
                if (evento.isAutenticou()) {
                    frmMain frm;
                    frm = new frmMain();
                    frm.setVisible(true);
                }
            });
            login.setVisible(true);

        });
        loading.setListener((e) -> {
            try {
                Initialize.init();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao abrir o sistema: " + ex.getMessage());
                System.exit(1);
            }

        });
        loading.setVisible(true);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            WebLookAndFeel.install();

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

        new GUIPratica();
//        System.exit(0);
    }

    class LoginListener implements ActionListener {

        private boolean autenticou = false;

        public boolean isAutenticou() {
            return autenticou;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            autenticou = true;
        }
    };

}
