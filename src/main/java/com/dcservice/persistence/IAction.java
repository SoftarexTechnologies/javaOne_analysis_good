package com.dcservice.persistence;

public interface IAction
{
    public void onBeforeExecute();

    public void onExecuted();

    public void execute(Object obj);
}
