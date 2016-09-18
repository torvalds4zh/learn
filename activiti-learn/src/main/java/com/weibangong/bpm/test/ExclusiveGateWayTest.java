package com.weibangong.bpm.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenbo on 16/7/19.
 */
public class ExclusiveGateWayTest {
    private static ProcessEngine processEngine;

    @Before
    public void startUp(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                new String[]{"classpath*:META-INF/activiti.cfg.xml"});

        processEngine = applicationContext.getBean(ProcessEngine.class);
    }

    /**部署流程定义（从inputStream）*/
    @Test
    public void deploymentProcessDefinition_inputStream(){
        InputStream inputStreamBpmn = this.getClass().getClassLoader().getResourceAsStream("diagrams/exclusiveGateway.bpmn");
        Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
                .createDeployment()//创建一个部署对象
                .name("排他网关")//添加部署的名称
                .addInputStream("exclusiveGateway.bpmn", inputStreamBpmn)//
                .deploy();//完成部署
        System.out.println("部署ID："+deployment.getId());//
        System.out.println("部署名称："+deployment.getName());//
    }

//    @Test
//    public void deleteDeployment(){
//        Deployment deployment = processEngine.getRepositoryService().createDeploymentQuery().deploymentName("排他网关").singleResult();
//        System.out.println("部署ID: " + deployment.getId() + "    " + "部署Category: " + deployment.getCategory() + "    " + "部署名称: "+ deployment.getName() + "    " + "部署tenantId: " + deployment.getTenantId());
//        processEngine.getRepositoryService().deleteDeployment(deployment.getId(), true);
//        System.out.println("已删除");
//    }

    /**
     * 启动流程实例
     */
    @Test
    public void startProcessInstance() {
        String processDefinitionKey = "exclusiveGateway";
        ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例,key对应hello-world.bpmn文件中id的属性值
        System.out.println("流程实例ID: " + pi.getId());//37501
        System.out.println("流程定义ID: " + pi.getProcessDefinitionId());//exclusiveGateway:1:37504
    }

    /**查询当前人的个人任务*/
    @Test
    public void findMyPersonalTask(){
        String assignee = "李四";
        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .createTaskQuery()//创建任务查询对象
                /**查询条件（where部分）*/
                .taskAssignee(assignee)//指定个人任务查询，指定办理人
//						.taskCandidateUser(candidateUser)//组任务的办理人查询
//						.processDefinitionId(processDefinitionId)//使用流程定义ID查询
//						.processInstanceId(processInstanceId)//使用流程实例ID查询
//						.executionId(executionId)//使用执行对象ID查询
                /**排序*/
                .orderByTaskCreateTime().asc()//使用创建时间的升序排列
                /**返回结果集*/
//						.singleResult()//返回惟一结果集
//						.count()//返回结果集的数量
//						.listPage(firstResult, maxResults);//分页查询
                .list();//返回列表
        if(list!=null && list.size()>0){
            for(Task task:list){
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间:"+task.getCreateTime());
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID："+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("########################################################");
            }
        }
    }

    /**完成我的任务*/
    @Test
    public void completeMyPersonalTask(){
        //任务ID
        String taskId = "62504";
        //完成任务的同时，设置流程变量，使用流程变量用来指定完成任务后，下一个连线，对应exclusiveGateWay.bpmn文件中${money>1000}
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("money", 700);
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId,variables);
        System.out.println("完成任务：任务ID："+taskId);
    }

}
