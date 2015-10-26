
package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.GregorianCalendar;

public class Controller
{
    @FXML
    DatePicker dt_1;



    @FXML
    public void goAction()
    {
       // Calendar calendar = new GregorianCalendar();
       // SimpleDateFormat formattedDate = new SimpleDateFormat("dd.MM.yyyy");
       // String dateToday = formattedDate.format(calendar.getTime());
        dt_1.setValue(LocalDate.now()); Localdate();

    }

    public void Localdate ()
    {  dt_1.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            LocalDate date = dt_1.getValue();
            System.err.print("Select date:" + date);

        }
    }); }
}



