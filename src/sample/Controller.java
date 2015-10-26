
package sample;


import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class Controller
{
    @FXML
    DatePicker dt_1;
    public void goAction(){
        dt_1.setValue(LocalDate.now());

    }

   }
