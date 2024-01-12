package com.cuentasActivas.model;

public class Account 
{
    private String accountID;
    private String timestamp;
    private String amount;
    private String status;

    public Account() 
    {

    }

    public Account(String accountID, String timestamp, String amount, String status) 
    {
        this.accountID = accountID;
        this.timestamp = timestamp;
        this.amount = amount;
        this.status = status;
    }

    // Getters

    public String getAccountID() 
    {
        return accountID;
    }

    public String getTimestamp() 
    {
        return timestamp;
    }

    public String getAmount() 
    {
        return amount;
    }

    public String getStatus() 
    {
        return status;
    }

    // Setters

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public void setAccountID(String accountID) 
    {
    	this.accountID = accountID;
    }

    public void setTimestamp(String timestamp) 
    {
    	this.timestamp = timestamp;
    }

    public void setAmount(String amount) 
    {
    	this.amount = amount;
    }
}
