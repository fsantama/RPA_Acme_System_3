package com.cuentasActivas.service;

import com.workfusion.bot.service.SecureEntryDTO;
import com.workfusion.rpa.helpers.RPA;
import com.workfusion.rpa.helpers.UiElement;
import com.workfusion.rpa.helpers.UiElementCollection;

import static com.cuentasActivas.util.Constants.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.cuentasActivas.model.Account;
import com.cuentasActivas.model.Client;


public class AcmeSystem3Service 
{
	/**
	 * Espera hasta que la ventana con el título dado esté disponible.
	 *
	 * @param windowTitle El título de la ventana a esperar.
	 * @param maxAttempts El número máximo de intentos.
	 */
	public void waitForWindow(String windowTitle, int maxAttempts)
	{
		int attempts = 0;
		while (attempts < maxAttempts)
		{
			try 
			{
				RPA.switchTo().window(windowTitle);
				System.out.println("La ventana " + windowTitle + " está disponible.");
				return;
			} 
			catch (Exception e) 
			{
				System.out.println("La ventana " + windowTitle + " aún no está disponible.");
			}
			RPA.sleep(1000);
			attempts++;
		}
	}

	/**
	 * Busca las cuentas activas asociadas a un cliente en ACME System 3.
	 *
	 * @param clientID El ID del cliente para buscar cuentas asociadas.
	 * @return Un objeto Client con el ID del cliente y la lista de cuentas activas.
	 */
	public Client searchAccounts(String clientID)
	{
		List<Account> accountsList = new ArrayList<>();
		try 
		{
			RPA.$(CLIENT).doubleClick();
			RPA.$(CLIENTS_ACCOUNT_BUTTON).click();
			UiElement accountSelector = RPA.$(CLIENTS_ACCOUNT_WINDOWS);
			UiElementCollection accountSelectors = accountSelector.$$(".ListItem");
			UiElementCollection accountdata = accountSelector.$$(".TextBlock");
			for (int i = 0; i < accountSelectors.size() - 3; i += 4) 
			{
				String accountID = RPA.$(accountdata.get(i)).getText();
				String timestamp = RPA.$(accountdata.get(i + 1)).getText();
				String amount = RPA.$(accountdata.get(i + 2)).getText();
				String status = RPA.$(accountdata.get(i + 3)).getText();
				if (status.contains("Active")) 
				{
					accountsList.add(new Account(accountID, timestamp, amount, status));
				}
				if (!StringUtils.isNumeric(accountID))
					break ;
				}
			System.out.println("Cliente: " + clientID);
			for (Account account : accountsList) 
			{
				System.out.println("  Cuenta ID: " + account.getAccountID());
				System.out.println("  Timestamp: " + account.getTimestamp());
				System.out.println("  Amount: " + account.getAmount());
				System.out.println("  Status: " + account.getStatus());
				System.out.println();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Error durante la búsqueda de cuentas en ACME System 3.");
			System.out.println("Exception message: " + e.getMessage());
		}
		finally 
		{
			System.out.println("Closing windows...");
			RPA.$(".Button").click();
			RPA.$(".Button").click();
			System.out.println("Windows closed.");
		}
		return new Client(clientID, accountsList);
	}

	/**
	 * Busca información de clientes por sus IDs en ACME System 3.
	 *
	 * @param clientIDs Lista de IDs de clientes para buscar información.
	 * @return Una lista de objetos Client con información de los clientes.
	 */
	public List<Client> seachClientID(List<String> clientIDs)
	{
		List<Client> clientList = new ArrayList<>();
		try
		{
			RPA.switchTo().window(WINDOW_TITLE);
			RPA.$(CLIENT_BUTTON).click();
			RPA.$(CLIENT_BUTTON).pressTab();
			RPA.$(CLIENT_BUTTON).pressTab();
			RPA.$(CLIENT_BUTTON).pressEnter();
			RPA.$(CLIENT_BUTTON).pressTab();
			RPA.$(CLIENT_BUTTON).pressEnter();
			RPA.$(CHECK_BOX).click();
			for (String clientID : clientIDs)
			{
				RPA.$(SEARCH_FORM).setText(clientID);
				RPA.$(SEARCH_BUTTON).click();
				clientList.add(searchAccounts(clientID));
			}
			return  clientList;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Error durante la búsqueda de cliente por ID en ACME System 3.");
	    }
		return clientList;
	}

	/**
	 * Realiza el inicio de sesión en ACME System 3.
	 *
	 * @param credentials Las credenciales de inicio de sesión.
	 */
	public void acmeLogin(SecureEntryDTO credentials)
	{
		try
		{
		RPA.switchTo().window(LOGIN_WINDOW_TITLE);
		RPA.$(USERNAME_BOX).setText(credentials.getKey());
		RPA.$(PASSWORD_BOX).setText(credentials.getValue());
		RPA.$(LOGIN_BUTTON_S3).click();
		System.out.println("Exito durante el inicio de sesión en ACME System 3.");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Error durante el inicio de sesión en ACME System 3.");
		}
		waitForWindow(WINDOW_TITLE, 3);
	}

	/**
	 * Abre la aplicación especificada por la ruta.
	 *
	 * @param appPath La ruta de la aplicación a abrir.
	 */
	public void openApp(String appPath)
	{
		RPA.open(appPath);
	}
}
