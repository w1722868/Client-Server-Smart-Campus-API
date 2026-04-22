/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.resources;

import java.util.ArrayList;
import java.util.List;

public class Database {

    public static List<Room> rooms = new ArrayList<>();
    public static List<Sensor> sensors = new ArrayList<>();
    public static List<SensorReading> readings = new ArrayList<>();

    static {
        //Room
        List<String> sensors1 = new ArrayList<>();
        sensors1.add("S1");

        List<String> sensors2 = new ArrayList<>();
        sensors2.add("S4");
        sensors2.add("S5");

        List<String> sensors3 = new ArrayList<>();
        sensors3.add("S3");

        List<String> sensors4 = new ArrayList<>();
        sensors4.add("S6");
        
        List<String> sensors5 = new ArrayList<>();
        sensors5.add("S2");

        //Rooms
        rooms.add(new Room("R1", "Lecture Hall", 100, sensors1));
        rooms.add(new Room("R2", "Business Class", 90, sensors2));
        rooms.add(new Room("R3", "Computer Lab", 50, sensors3));
        rooms.add(new Room("R4", "Library", 80, sensors4));
        rooms.add(new Room("R5", "Large Theater", 500, sensors5));

        //Sensors
        sensors.add(new Sensor("S1", "Temperature", "ACTIVE", 22.5, "R1"));
        sensors.add(new Sensor("S2", "CO2", "OFFLINE", 23.9, "R5"));
        sensors.add(new Sensor("S3", "Occupancy", "MAINTENANCE", 12, "R3"));
        sensors.add(new Sensor("S4", "CO2", "ACTIVE", 15.5, "R2"));
        sensors.add(new Sensor("S5", "Temperature", "OFFLINE", 30.5, "R2"));
        sensors.add(new Sensor("S6", "Occupancy", "MAINTENANCE", 45, "R4"));
  
        //Sensor Reading for Sensor 1
        readings.add(new SensorReading("S1-1", 1700000, 19));
        readings.add(new SensorReading("S1-2", 250000, 30.8));
        readings.add(new SensorReading("S1-3", 300000, 22.5));

        //Sensor Reading for Sensor 2
        readings.add(new SensorReading("S2-1", 800000, 14));
        readings.add(new SensorReading("S2-2", 6000000, 23.9));

        //Sensor Reading for Sensor 3
        readings.add(new SensorReading("S3-1", 1500000, 12));
        
        //Sensor Reading for Sensor 4
        readings.add(new SensorReading("S4-1", 3400000, 15.5));

    }
}
