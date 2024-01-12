package com.cuentasActivas.model;

import java.util.List;

public class Client 
{
    private String clientID;
    private List<Account> accounts;

    public Client() 
    {

    }

    public Client(String clientID, List<Account> accounts) 
    {
        this.clientID = clientID;
        this.accounts = accounts;
    }

    // Getters

    public String getClientID() 
    {
        return clientID;
    }

    public List<Account> getAccounts() 
    {
    	return accounts;
    }
    
    // Setters
    
    public void setClientID(String clientID) 
    {
        this.clientID = clientID;
    }


    public void setAccounts(List<Account> accounts) 
    {
        this.accounts = accounts;
    }
}

