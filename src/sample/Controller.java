
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
    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, String > currencyColum;
    @FXML
    private TableColumn<User,Double > purchaseColumn;
    @FXML
    private TableColumn<User,Double>saleColumn;

    private ObservableList<User> userData = FXCollections.observableArrayList();
    @FXML
    private void initialize()
    {
        initData();
        dt_1.setValue(LocalDate.now());
    }

    @FXML
    private void ShowCurrencyRate()
    {
        currencyColum.setCellValueFactory(new PropertyValueFactory<User,String>("Currency"));
        purchaseColumn.setCellValueFactory(new PropertyValueFactory<User, Double>("Purchase"));
        saleColumn.setCellValueFactory(new PropertyValueFactory<User, Double>("Sale"));
        tableUsers.setItems(userData);
    }

    @FXML
    private void exitMethod()
    {
      System.exit(0);
    }

    private void initData(){
        userData.add(new User("USD ",45.5,545.5));
        userData.add(new User( "EUR", 32.5, 45.5));
    }


   }
