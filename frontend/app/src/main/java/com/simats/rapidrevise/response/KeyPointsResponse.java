package com.simats.rapidrevise.response;

import com.simats.rapidrevise.model.KeyPoint;

import java.util.List;

public class KeyPointsResponse {

    private String status;
    private List<KeyPoint> points;

    public String getStatus() { return status; }
    public List<KeyPoint> getPoints() { return points; }
}
