/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.resources;

import com.mycompany.smartcampus.exception.RoomNotEmptyException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/rooms")
public class SensorRoom {

    private static List<Room> rooms = Database.rooms;
    private static List<Sensor> sensors = Database.sensors;
    

    
    //GET all rooms
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getAllRooms() {
        return rooms;
        
    }
    
    
    //ADD room
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Room addRoom(Room room) {
        for (Room r : Database.rooms) {
            if (r.getId().equals(room.getId())) {
                throw new NotFoundException("Room " + room.getId() + " is already taken.");
            }
        }
        
        rooms.add(room);
        return (room);
    }

    //GET room by id
    @GET
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room getRoomById(@PathParam("roomId") String roomId) {
        for (Room r : rooms) {
            if (r.getId().equals(roomId)) {
                return r;
            }
        }
        throw new NotFoundException("Room with ID " + roomId + " is not found.");
    }

    //DELETE room by id
    @DELETE
    @Path("/{roomId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room deleteRoomById(@PathParam("roomId") String roomId) {
        Room roomToDelete = null;

        for (Room r : rooms) {
            if (r.getId().equals(roomId)) {
                roomToDelete = r;
                break;
            }
        }
        // Room not found
        if (roomToDelete == null) {
            throw new NotFoundException("Room with ID " + roomId + " is not found.");
        }
        //Room has sensors
        for (Sensor s : sensors) {
            if (s.getRoomId().equals(roomId)
                    && s.getStatus().equalsIgnoreCase("ACTIVE")) {

                throw new RoomNotEmptyException("Room " + roomId + " cannot be deleted because it has ACTIVE sensors.");
            }
        }
        rooms.remove(roomToDelete);
        return roomToDelete;
    }

}
