package sample;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Nastia on 27.10.2015.
 */
@XStreamAlias("DailyExRates")
public class DailyExRates{
    @XStreamAlias("Date")
    @XStreamAsAttribute
     public LocalDate date;
     public ArrayList<Currency> Currencies;





}