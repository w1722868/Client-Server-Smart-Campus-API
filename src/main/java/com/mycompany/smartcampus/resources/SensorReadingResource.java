/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.mycompany.smartcampus.resources;

import com.mycompany.smartcampus.exception.SensorUnavailableException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class SensorReadingResource {
    private String sensorId;
    private static List<SensorReading> readings = Database.readings;
    
    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }
    
    //GET all readings for that specific sensor
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorReading> getReadings() {
        List<SensorReading> reading = new ArrayList<>();
        for (SensorReading sr : Database.readings) {
            if (sr.getId().startsWith(sensorId + "-")) {
                reading.add(sr);
            }
        }
        return reading;
    }
    
    //POST a new reading
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SensorReading addReading(SensorReading reading) {

        Sensor sensor = null;
        for (Sensor s : Database.sensors) {
            if (s.getId().equals(sensorId)) {
                sensor = s;
                break;
            }
        }

        if (sensor == null) {
            throw new NotFoundException("Sensor with ID " + sensorId + " not found.");
        }
        
        for (SensorReading sr : Database.readings) {
            if (sr.getId().equals(reading.getId())) {
                throw new NotFoundException("Reading ID " + reading.getId() + " is already taken.");
            }
        }

        // MAINTENANCE 
        if (sensor.getStatus().equalsIgnoreCase("MAINTENANCE")) {
            throw new SensorUnavailableException("Sensor " + sensorId + " is currently unavailable for readings.");
        }

        readings.add(reading);
    
        //Update the sensor’s currentValue
        sensor.setCurrentValue(reading.getValue());

        return reading;
    }
    
}


