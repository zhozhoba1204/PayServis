package ru.blinov.payservis.model;



import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Transaction {
    private BigDecimal remainBalance;
    private BigDecimal amountOfPayment;

    public Transaction(){
    }

    public Transaction(BigDecimal remainBalance, BigDecimal amountOfPayment) {
        this.remainBalance = remainBalance;
        this.amountOfPayment = amountOfPayment;
    }

    public BigDecimal getRemainBalance() {
        return remainBalance;
    }

    public void setRemainBalance(BigDecimal remainBalance) {
        this.remainBalance = remainBalance;
    }

    public BigDecimal getAmountOfPayment() {
        return amountOfPayment;
    }

    public void setAmountOfPayment(BigDecimal amountOfPayment) {
        this.amountOfPayment = amountOfPayment;
    }
}
