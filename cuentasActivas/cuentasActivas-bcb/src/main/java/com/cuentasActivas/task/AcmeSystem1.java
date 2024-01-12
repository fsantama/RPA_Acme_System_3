package com.cuentasActivas.task;

import javax.inject.Inject;

import static com.cuentasActivas.util.Constants.*;

import java.util.ArrayList;
import java.util.List;

import com.cuentasActivas.model.WorkItem;
import com.cuentasActivas.model.dao.ManageDDBB;
import com.cuentasActivas.rpa.ActiveAccountsPlaneRobot;
//import com.cuentasActivas.service.WorkItemsService;
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
public class AcmeSystem1 implements AdHocTask
{
    private final RpaRunner rpaRunner;
    private final SecretsVaultService loginAcme;
    private final SecretsVaultService loginDDBB;
    private List<WorkItem> workItems;

    @Inject
    public	AcmeSystem1(Injector injector)
    {
    	RpaFactory rpaFactory = injector.instance(RpaFactory.class);
        this.rpaRunner = rpaFactory.builder(RpaDriver.UNIVERSAL).build();
        this.loginAcme = injector.instance(SecretsVaultService.class);
        this.loginDDBB = injector.instance(SecretsVaultService.class);
        this.workItems = new ArrayList<WorkItem>();
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
			activeAccount.openUrl(WORK_ITEMS_URL);
			workItems = activeAccount.getAccount();
			ManageDDBB manageDDBB = new ManageDDBB(credentialsDDBB);
			manageDDBB.establishConnection();
			manageDDBB.createDDBB();
			manageDDBB.insertData(workItems);
			manageDDBB.closeConnection();
		});
		return taskInput.asResult()
				.withColumn("open_acme_system_task_output", "completed_successfully");
	}
}