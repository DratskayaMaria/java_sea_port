package service2;

import service1.ScheduleGenerator;
import service1.Ship;
import service3.ScheduleProcessing;
import service3.Simulation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            Files.delete(Path.of(JsonSerialization.NAME_OF_JSON_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ScheduleGenerator schedule = new ScheduleGenerator();
        schedule.generateSchedule();
        int newNumberOfShips = JsonSerialization.serializationOfSchedule(schedule);
        ScheduleGenerator newSchedule = JsonSerialization.deserializationOfSchedule(newNumberOfShips);
        newSchedule.getSchedule().sort(Ship::compareTo);
        System.out.println("---GENERATED SCHEDULE---");
        newSchedule.printInfoAboutSchedule();

        ScheduleProcessing.formInformationAboutTypeCargoQueues(newSchedule);
        Simulation.startMinimization();
    }

}
