package com.ganesh.metro;

public class MetroServiceHelper {
    public static int calculateFare(Station source, Station destination) {
        //System.out.println("--"+ source.getStationId() + "-->" + destination.getStationId());
        if(source.getStationId() > destination.getStationId()){
            System.out.println("--"+ source.getStationId() + ">" + destination.getStationId());
            return (source.getStationId() - destination.getStationId()) * 5;
        }
        else {
            System.out.println("--"+ source.getStationId() + "<" + destination.getStationId());
            return (destination.getStationId() - source.getStationId()) * 5;
        }
    }

}
