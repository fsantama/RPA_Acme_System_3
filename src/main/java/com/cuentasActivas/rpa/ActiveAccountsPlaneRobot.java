package com.cuentasActivas.rpa;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.cuentasActivas.model.Account;
import com.cuentasActivas.model.Client;
import com.cuentasActivas.model.WorkItem;
import com.cuentasActivas.service.WorkItemsService;
import com.workfusion.bot.service.SecureEntryDTO;
import com.workfusion.odf2.core.webharvest.rpa.RpaDriver;
import com.workfusion.rpa.driver.Driver;

import static com.cuentasActivas.util.Constants.*;

public class ActiveAccountsPlaneRobot 
{
	private final Driver driver;
    private final SecureEntryDTO credentials;
    private final WorkItemsService workItemsService;
    
	private LoginPage loginPage;
	private WorkItemsPage workItemsPage;
	private WorkItemsDetailsPage workItemsDetailsPage;
	private WorkItemsUpdatePage workItemsUpdatePage;
		
	public ActiveAccountsPlaneRobot(Driver driver,SecureEntryDTO credentials)
	{
    	this.driver = Objects.requireNonNull(driver);
        this.credentials = Objects.requireNonNull(credentials);
        
        setOptions();
        
		loginPage = PageFactory.initElements(driver, LoginPage.class);
		workItemsPage = PageFactory.initElements(driver, WorkItemsPage.class);
		workItemsDetailsPage = PageFactory.initElements(driver, WorkItemsDetailsPage.class);
		workItemsUpdatePage = PageFactory.initElements(driver, WorkItemsUpdatePage.class);
		workItemsService = new WorkItemsService();
	}
	
	/**
	 * Actualiza el estado de un ítem de trabajo.
	 * 
	 * @param workItem El ítem de trabajo que se actualizará.
	 */
	public void updateWorkItem(WorkItem workItem)
	{
		Client client = workItem.getClient();
		List<Account> accountList = client.getAccounts();
		workItemsUpdatePage.addComment(workItem);
		if (!accountList.isEmpty())
		{
			workItemsUpdatePage.completedStatus();
		}
		else
		{
			workItemsUpdatePage.rejectedStatus();
		}
		workItemsUpdatePage.clickUpdateButton();
	}
	
	/**
	 * Obtiene el identificador del cliente de la página de detalles del ítem de trabajo.
	 * 
	 * @return El elemento que contiene el identificador del cliente.
	 */
	public WebElement getClientID()
	{
		return workItemsDetailsPage.getClientID();
	}

	/**
	 * Obtiene la lista de workItems a partir de las páginas disponibles.
	 * 
	 * @return Lista workItems.
	 */
	public List<WorkItem> getAccount()
	{
		int	size;
		WebElement listPages; 
		List<WebElement> pages; 

		listPages = workItemsPage.getPages();
		pages = listPages.findElements(By.className("page-numbers"));
		size = pages.size() - 2;
		for (int i = 2; i <= size + 2; i++)
		{
			workItemsService.verifyAccount(workItemsPage.getTable(), i - 1);
			if (i <= size + 1)
				openUrl(WORK_ITEMS_URL + "?page=" + i);
		}
		List<WorkItem> workItemList = workItemsService.searchClient(this);
		return (workItemList);
	}

	/**
	 * Configura las opciones del WebDriver, como el navegador y los tiempos de espera.
	 */
    private void setOptions() 
    {
    	driver.switchDriver(RpaDriver.FIREFOX.getName());

        WebDriver.Options options = driver.manage();

        options.timeouts()
        .implicitlyWait(10, TimeUnit.SECONDS)
        .pageLoadTimeout(90, TimeUnit.SECONDS);

        options.deleteAllCookies();
    }

    /**
     * Inicia sesión en la página web.
     */
	public void login()
	{
		loginPage.loginPage(credentials);
	}

	/**
	 * Abre una URL en el navegador.
	 *
	 *@param url La URL que se abrirá en el navegador.
	 */
	public void openUrl (String url)
	{
		driver.navigate().to(url);
	}
}
