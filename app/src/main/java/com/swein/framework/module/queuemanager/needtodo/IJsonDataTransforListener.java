package com.swein.framework.module.queuemanager.needtodo;

public interface IJsonDataTransforListener {

    void onSuccess(String response);
    void onFailure();
}
