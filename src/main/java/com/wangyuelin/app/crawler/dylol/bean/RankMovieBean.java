package com.wangyuelin.app.crawler.dylol.bean;

import javax.persistence.Column;

public class RankMovieBean {
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;
    @Column(name = "location_str")
    private String locationStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationStr() {
        return locationStr;
    }

    public void setLocationStr(String locationStr) {
        this.locationStr = locationStr;
    }
}
