package com.swein.framework.module.devicestoragescanner.constants;

public class DSSConstants {

    public static final long SH_GB = 1073741824;
    public static final long SH_MB = 1048576;
    public static final int SH_KB = 1024;

    public final static String SUB_SYSTEM_KEY = "mSubSystem";
    public final static String SD_CARD_KEY = "sd";
    public final static String USB_KEY = "usb";

    public final static String STORAGE_STATE_MOUNTED = "mounted";
    public final static String STORAGE_STATE_UN_MOUNTED = "unmounted";

    public enum STORAGE_TYPE {
        LOCAL, SD_CARD, USB
    }

}
