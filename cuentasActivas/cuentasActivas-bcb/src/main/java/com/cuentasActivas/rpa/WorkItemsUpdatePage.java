package com.cuentasActivas.rpa;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.cuentasActivas.model.Account;
import com.cuentasActivas.model.Client;
import com.cuentasActivas.model.WorkItem;

import static com.cuentasActivas.util.Constants.*;

import java.util.List;

public class WorkItemsUpdatePage
{
	@FindBy (xpath = ADD_COMMENTS)
	private WebElement addComments;
	
	@FindBy (xpath = NEW_STATUS)
	private WebElement newStatus;
	
	@FindBy (xpath = UPDATE_BOTTON)
	private WebElement updateButton;
	
	@FindBy (xpath = REJECTED_STATUS)
	private WebElement rejectedStatus;
	
	@FindBy (xpath = COMPLETED_STATUS)
	private WebElement completedStatus;
	
	public void clickAddComments()
	{
		addComments.click();
	}
	
	public void clickNewStatus()
	{
		newStatus.click();
	}
	
	public void clickRejectedStatus()
	{
		rejectedStatus.click();
	}
	
	public void clickCompletedStatus()
	{
		completedStatus.click();
	}
	
	public void clickUpdateButton()
	{
		updateButton.click();
	}

	/**
	 * Establece el estado como completado.
	 */
	public void completedStatus()
	{
		clickNewStatus();
		clickCompletedStatus();
	}

	/**
	 * Establece el estado como rechazado.
	 */
	public void rejectedStatus()
	{
		clickNewStatus();
		clickRejectedStatus();
	}

	/**
	 * Agrega un comentario al elemento de comentarios en la página de actualización del elemento de trabajo.
	 *
	 * @param workItem El elemento de trabajo actual.
	 */
	public void addComment(WorkItem workItem) 
	{
	    Client client = workItem.getClient();
	    List<Account> activeAccounts = client.getAccounts();
	    StringBuilder comment = new StringBuilder();
	    if (!activeAccounts.isEmpty())
	    {
	        comment.append("Existe cuenta/s activa/s: ");
	    	for (int i = 0; i < activeAccounts.size(); i++) 
	    	{
	    		comment.append(activeAccounts.get(i).getAccountID());
	    		if (i < activeAccounts.size() - 1) 
	    		{
	    			comment.append(", ");
	    		}
	    	}
	    }
	    else
	    {
	    	comment.append("No existen cuentas activas para este cliente");
	    }
	    addComments.sendKeys(comment.toString());
	}
}