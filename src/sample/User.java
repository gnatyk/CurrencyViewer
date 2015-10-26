package sample;

/**
 * Created by Nastia on 26.10.2015.
 */
public class User {

    private String currency;
    private String value;


    public User(String currency, String value) {

        this.currency = currency;
        this.value = value;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency() {
        this.currency = currency;
    }

    public String getValue() {
        return value;
    }

    public void setValue() {
        this.value = value;
    }
}