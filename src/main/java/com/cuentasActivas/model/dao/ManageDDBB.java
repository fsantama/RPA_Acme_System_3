package com.cuentasActivas.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.cuentasActivas.util.Constants.*;

import com.cuentasActivas.model.Account;
import com.cuentasActivas.model.Client;
import com.cuentasActivas.model.WorkItem;
import com.workfusion.bot.service.SecureEntryDTO;

public class ManageDDBB 
{
	private final SecureEntryDTO credentials;
	private static Connection connection;

	public ManageDDBB (SecureEntryDTO credentials)
	{
		this.credentials = Objects.requireNonNull(credentials);
	}

	/**
	 * Recupera los datos de la base de datos.
	 *
	 * @return Una lista de elementos de trabajo recuperados.
	 */
	public List<WorkItem> retrieveData() 
	{
		List<WorkItem> workItems = new ArrayList<>();
		try 
		{
			ResultSet workItemsResultSet = connection.createStatement().executeQuery("SELECT * FROM workitems");
			while (workItemsResultSet.next()) 
			{
				WorkItem workItem = new WorkItem();
				workItem.setWiid(workItemsResultSet.getInt("wi_id"));
				workItem.setClientID(workItemsResultSet.getString("client_id"));
				ResultSet accountsResultSet = connection.createStatement().executeQuery(
	                    "SELECT * FROM clientaccounts WHERE client_id = '" + workItem.getClientID() + "'"
	                    );
				Client client = new Client();
				client.setClientID(workItem.getClientID());
				List<Account> accounts = new ArrayList<>();
				while (accountsResultSet.next()) 
				{
					Account account = new Account();
					account.setAccountID(accountsResultSet.getString("account_id"));
					account.setTimestamp(accountsResultSet.getString("timestamp"));
					account.setAmount(accountsResultSet.getString("amount"));
					account.setStatus(accountsResultSet.getString("status"));
					accounts.add(account);
					}
				client.setAccounts(accounts);
				workItem.setClient(client);
				workItems.add(workItem);
				}
			System.out.println("Datos recuperados:");
			for (WorkItem workItem : workItems) 
			{
				System.out.println("WorkItem - Wiid: " + workItem.getWiid() + ", ClientID: " + workItem.getClientID());
				Client client = workItem.getClient();
				System.out.println("Client - ClientID: " + client.getClientID());
				List<Account> accounts = client.getAccounts();
				for (Account account : accounts) 
				{
					System.out.println("Account - AccountID: " + account.getAccountID() +
							", Timestamp: " + account.getTimestamp() +
							", Amount: " + account.getAmount() +
							", Status: " + account.getStatus());
					}
				System.out.println("---------------------------------------");
				}
			System.out.println("---------------------------------------");
			System.out.println("Datos recuperados de las tablas");
			System.out.println("---------------------------------------");
			} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("---------------------------------------");
			System.out.println("Error al recuperar datos de las tablas");
			System.out.println("---------------------------------------");
			}
		return workItems;
	}

	/**
	 * Obtiene la lista de ID de clientes.
	 *
	 * @return Una lista de ID de clientes.
	 */
	public List<String> getClientID()
	{
		List<String> clientIDs = new ArrayList<>();
		PreparedStatement preparedStatement;

		try
		{
			preparedStatement = connection.prepareStatement("SELECT DISTINCT client_id From workitems");
			ResultSet result = preparedStatement.executeQuery();
			while(result.next()) 
			{
				String clientID = result.getString(1);
				clientIDs.add(clientID);
			}
			preparedStatement.close();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Error al obtener los client_id desde la base de datos.");
		}
		return clientIDs;
	}

	/**
	 * Cierra la conexión a la base de datos.
	 */
	public void closeConnection() 
	{
		try 
		{
			connection.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Establece la conexión a la base de datos.
	 */
	public void establishConnection() 
	 {
		 try 
		 {
			 connection = DriverManager.getConnection(WFDB_URL, credentials.getKey(), credentials.getValue());
			 } 
		 catch (SQLException e) 
		 {
			 e.printStackTrace();
			 System.out.println("No se pudo establecer la conexión a la base de datos.");
			 }
		 }

	/**
	 * Inserta datos en la tabla workitems de la base de datos.
	 *
	 * @param workItems La lista de elementos de trabajo a insertar.
	 */
	public void insertData(List<WorkItem> workItems) 
	{
			PreparedStatement preparedStatement;
			try 
			{
				for(WorkItem workItem : workItems) {
					preparedStatement = connection.prepareStatement("INSERT INTO workitems (wi_id, client_id, report, status) VALUES (?,?,?,?)");
					preparedStatement.setString(1, String.valueOf(workItem.getWiid()));
					preparedStatement.setString(2, String.valueOf(workItem.getClient().getClientID()));
					preparedStatement.setString(3, "3");
					preparedStatement.setString(4, "4");
					preparedStatement.executeUpdate();
					preparedStatement.close();
					System.out.println("---------------------------------------");
					System.out.println("SUBIENDO DATOS A BBDD");
					System.out.println("---------------------------------------");
				}
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
				System.out.println("---------------------------------------");
				System.out.println("NO SE HAN PODIDO SUBIR LOS DATOS");
				System.out.println("---------------------------------------");
			}
		}

	/**
	 * Crea la tabla workitems en la base de datos si no existe.
	 */
	public void createDDBB() 
	{
		try 
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate("IF NOT EXISTS (SELECT * FROM sys.tables WHERE name= 'workitems') " +
			"BEGIN " +
					"CREATE TABLE workitems (wi_id VARCHAR(20), client_id VARCHAR(255), report VARCHAR(80), status VARCHAR(20)) " +
			"END;");
			System.out.println("---------------------------------------");
			System.out.println("SE HA CREADO LA TABLA");
			System.out.println("---------------------------------------");
			statement.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("---------------------------------------");
			System.out.println("NO SE HA CREADO LA TABLA");
			System.out.println("---------------------------------------");
		}
	}

	/**
	 * Crea la tabla clientaccounts en la base de datos si no existe.
	 */
	public void createDDBB2() 
	{
		try 
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate("IF NOT EXISTS (SELECT * FROM sys.tables WHERE name= 'clientaccounts') " +
			"BEGIN " +
					"CREATE TABLE clientaccounts (client_id VARCHAR(20), account_id VARCHAR(255), timestamp VARCHAR(80), amount VARCHAR(20), status VARCHAR(20)) " +
			"END;");
			System.out.println("---------------------------------------");
			System.out.println("SE HA CREADO LA TABLA");
			System.out.println("---------------------------------------");
			statement.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("---------------------------------------");
			System.out.println("NO SE HA CREADO LA TABLA");
			System.out.println("---------------------------------------");
		}
	}

	/**
	 * Inserta datos en la tabla clientaccounts de la base de datos.
	 *
	 *@param client El cliente del cual se insertan las cuentas.
	 */
	public static void insertAccountData(Client client) 
	{
		try 
		{
			if (connection == null || connection.isClosed()) 
			{
				System.out.println("Error: La conexión a la base de datos no está disponible.");
				return;
			}
			String clientId = client.getClientID();
			List<Account> accounts = client.getAccounts();
			for (Account account : accounts) 
			{
				String sql = "INSERT INTO clientaccounts (client_id, account_id, timestamp, amount, status) VALUES (?, ?, ?, ?, ?)";
				try (PreparedStatement statement = connection.prepareStatement(sql)) 
				{
					statement.setString(1, clientId);
					statement.setString(2, account.getAccountID());
					statement.setString(3, account.getTimestamp());
					statement.setString(4, account.getAmount());
					statement.setString(5, account.getStatus());
					statement.executeUpdate();
					statement.close();
					}
				}
			System.out.println("Datos insertados en la base de datos correctamente.");
			} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			System.out.println("Error al insertar datos en la base de datos.");
		}
	}
}
