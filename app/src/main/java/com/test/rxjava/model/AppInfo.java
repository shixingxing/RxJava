package com.test.rxjava.model;

import lombok.Data;

@Data
public class AppInfo implements Comparable<Object> {
    private long lastUpdateTime;
    private String name;
    private String icon;

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
