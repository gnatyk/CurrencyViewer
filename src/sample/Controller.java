
package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class Controller
{
    @FXML
    public DatePicker dt_1;

    private ObservableList<User> userData = FXCollections.observableArrayList();

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, String > currencyColum;
    @FXML
    private TableColumn<User,String > valueColumn;

    @FXML
    private void initialize()
    {
        initData();
        dt_1.setValue(LocalDate.now());
        currencyColum.setCellValueFactory(new PropertyValueFactory<User, String>("currency"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<User, String>("value"));

        tableUsers.setItems(userData);

    }
    private void initData(){
        userData.add(new User("USD ","56 "));
        userData.add(new User( "EUR", "gag"));

    }
   }
