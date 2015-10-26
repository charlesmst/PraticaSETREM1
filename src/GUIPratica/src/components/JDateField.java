package components;

import java.awt.EventQueue;
import java.util.Date;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.text.AbstractDocument;

public class JDateField extends JSpinner {

    SpinnerDateModel model;

    @Override
    public void setModel(SpinnerModel model) {
        super.setModel(model); //To change body of generated methods, choose Tools | Templates.
        setFormatter();

    }

    public JDateField() {
        super(new SpinnerDateModel());
        model = (SpinnerDateModel) getModel();
        setFormatter();
        model.setValue(new Date());

    }

    private void setFormatter() {
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(this, "d/M/y");
//        
        setEditor(timeEditor);
    }

    public void setDate(Date d) {
        model.setValue(d);
    }

    public Date getDate() {
        return (Date) getModel().getValue();
    }

}
