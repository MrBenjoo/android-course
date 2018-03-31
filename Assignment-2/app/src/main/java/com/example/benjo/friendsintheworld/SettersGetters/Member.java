package com.example.benjo.friendsintheworld.SettersGetters;


public class Member {
    private String name;
    private String id;
    private String group;
    private String longitude;
    private String latitude;

    public Member(String name, String group, String id, String longitude, String latitude) {
        this.name = name;
        this.group = group;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }
}
