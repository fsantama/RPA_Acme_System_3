package com.cuentasActivas.model;

public class WorkItem 
{
	int wiid;
	int row;
	int page;
	String clientID;
	public Client client;
	
	public WorkItem(int wiid, int row, int page)
	{
		this.wiid = wiid;
		this.row = row;
		this.page = page;
	}
	
    public WorkItem() 
    {

	}

	// Getters
    
    public int getWiid() 
    {
        return wiid;
    }

    public int getRow() 
    {
        return row;
    }

    public int getPage() 
    {
        return page;
    }

    public Client getClient() 
    {
    	return client;
    }

	public String getClientID() 
	{
		return clientID;
	}
    
    // Setters
	
    public void setWiid(int wiid) 
    {
        this.wiid = wiid;
    }

    public void setRow(int row) 
    {
        this.row = row;
    }

    public void setPage(int page) 
    {
        this.page = page;
    }
    
    public void setClient(Client client) 
    {
        this.client = client;
    }
    
    public void setClientID(String clientID) 
    {
        this.clientID = clientID;
    }
}
