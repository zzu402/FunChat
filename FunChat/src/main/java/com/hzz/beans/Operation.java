package com.hzz.beans;

public class Operation {

    private String runner;
    private String action;
    private Long lastOperation;

    public String getRunner() {
        return runner;
    }

    public void setRunner(String runner) {
        this.runner = runner;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(Long lastOperation) {
        this.lastOperation = lastOperation;
    }
}
