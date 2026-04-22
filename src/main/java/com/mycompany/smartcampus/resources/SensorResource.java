/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.resources;

import com.mycompany.smartcampus.exception.LinkedResourceNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/sensors")
public class SensorResource {

    private static List<Room> rooms = Database.rooms;
    private static List<Sensor> sensors = Database.sensors;

    
    //ADD sesnsor if RoomId exist
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Sensor addSensor(Sensor sensor) {

        boolean RoomExists = false;

        for (Room r : rooms) {
            if (r.getId().equals(sensor.getRoomId())) {
                r.getSensorIds().add(sensor.getId());
                RoomExists = true;
                break;
            }
        }
        if (!RoomExists) {
            throw new LinkedResourceNotFoundException("Room with ID " + sensor.getRoomId() + " does not exist.");
        }
        sensors.add(sensor);
        return sensor;
    }
    
    //List all sensors, and allow filtering
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getSensors(@QueryParam("type") String type) {

        // If no type is provided, return all sensors
        if (type == null || type.isEmpty()) {
            return sensors;
        }
        
        // Return sensors for particular sensor type
        List<Sensor> filteredSensors = new ArrayList<>();

        for (Sensor s : sensors) {
            if (s.getType().equalsIgnoreCase(type)) {
                filteredSensors.add(s);
            }
        }

        return filteredSensors;
    }

    //sub‑resource locator
    @Path("/{sensorId}/readings")
    public SensorReadingResource getReading(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }

}
