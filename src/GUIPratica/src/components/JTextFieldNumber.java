/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import utils.HibernateUtil;

/**
 *
 * @author Charles
 */
public class JTextFieldNumber extends JTextField {

    public JTextFieldNumber() {
        super();

        AbstractDocument doc = (AbstractDocument) getDocument();
        doc.setDocumentFilter(new NumberFilter());
    }

    public void setValue(int i){
        setText(i+"");
    }
    public int getValue(){
        if(!"".equals(getText())){
            return Integer.valueOf(getText());
        }
        return 0;
    }
    class NumberFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
            text = text.replaceAll("[^0-9]", "");

            super.insertString(fb, offset, text, attr); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {

            text = text.replaceAll("[^0-9]", "");
            super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
