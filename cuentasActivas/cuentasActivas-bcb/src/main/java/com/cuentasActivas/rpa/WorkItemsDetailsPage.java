package com.cuentasActivas.rpa;

import static com.cuentasActivas.util.Constants.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkItemsDetailsPage 
{
	@FindBy (xpath = WORK_ITEMS_CLIENTID)
	private WebElement clientID;
	
	@FindBy (xpath = UPDATE_WORK_ITEM)
	private WebElement updateWorkItem;
	
	public WebElement getClientID()
	{
		return clientID;
	}
	
	public void updateWorkItemClick()
	{
		updateWorkItem.click();
	}
}
