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
   private int id;
    @XStreamAlias("NumCode")
    private int NumCode;
    @XStreamAlias("CharCode")
    private String CharCode;
    @XStreamAlias("Scale")
    private int Scale;
    @XStreamAlias("Name")
    private String Name;
    @XStreamAlias("Rate")
    private Double Rate;


    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id =id;
    }

    public int getNumCode()
    {
        return NumCode;
    }

    public void setNumCode(int numCode)
    {
        this.NumCode = numCode;
    }

    public String getCharCode()
    {
        return CharCode;
    }

    public void setCharCode(String charCode)
    {
        this.CharCode = charCode;
    }

    public int getScale()
    {
        return Scale;
    }
    public void setScale(int scale)
    {
        this.Scale = scale;
    }

    public String getName()
    {
        return Name;
    }

    public void setName(String name)
    {
        this.Name = name;
    }

    public Double getRate()
    {
        return Rate;
    }
    public void setRate(Double rate)
    {
        this.Rate = rate;
    }


}
