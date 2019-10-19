package com.swein.framework.module.queuemanager.needtodo;

import java.io.InputStream;

public interface CallBackListener {

    void onSuccess(InputStream inputStream);

    void onFailure();
}
