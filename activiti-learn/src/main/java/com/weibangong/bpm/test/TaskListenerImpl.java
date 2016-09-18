package com.weibangong.bpm.test;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * Created by chenbo on 16/7/19.
 */
public class TaskListenerImpl implements TaskListener {
    private String assignee;

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee(assignee);
    }
}
