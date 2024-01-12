package com.cuentasActivas.util;

public class Constants
{
	// SYSTEM 3
	public static final String CHECK_BOX = "//*[@class='WindowsForms10.BUTTON.app.0.141b42a_r8_ad1'][@name='checkBox1']";
	public static final String CLIENT = "//*[@class='ListItem']";
	public static final String CLIENTS_ACCOUNT_BUTTON = "//*[@class='WindowsForms10.BUTTON.app.0.141b42a_r8_ad1'][@name='button1']";
	public static final String CLIENTS_ACCOUNT_WINDOWS = "[class=\"WindowsForms10.SysListView32.app.0.141b42a_r8_ad1\"]";
	public static final String CLIENT_BUTTON = "//*[@class='MenuItem'][@name='Clients']";
	public static final String LOGIN_BUTTON_S3	 = "//*[@class='WindowsForms10.BUTTON.app.0.141b42a_r8_ad1'][@name='button1']";
	public static final String LOGIN_WINDOW_TITLE = "//*[@class='WindowsForms10.Window.8.app.0.141b42a_r8_ad1'][@title='ACME System3']";
	public static final String SEARCH_BUTTON = "//*[@class='WindowsForms10.BUTTON.app.0.141b42a_r8_ad1'][@name='button1']";
	public static final String SEARCH_FORM = "//*[@class='WindowsForms10.EDIT.app.0.141b42a_r8_ad1']";
	public static final String PASSWORD_BOX = "//*[@class='WindowsForms10.EDIT.app.0.141b42a_r8_ad1'][@name='textBox2']";
	public static final String SYSTEM3_PATH = "D:\\Descargas\\Proyecto_Cuentas_Activas_WF\\Acme System 3\\ACME-System3.exe";
	public static final String USERNAME_BOX = "//*[@class='WindowsForms10.EDIT.app.0.141b42a_r8_ad1'][@name='textBox1']";
	public static final String WINDOW_TITLE = "//*[@class='WindowsForms10.Window.8.app.0.141b42a_r8_ad1'][@title='ACME System 3']";
	
	// URL 
	public static final String LOGIN_URL = "https://acme-test.uipath.com/login";
	public static final String WFDB_URL = "jdbc:sqlserver://;serverName=localhost;port=11433;databaseName=wfdb";
	public static final String WORK_ITEMS_UPDATE = "https://acme-test.uipath.com/work-items/update/";
	public static final String WORK_ITEMS_URL = "https://acme-test.uipath.com/work-items";
    
    // XPATH
	public static final String ADD_COMMENTS = "//*[@id=\"newComment\"]";
	public static final String COMPLETED_STATUS = "//*[@id=\"loginForm\"]/div[2]/div/div/div/ul/li[4]/a/span[1]";
    public static final String LOGIN_BUTTON = "/html/body/div/div[2]/div/div/div/form/button";
    public static final String NEW_STATUS = "//*[@id=\"loginForm\"]/div[2]/div/div/button/span[1]";
    public static final String PWD_BOX = "//*[@id=\"password\"]";
    public static final String REJECTED_STATUS = "//*[@id=\"loginForm\"]/div[2]/div/div/div/ul/li[3]/a/span[1]";
    public static final String UPDATE_BOTTON = "//*[@id=\"buttonUpdate\"]";
    public static final String UPDATE_WORK_ITEM = "/html/body/div/div[2]/div/div[2]/div/div/div[2]/p[2]/a/button";
    public static final String USER_BOX = "//*[@id=\"email\"]";
    public static final String WORK_ITEMS_BUTTON = "//*[@id=\"dashmenu\"]/div[2]/a/button";
    public static final String WORK_ITEMS_CLIENTID = "/html/body/div/div[2]/div/div[2]/div/div/div[1]/p";
    public static final String WORK_ITEMS_PAGES = "/html/body/div/div[2]/div/nav/ul";
    public static final String WORK_ITEMS_TABLE = "/html/body/div/div[2]/div/table";
    
}
