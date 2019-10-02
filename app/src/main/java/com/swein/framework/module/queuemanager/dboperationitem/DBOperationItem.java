package com.swein.framework.module.queuemanager.dboperationitem;

import androidx.annotation.NonNull;

public class DBOperationItem {

    public String sql;

    @NonNull
    @Override
    public String toString() {
        return sql;
    }
}
