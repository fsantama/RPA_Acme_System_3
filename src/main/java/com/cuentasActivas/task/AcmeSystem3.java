package com.cuentasActivas.task;

import javax.inject.Inject;

import static com.cuentasActivas.util.Constants.*;

import java.util.ArrayList;
import java.util.List;

import com.cuentasActivas.model.Client;
import com.cuentasActivas.model.dao.ManageDDBB;
import com.cuentasActivas.service.AcmeSystem3Service;
import com.workfusion.bot.service.SecureEntryDTO;
import com.workfusion.odf2.compiler.BotTask;
import com.workfusion.odf2.core.cdi.Injector;
import com.workfusion.odf2.core.cdi.Requires;
import com.workfusion.odf2.core.task.AdHocTask;
import com.workfusion.odf2.core.task.TaskInput;
import com.workfusion.odf2.core.task.output.TaskRunnerOutput;
import com.workfusion.odf2.core.webharvest.rpa.RpaDriver;
import com.workfusion.odf2.core.webharvest.rpa.RpaFactory;
import com.workfusion.odf2.core.webharvest.rpa.RpaRunner;
import com.workfusion.odf2.service.ControlTowerServicesModule;
import com.workfusion.odf2.service.vault.SecretsVaultService;

@BotTask(requireRpa = false)
@Requires(ControlTowerServicesModule.class)
public class AcmeSystem3 implements AdHocTask {
	private final RpaRunner rpaRunner;
    private final SecretsVaultService loginAcme;
    private final SecretsVaultService loginDDBB;
    private final AcmeSystem3Service acmeSystem3Service;
    private List<String> clientIDs;
    private List<Client> clientList;
    public Client client;
	
	@Inject
	public AcmeSystem3(Injector injector) 
	{
		RpaFactory rpaFactory = injector.instance(RpaFactory.class);
		this.rpaRunner = rpaFactory.builder(RpaDriver.DESKTOP).build();
        this.loginAcme = injector.instance(SecretsVaultService.class);
        this.loginDDBB = injector.instance(SecretsVaultService.class);
        this.clientIDs = new ArrayList<String>();
        acmeSystem3Service = new AcmeSystem3Service();
	}
	
	@Override
	public TaskRunnerOutput run(TaskInput taskInput) 
	{
		rpaRunner.execute(driver -> 
		{
			SecureEntryDTO credentialsAcme = loginAcme.getEntry("cuentasActivas");
			SecureEntryDTO credentialsDDBB = loginDDBB.getEntry("acmeSystem3");
			acmeSystem3Service.openApp(SYSTEM3_PATH);
			acmeSystem3Service.acmeLogin(credentialsAcme);
			ManageDDBB manageDDBB = new ManageDDBB(credentialsDDBB);
			manageDDBB.establishConnection();
			clientIDs = manageDDBB.getClientID();
			manageDDBB.createDDBB2();
			clientList = acmeSystem3Service.seachClientID(clientIDs);
			for (Client client : clientList) 
			{
			    ManageDDBB.insertAccountData(client);
			}		
		});
		return taskInput.asResult().withColumn("example_bot_task_output", "completed_successfully");
	}
}