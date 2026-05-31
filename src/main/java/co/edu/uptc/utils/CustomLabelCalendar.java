package co.edu.uptc.utils;

import java.awt.Dimension;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class CustomLabelCalendar extends BaseComponent<CustomLabelCalendar>{
    private JDateChooser dateChooser;

    public CustomLabelCalendar() {
        super();
        createCalendarComponent();
        addErrorLabel();
    }

    private void createCalendarComponent() {
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(200, 30)); 
        add(dateChooser);
    }

    public Date getDate() {
        return dateChooser.getDate();
    }

    public CustomLabelCalendar setDate(Date date) {
        dateChooser.setDate(date);
        return this;
    }

    public CustomLabelCalendar clearDate() {
        dateChooser.setDate(null);
        return this;
    }

}
