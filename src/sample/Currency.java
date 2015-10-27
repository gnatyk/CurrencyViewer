package sample;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * Created by Nastia on 27.10.2015.
 * <NumCode>036</NumCode>
 <CharCode>AUD</CharCode>
 <Scale>1</Scale>
 <Name>Австралийский доллар</Name>
 <Rate>2986.46</Rate>
 */
@XStreamAlias("Currency")
public class Currency {
    @XStreamAsAttribute
    @XStreamAlias("Id")
    int id;
    @XStreamAlias("NumCode")
    int NumCode;
    @XStreamAlias("CharCode")
    String CharCode;
    @XStreamAlias("Scale")
    int Scale;
    @XStreamAlias("Name")
    String Name;
    @XStreamAlias("Rate")
    Double Rate;
}
