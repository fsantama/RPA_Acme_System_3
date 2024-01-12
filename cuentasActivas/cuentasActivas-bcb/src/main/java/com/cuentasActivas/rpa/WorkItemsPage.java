package com.cuentasActivas.rpa;

import static com.cuentasActivas.util.Constants.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkItemsPage 
{
	@FindBy (xpath = WORK_ITEMS_BUTTON)
	private WebElement workItemButton;
	
	@FindBy (xpath = WORK_ITEMS_PAGES)
	private WebElement pages;
	
	@FindBy (xpath = WORK_ITEMS_TABLE)
	private WebElement table;
	
	public void clickWorkItemButton()
	{
		workItemButton.click();
	}
	
	public WebElement getPages()
	{
		return pages;
	}
	
	public WebElement getTable()
	{
		return table;
	}
}
