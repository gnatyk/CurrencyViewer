
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;
import java.util.Locale;

import com.thoughtworks.xstream.*;


public class Controller {
    //TableView<Currency> tableView = new TableView<Currency>();
    private  ObservableList<Currency> userData = FXCollections.observableArrayList();
    @FXML
    private DatePicker dt_1;
    @FXML
    private TableView<Currency> tableUsers;
    @FXML
    private TableColumn<Currency, Integer> numCodeColumn;
    @FXML
    private TableColumn<Currency, String> charCodeColumn;
    @FXML
    private TableColumn<Currency, Integer> scaleColumn;
    @FXML
    private TableColumn<Currency, String> nameColumn;
    @FXML
    private TableColumn<Currency, Double> rateColumn;


    @FXML
    private void initialize() {

       dt_1.setValue(LocalDate.now());


    }

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "MM/dd/yyyy");

    @FXML
    private void ShowCurrencyRate()  {

        DailyExRates dailyExRates = new DailyExRates();
        String date = ChangeFormatDate(dt_1.getValue().toString(),dailyExRates);
        dailyExRates = Disirealasy(date);
         initData(dailyExRates);
        numCodeColumn.setCellValueFactory(new PropertyValueFactory<Currency, Integer>("numCode"));
        charCodeColumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("charCode"));
        scaleColumn.setCellValueFactory(new PropertyValueFactory<Currency, Integer>("scale"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("name"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<Currency, Double>("rate"));
        tableUsers.setItems(userData);
    }

    public String ChangeFormatDate(String currentDate, DailyExRates dailyExRates)
    {
        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = myDateFormat.parse(currentDate);
            myDateFormat.applyPattern("MM/dd/yyyy");
            String newDateString = myDateFormat.format(date);
            dailyExRates.date = newDateString;
        } catch (ParseException e) {
            System.out.println("Invalid Date Parser Exception");
        }

        String date = dailyExRates.date;
        return date;
    }

      public DailyExRates Disirealasy(String date) {
          String ondate = "ondate="+ date;
        String xmlResult = excuteGet("http://www.nbrb.by/Services/XmlExRates.aspx", ondate);
        xmlResult = xmlResult.trim().replaceFirst("^([\\W]+)<", "<");

        XStream xstream = new XStream(new StaxDriver());

       String dateFormat = "MM/dd/yyyy";
        String[] acceptableFormats = {dateFormat};
        xstream.registerConverter(new DateConverter(dateFormat, acceptableFormats));

        xstream.alias("DailyExRates", DailyExRates.class);
        xstream.alias("Currency", Currency.class);
        xstream.addImplicitCollection(DailyExRates.class, "Currencies", "Currency", Currency.class);
        xstream.processAnnotations(DailyExRates.class);
        xstream.processAnnotations(Currency.class);
        DailyExRates dailyExRates = (DailyExRates) xstream.fromXML(xmlResult);
        return dailyExRates;
    }


     private void initData(DailyExRates dailyExRates) {

    for(Currency c: dailyExRates.Currencies) userData.add(c);

}

    public static String excuteGet(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL + "?" + urlParameters);
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
                //wr.writeBytes(urlParameters);
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
