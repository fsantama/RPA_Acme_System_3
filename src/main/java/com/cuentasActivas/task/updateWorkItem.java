package com.cuentasActivas.task;

import static com.cuentasActivas.util.Constants.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.cuentasActivas.model.WorkItem;
import com.cuentasActivas.model.dao.ManageDDBB;
import com.cuentasActivas.rpa.ActiveAccountsPlaneRobot;
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

@BotTask
@Requires(ControlTowerServicesModule.class)
public class updateWorkItem implements AdHocTask
{
	private final RpaRunner rpaRunner;
	private final SecretsVaultService loginAcme;
	private final SecretsVaultService loginDDBB;
	private List<WorkItem> workItemList;
	
	@Inject
	public updateWorkItem(Injector injector)
	{
		RpaFactory rpaFactory = injector.instance(RpaFactory.class);
		this.rpaRunner = rpaFactory.builder(RpaDriver.UNIVERSAL).build();
		this.loginAcme = injector.instance(SecretsVaultService.class);
		this.loginDDBB = injector.instance(SecretsVaultService.class);
		this.workItemList = new ArrayList<WorkItem>();
	}
	
	@Override
	public TaskRunnerOutput run(TaskInput taskInput)
	{
		rpaRunner.execute(driver -> 
		{
			SecureEntryDTO credentialsAcme = loginAcme.getEntry("cuentasActivas");
			SecureEntryDTO credentialsDDBB = loginDDBB.getEntry("acmeSystem3");
			ActiveAccountsPlaneRobot activeAccount = new ActiveAccountsPlaneRobot(driver, credentialsAcme);
			activeAccount.openUrl(LOGIN_URL);
			activeAccount.login();
			ManageDDBB manageDDBB = new ManageDDBB(credentialsDDBB);
			manageDDBB.establishConnection();
			workItemList = manageDDBB.retrieveData();
			for (WorkItem workItem : workItemList)
			{
				activeAccount.openUrl(WORK_ITEMS_UPDATE + workItem.getWiid());		
				activeAccount.updateWorkItem(workItem);
			}
		});
		return taskInput.asResult()
				.withColumn("open_acme_system_task_output", "completed_successfully");
	}
}
