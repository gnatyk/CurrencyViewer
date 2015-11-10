
package sample;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import com.thoughtworks.xstream.*;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private ObservableList<Currency> userData = FXCollections.observableArrayList();
    @FXML
    private ObservableList<String> userListViewDate = FXCollections.observableArrayList();
    @FXML
    private DatePicker dt_1;
    @FXML
    private ListView<String> listView;
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
    ArrayList<DailyExRates> listOfValueForCurrencyDate = new ArrayList<>();

    @FXML
    private void ShowCurrencyRate() {
        refreshTable();

        DailyExRates dailyExRates = new DailyExRates();
        String date = ChangeFormatDate(dt_1.getValue().toString());
        dailyExRates = Disirealasy(date);
        initData(dailyExRates);
        initializeTadleView();
    }


    private void initializeTadleView() {
        numCodeColumn.setCellValueFactory(new PropertyValueFactory<Currency, Integer>("numCode"));
        charCodeColumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("charCode"));
        scaleColumn.setCellValueFactory(new PropertyValueFactory<Currency, Integer>("scale"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Currency, String>("name"));
        rateColumn.setCellValueFactory(new PropertyValueFactory<Currency, Double>("rate"));
        tableUsers.setItems(userData);

    }

    @FXML
    private void ShowListOnDate() {
        String date = ChangeFormatDate(dt_1.getValue().toString());
        initializeListViewDate(date);

        InitCurrenciesList();
        System.out.print(listOfValueForCurrencyDate.size());
    }

    private void InitCurrenciesList() {
        DailyExRates dailyExRates;
        for (String f : userListViewDate) {
            dailyExRates = Disirealasy(f);
            listOfValueForCurrencyDate.add(dailyExRates);
        }
    }
    @FXML
    private void showChart()
    {
        Stage primaryStage = new Stage();
        myChart(primaryStage);
    }

    private void myChart(Stage primaryStage)  {

        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();

        LineChart<Number, Number> numberNumberLineChart = new LineChart<Number, Number>(x, y);
        numberNumberLineChart.setTitle("Series");
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("cos(x)");
        series1.setName("sin(x)");
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
        ObservableList<XYChart.Data> datas2 = FXCollections.observableArrayList();
        for (int i = 0; i < 20; i++) {
            datas.add(new XYChart.Data(i, Math.sin(i)));
            datas2.add(new XYChart.Data(i, Math.cos(i)));
        }
        series1.setData(datas);
        series2.setData(datas2);

        Scene scene = new Scene(numberNumberLineChart, 600, 600);
        numberNumberLineChart.getData().add(series1);
        numberNumberLineChart.getData().add(series2);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void initializeListViewDate(String date) {
        userListViewDate.add(date);
        listView.setItems(userListViewDate);
    }

    private void refreshTable() {
        tableUsers.getItems().clear();
    }

    public String ChangeFormatDate(String currentDate) {

        SimpleDateFormat myDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String newDateString = null;
        try {
            Date date = myDateFormat.parse(currentDate);
            myDateFormat.applyPattern("MM/dd/yyyy");
            newDateString = myDateFormat.format(date);

        } catch (ParseException e) {
            System.out.println("Invalid Date Parser Exception");
        }
        return newDateString;
    }

    public DailyExRates Disirealasy(String date) {
        String ondate = "ondate=" + date;
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

        for (Currency c : dailyExRates.Currencies) userData.add(c);

    }

    public static String excuteGet(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL + "?" + urlParameters);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "ru-RU");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
                     wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
