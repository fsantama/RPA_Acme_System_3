package com.cuentasActivas.rpa;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.workfusion.bot.service.SecureEntryDTO;

import static com.cuentasActivas.util.Constants.*;

public class LoginPage 
{
	@FindBy (xpath = USER_BOX)
	private WebElement userBox;

	@FindBy (xpath = PWD_BOX)
	private WebElement pwdBox;

	@FindBy (xpath = LOGIN_BUTTON)
	private WebElement loginButton;

	/**
	 * Realiza el proceso de inicio de sesión en la página web.
	 *
	 * @param credentials Las credenciales (usuario y contraseña) para iniciar sesión.
	 */
	public void loginPage(SecureEntryDTO credentials)
	{
		userBox.clear();
		pwdBox.clear();
		userBox.sendKeys(credentials.getKey());
		pwdBox.sendKeys(credentials.getValue());
		loginButton.click();
	}
}
