package com.weibangong.bpm.test;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.*;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by chenbo on 16/7/20.
 */
public class DynamicBpmTest {
    private static ProcessEngine processEngine;

    @Before
    public void startUp(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath*:/META-INF/activiti.cfg.xml"});
        processEngine = applicationContext.getBean(ProcessEngine.class);
    }

    @Test
    public void testDynamicDeploy() throws Exception {
        // 1. Build up the model from scratch
        BpmnModel model = new BpmnModel();
        org.activiti.bpmn.model.Process process = new org.activiti.bpmn.model.Process();
        model.addProcess(process);
        process.setId("my-process");

        process.addFlowElement(createStartEvent());
        process.addFlowElement(createUserTask("userTask1", "提交请假申请", "张三"));
        process.addFlowElement(createUserTask("userTask2", "审批[部门经理]", "李四"));
        process.addFlowElement(createUserTask("userTask3", "审批[总经理]", "王五"));
        process.addFlowElement(createEndEvent());

        process.addFlowElement(createSequenceFlow("start", "userTask1"));
        process.addFlowElement(createSequenceFlow("userTask1", "userTask2"));
        process.addFlowElement(createSequenceFlow("userTask2", "userTask3"));
        process.addFlowElement(createSequenceFlow("userTask3", "end"));

        // 2. Generate graphical information
        new BpmnAutoLayout(model).execute();

        // 3. Deploy the process to the engine
        Deployment deployment = processEngine.getRepositoryService().createDeployment()
                .addBpmnModel("dynamic-model.bpmn", model).name("Dynamic process deployment")
                .deploy();

        // 4. Start a process instance
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("my-process");

        // 5. Check if task is available
        List<org.activiti.engine.task.Task> tasks = processEngine.getTaskService().createTaskQuery()
                .processInstanceId(processInstance.getId()).list();

        Assert.assertEquals(1, tasks.size());
        Assert.assertEquals("提交请假申请", tasks.get(0).getName());
        Assert.assertEquals("张三", tasks.get(0).getAssignee());

        // 6. Save process diagram to a file
        InputStream processDiagram = processEngine.getRepositoryService()
                .getProcessDiagram(processInstance.getProcessDefinitionId());
        FileUtils.copyInputStreamToFile(processDiagram, new File("target/dynamic-model.png"));

        // 7. Save resulting BPMN xml to a file
        InputStream processBpmn = processEngine.getRepositoryService()
                .getResourceAsStream(deployment.getId(), "dynamic-model.bpmn");
        FileUtils.copyInputStreamToFile(processBpmn,
                new File("target/process.bpmn20.xml"));
    }

    protected UserTask createUserTask(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setAssignee(assignee);
        return userTask;
    }

    protected SequenceFlow createSequenceFlow(String from, String to) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }

    protected StartEvent createStartEvent() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }

    protected EndEvent createEndEvent() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }
}
