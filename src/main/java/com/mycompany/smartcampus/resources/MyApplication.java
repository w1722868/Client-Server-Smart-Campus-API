/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.resources;


import com.mycompany.smartcampus.exception.GenericExceptionMapper;
import com.mycompany.smartcampus.exception.LinkedResourceNotFoundExceptionMapper;
import com.mycompany.smartcampus.exception.RoomNotEmptyExceptionMapper;
import com.mycompany.smartcampus.exception.SensorUnavailableExceptionMapper;
import com.mycompany.smartcampus.filter.LoggingFilter;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api/v1")
public class MyApplication extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(Discovery.class);
        classes.add(SensorRoom.class);
        classes.add(SensorResource.class);
        classes.add(LoggingFilter.class);
        classes.add(RoomNotEmptyExceptionMapper.class);
        classes.add(GenericExceptionMapper.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        return classes;
    }
}
