package com.cuentasActivas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cuentasActivas.model.Client;
import com.cuentasActivas.model.WorkItem;
import com.cuentasActivas.rpa.ActiveAccountsPlaneRobot;

import static com.cuentasActivas.util.Constants.*;

public class WorkItemsService 
{
	public List<WorkItem> workItemList = new ArrayList<>();

	/**
	 * Verifica las cuentas en la tabla y agrega a la lista los elementos WorkItem que cumplen con los criterios.
	 *
	 * @param table La tabla de cuentas a verificar.
	 * @param page  El número de página actual.
	 */
	public void verifyAccount(WebElement table, int page) 
	{
		List <WebElement> colWiid; 
		List <WebElement> colDescription; 
		List <WebElement> colStatus;
		int	i;

		i = 0;
		colWiid = table.findElements(By.xpath(".//tr/td[2]"));
		colDescription = table.findElements(By.xpath(".//tr/td[3]"));
		colStatus = table.findElements(By.xpath(".//tr/td[5]"));
		for (WebElement element : colDescription)
		{
			if (element.getText().equals("Verify Account Position") && colStatus.get(i).getText().equals("Open"))
			{
				WorkItem workItem = new WorkItem();
				workItem.setWiid(Integer.valueOf(colWiid.get(i).getText()));
				workItem.setPage(page);
				workItemList.add(workItem);	
			}
			i++;
		}
		for (WorkItem workItem : workItemList) 
		{
			System.out.println("---------------------------------------");
			System.out.println("Cuenta verificada: " + "/" + workItem.getWiid());
			System.out.println("---------------------------------------");
		}
	}

	/**
	 * Busca el cliente asociado a cada WorkItem y lo agrega a la lista de WorkItem.
	 *
	 * @param robot El robot que realiza las acciones en la interfaz gráfica.
	 * @return La lista actualizada de WorkItem con información del cliente.
	 */
	public List<WorkItem> searchClient(ActiveAccountsPlaneRobot robot)
	{
		for (WorkItem workItem : workItemList)
		{
			Pattern pattern = Pattern.compile("Client ID: (\\w+)");
			robot.openUrl(WORK_ITEMS_URL + "/" + workItem.getWiid());
			WebElement customerId = robot.getClientID();
			Matcher matcher = pattern.matcher(customerId.getText());
			if (matcher.find()) {
				Client newClient = new Client();
				newClient.setClientID(matcher.group(1));
				workItem.setClient(newClient);
				System.out.println("CLIENT -----------> " + newClient.getClientID().toString());
			}
		}
		return (workItemList);
	}
}
