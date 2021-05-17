package com.example.service2;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.example.service1.ScheduleGenerator;
import com.example.service1.Ship;
import service3.Statistics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonSerialization {
    public static final String NAME_OF_SCHEDULE_JSON_FILE = "D:\\kurs.java\\service3\\schedule.json";
    public static final String NAME_OF_STATISTICS_JSON_FILE = "D:\\kurs.java\\service3\\statistics.json";

    public static int numberOfShips = 0;

    public static void serializationOfSchedule(ScheduleGenerator schedule){
        int numberOfAddedShips = 0;
        try {
            Files.deleteIfExists(Path.of(JsonSerialization.NAME_OF_SCHEDULE_JSON_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            for (Ship ship: schedule.getSchedule()) {
                writer.writeValue(new FileOutputStream(NAME_OF_SCHEDULE_JSON_FILE, true), ship);
            }

            //numberOfAddedShips = RecordAddingInFile.addRecord();

        }catch (IOException e) {
            e.printStackTrace();
        }

        numberOfShips = schedule.getSchedule().size() + numberOfAddedShips;
    }

    public static ScheduleGenerator deserializationOfSchedule(String fileName) {
        ScheduleGenerator newSchedule = new ScheduleGenerator();
        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectReader reader = mapper.readerFor(Ship.class);

            MappingIterator<Ship> shipMappingIterator = reader.readValues(new File(fileName));
            for (int i = 0; i < numberOfShips; i++){
                newSchedule.addShip(shipMappingIterator.nextValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newSchedule;
    }

    public static void serializationOfStatistics(Statistics statistics){
        try {
            Files.deleteIfExists(Path.of(JsonSerialization.NAME_OF_STATISTICS_JSON_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new FileOutputStream(NAME_OF_STATISTICS_JSON_FILE, true), statistics);

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}


