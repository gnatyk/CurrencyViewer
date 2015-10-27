
package sample;

import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import com.thoughtworks.xstream.*;
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
    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "MM/dd/yyyy");
    @FXML
    private void ShowCurrencyRate()
    {
       // currencyColum.setCellValueFactory(new PropertyValueFactory<User,String>("Currency"));
       // purchaseColumn.setCellValueFactory(new PropertyValueFactory<User, Double>("Purchase"));
        //saleColumn.setCellValueFactory(new PropertyValueFactory<User, Double>("Sale"));
       // tableUsers.setItems(userData);
        String xmlResult = excuteGet("http://www.nbrb.by/Services/XmlExRates.aspx","ondate=01/31/2011");
        xmlResult = xmlResult.trim().replaceFirst("^([\\W]+)<","<");
        XStream xstream = new XStream(new StaxDriver());

        String dateFormat = "MM/dd/yyyy";
        String[] acceptableFormats = {dateFormat};
        xstream.registerConverter(new DateConverter(dateFormat, acceptableFormats));

        xstream.alias("DailyExRates", DailyExRates.class);
        xstream.alias("Currency", Currency.class);
        xstream.addImplicitCollection(DailyExRates.class, "Currencies", "Currency", Currency.class);
        xstream.processAnnotations(DailyExRates.class);
        xstream.processAnnotations(Currency.class);
        DailyExRates dailyExRates = (DailyExRates)xstream.fromXML(xmlResult);
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
    public static String excuteGet(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "ru-RU");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is,"UTF-8"));
            StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
            String line;
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }


   }
