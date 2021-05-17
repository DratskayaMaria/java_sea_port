package service3;

import org.springframework.web.client.RestTemplate;
import com.example.service1.ScheduleGenerator;
import com.example.service1.Ship;

import java.io.File;
import java.util.Scanner;

public class MainService3 {
    public static final String GET_JSON_SERIALIZATION = "http://localhost:8082/service2/getJsonSerialization";
    public static final String GET_SCHEDULE_DESERIALIZATION = "http://localhost:8082/service2/getScheduleDeserialization?filename=";
    public static final String POST_STATISTICS = "http://localhost:8082/service2/saveStatistics";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        File file = restTemplate.getForObject(GET_JSON_SERIALIZATION, File.class);
        System.out.println("Generation of the schedule and writing to the file " +
                        file.getName() + " is over.");
        System.out.println("Please enter name of file with schedule: ");
        Scanner in = new Scanner(System.in);
        String fileName = in.nextLine();

        ScheduleGenerator newSchedule = restTemplate.getForObject(GET_SCHEDULE_DESERIALIZATION +
                    fileName, ScheduleGenerator.class);

        newSchedule.getSchedule().sort(Ship::compareTo);
        ScheduleProcessing.formInformationAboutTypeCargoQueues(newSchedule);
        Statistics ourStatistics = Simulation.startMinimization();

        restTemplate.postForObject(POST_STATISTICS, ourStatistics, Void.class);
        System.out.println("Simulation is over. Check the statistics.json file.");
    }
}
