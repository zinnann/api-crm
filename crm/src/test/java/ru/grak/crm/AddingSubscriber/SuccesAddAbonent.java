package ru.grak.crm.AddingSubscriber;

import java.math.BigDecimal;

public class SuccesAddAbonent {
    private Integer id;
    private String phoneNumber;
    private String tariff;
    private BigDecimal balance;

    public Integer getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTariff() {
        return tariff;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
