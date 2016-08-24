package com.weibangong.bpm.test;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by chenbo on 16/6/24.
 */
public class demo {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{"classpath*:META-INF/activiti.cfg.xml"});
        // Create Activiti process engine
//        ProcessEngine processEngine = ProcessEngineConfiguration
//                .createStandaloneProcessEngineConfiguration()
//                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
//                .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
//                .setAsyncExecutorEnabled(true)
//                .setAsyncExecutorActivate(false)
//                .buildProcessEngine();

        ProcessEngine processEngine = applicationContext.getBean(ProcessEngine.class);

        // Get Activiti services
//        RepositoryService repositoryService = processEngine.getRepositoryService();
        RepositoryService repositoryService = applicationContext.getBean(RepositoryService.class);
//        RuntimeService runtimeService = processEngine.getRuntimeService();
        RuntimeService runtimeService = applicationContext.getBean(RuntimeService.class);

        // Deploy the process definition
        repositoryService.createDeployment()
                .addClasspathResource("financial-report-process.bpmn20.xml")
                .deploy();

        // Start a process instance
        String procId = runtimeService.startProcessInstanceByKey("financialReport").getId();

        // Get the first task
//        TaskService taskService = processEngine.getTaskService();
        TaskService taskService = applicationContext.getBean(TaskService.class);
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
        for (Task task : tasks) {
            System.out.println("Following task is available for accountancy group: " + task.getName());

            // claim it
            taskService.claim(task.getId(), "fozzie");
        }

        // Verify Fozzie can now retrieve the task
        tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
        for (Task task : tasks) {
            System.out.println("Task for fozzie: " + task.getName());

            // Complete the task
            taskService.complete(task.getId());
        }

        System.out.println("Number of tasks for fozzie: "
                + taskService.createTaskQuery().taskAssignee("fozzie").count());

        // Retrieve and claim the second task
        tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
        for (Task task : tasks) {
            System.out.println("Following task is available for accountancy group: " + task.getName());
            taskService.claim(task.getId(), "kermit");
        }

        // Completing the second task ends the process
        for (Task task : tasks) {
            taskService.complete(task.getId());
        }

        // verify that the process is actually finished
//        HistoryService historyService = processEngine.getHistoryService();
        HistoryService historyService = applicationContext.getBean(HistoryService.class);
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
        System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
    }
}
