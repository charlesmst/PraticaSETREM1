/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Charles
 */
public class JTextFieldUpper extends JTextField {

    public JTextFieldUpper(int cols) {
        super(cols);
    }
    
    public JTextFieldUpper() {
        super();
    }

    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    static class UpperCaseDocument extends PlainDocument {

        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {

            if (str == null) {
                return;
            }
            char[] upper = str.toCharArray();
            for (int i = 0; i < upper.length; i++) {
                upper[i] = Character.toUpperCase(upper[i]);
            }
            super.insertString(offs, new String(upper), a);
        }
    }
}
