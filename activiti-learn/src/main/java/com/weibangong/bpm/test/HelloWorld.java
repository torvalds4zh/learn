package com.weibangong.bpm.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by chenbo on 16/7/18.
 */
public class HelloWorld {

    private ProcessEngine processEngine;

    @Before
    public void startUp() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{"classpath*:META-INF/activiti.cfg.xml"});

        processEngine = applicationContext.getBean(ProcessEngine.class);
    }

    /**
     * 部署流程定义
     */
    @Test
    public void deploymentProcessDefinition() {

        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的service
                .createDeployment()//创建一个部署对象
                .name("helloWorld入门程序")//添加部署的名称
                .addClasspathResource("diagrams/hello-world.bpmn")//从classpath的资源中加载,一次只能加载一个文件
                .deploy();//完成部署

        System.out.println("部署ID: " + deployment.getId());//5001
        System.out.printf("流程定义ID: " + deployment.getName());//helloWorld入门程序
    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        String processDefinitionKey = "helloworld";
        ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例,key对应hello-world.bpmn文件中id的属性值
        System.out.println("流程实例ID: " + pi.getId());//7501
        System.out.println("流程定义ID: " + pi.getProcessDefinitionId());//helloworld:1:5004
    }

    /**
     * 查看当前人个人任务
     */
    @Test
    public void findMyPersonalTask() {
//        String assignee = "张三";
//        String assignee = "李四";
        String assignee = "王五";
        List<Task> list = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee(assignee)
                .list();

        if (!CollectionUtils.isEmpty(list)) {
            for (Task task : list) {
                System.out.println("任务ID: " + task.getId());
                System.out.println("任务名称: " + task.getName());
                System.out.println("任务的创建时间: " + task.getCreateTime());
                System.out.println("任务的办理人: " + task.getAssignee());
                System.out.println("流程实例ID: " + task.getProcessInstanceId());
                System.out.println("执行对象ID: " + task.getExecutionId());
                System.out.println("流程定义ID: " + task.getProcessDefinitionId());
                System.out.println("####################################");
            }
        }
    }

    /**
     * 完成我的任务
     */
    @Test
    public void completeMyTask() {
        String taskId = "25002";
        processEngine.getTaskService().complete(taskId);
        System.out.println("完成任务ID: " + taskId);
    }

}
