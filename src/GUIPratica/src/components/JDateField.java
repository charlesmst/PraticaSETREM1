/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.EventQueue;
import java.util.Date;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.text.AbstractDocument;

/**
 *
 * @author Charles
 */
public class JDateField extends JSpinner {

    @Override
    public void setModel(SpinnerModel model) {
        super.setModel(model); //To change body of generated methods, choose Tools | Templates.
        setFormatter();

    }

    public JDateField() {
        super(new SpinnerDateModel());
        setFormatter();

//        AbstractDocument doc = (AbstractDocument) getDocument();
//        doc.setDocumentFilter(new JTextFieldNumber.NumberFilter());
    
    }

    private void setFormatter() {
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(this, "d/M/y");
        
        setEditor(timeEditor);
        setValue(new Date());
    }
    
    public void setDate(Date d){
        setValue(ui);
    }
    
    public Date getDate(){
        return (Date)getValue();
    }
    
    

}
