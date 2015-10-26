package sample;

import java.util.DoubleSummaryStatistics;

/**
 * Created by Nastia on 26.10.2015.
 */
public class User {

    private String currency;
    private Double purchase;
    private Double sale;


    public User(String currency, Double purchase, Double sale) {

        this.currency = currency;
        this.purchase = purchase;
        this.sale = sale;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency() {
        this.currency = currency;
    }

    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase() {
        this.purchase = purchase;
    }

    public Double getSale()
    {
        return sale;
    }
    public void setSale(){
        this.sale = sale;
    }
}