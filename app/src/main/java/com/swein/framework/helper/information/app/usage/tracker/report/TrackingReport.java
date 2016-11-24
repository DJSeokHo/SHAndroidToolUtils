package com.swein.framework.helper.information.app.usage.tracker.report;

/**
 * Created by seokho on 24/11/2016.
 */

public class TrackingReport {

    private String screen;
    private String category;
    private String action;
    private String label;
    private long value;

    private String description;
    private boolean isFatal;

    private TrackingReport() {
        screen = "";
        category = "";
        action = "";
        label = "";
        value = 0;
        description = "";
        isFatal = false;
    }

    private static TrackingReport instance = new TrackingReport();
    public static TrackingReport getInstance() {
        return instance;
    }

    public void setTrackingReport(String screen) {
        this.screen = screen;
    }

    public void setTrackingReport(String description, boolean isFatal) {
        this.description = description;
        this.isFatal = isFatal;
    }

    public void setTrackingReport(String screen, String category, String action, String label, long value) {
        this.screen = screen;
        this.category = category;
        this.action = action;
        this.label = label;
        this.value = value;
    }

//    public void setScreen(String screen) {
//        this.screen = screen;
//    }
    public String getScreen() {
        return this.screen;
    }

//    public void setCategory(String category) {
//        this.category = category;
//    }
    public String getCategory() {
        return this.category;
    }

//    public void setAction(String action) {
//        this.action = action;
//    }
    public String getAction() {
        return this.action;
    }

//    public void setLabel(String label) {
//        this.label = label;
//    }
    public String getLabel() {
        return this.label;
    }

//    public void setValue(long value) {
//        this.value = value;
//    }
    public long getValue() {
        return this.value;
    }

//    public void setDescription(String description) {
//        this.description = description;
//    }
    public String getDescription() {
        return this.description;
    }

//    public void setIsFatal(boolean isFatal) {
//        this.isFatal = isFatal;
//    }
    public boolean getIsFatal() {
        return this.isFatal;
    }

    public void clear() {
        this.screen = "";
        this.category = "";
        this.action = "";
        this.label = "";
        this.value = 0;

        this.description = "";
        this.isFatal = false;
    }

}
