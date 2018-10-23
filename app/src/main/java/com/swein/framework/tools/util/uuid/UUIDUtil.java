package com.swein.framework.tools.util.uuid;

import java.util.UUID;

public class UUIDUtil {

    public static String getUUIDString() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
