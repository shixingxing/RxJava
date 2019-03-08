package com.test.rxjava.model;


public class AppInfo implements Comparable<Object> {
    private long lastUpdateTime;
    private String name;
    private String icon;

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public AppInfo() {
    }

    public AppInfo(long lastUpdateTime, String name, String icon) {
        this.lastUpdateTime = lastUpdateTime;
        this.name = name;
        this.icon = icon;
    }

    @Override
    public int compareTo(Object another) {
        AppInfo f = (AppInfo) another;
        return getName().compareTo(f.getName());
    }
}
