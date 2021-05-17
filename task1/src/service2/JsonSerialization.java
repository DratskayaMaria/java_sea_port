package service2;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import service1.ScheduleGenerator;
import service1.Ship;

import java.io.*;

public class JsonSerialization {
    public static final String NAME_OF_JSON_FILE = "schedule.json";

    public static int serializationOfSchedule(ScheduleGenerator schedule){

        int numberOfShips = schedule.getSchedule().size();
        int numberOfAddedShips = 0;

        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            for (Ship ship: schedule.getSchedule()) {
                writer.writeValue(new FileOutputStream(NAME_OF_JSON_FILE, true), ship);
            }

            numberOfAddedShips = RecordAddingInFile.addRecord();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return numberOfShips + numberOfAddedShips;
    }

    public static ScheduleGenerator deserializationOfSchedule(int numberOfShips) {
        ScheduleGenerator newSchedule = new ScheduleGenerator();
        try{
            ObjectMapper mapper = new ObjectMapper();
            ObjectReader reader = mapper.readerFor(Ship.class);

            MappingIterator<Ship> shipMappingIterator = reader.readValues(new File(NAME_OF_JSON_FILE));
            for (int i = 0; i < numberOfShips; i++){
                newSchedule.addShip(shipMappingIterator.nextValue());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return newSchedule;
    }
}


