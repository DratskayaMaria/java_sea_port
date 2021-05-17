package com.example.service2;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.example.service1.ScheduleGenerator;
import service3.Statistics;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class Service2Controller {
    public static final String GET_GENERATED_SCHEDULE = "http://localhost:8081/service1/generateSchedule";

    @GetMapping(value = "/service2/getJsonSerialization")
    public static File getJsonSerialization(){
        RestTemplate restTemplate = new RestTemplate();
        ScheduleGenerator schedule =
                restTemplate.getForObject(GET_GENERATED_SCHEDULE, ScheduleGenerator.class);
        JsonSerialization.serializationOfSchedule(schedule);
        return new File(JsonSerialization.NAME_OF_SCHEDULE_JSON_FILE);
    }

    @GetMapping(value = "/service2/getScheduleDeserialization")
    public static ScheduleGenerator getScheduleDeserialization(@RequestParam(value = "filename",
            defaultValue = JsonSerialization.NAME_OF_SCHEDULE_JSON_FILE) String fileName) throws ServiceException {
        if (!Files.exists(Path.of(fileName))){
            throw new ServiceException("Incorrect name of json file: " + fileName);
        }
        ScheduleGenerator scheduleGenerator = JsonSerialization.deserializationOfSchedule(fileName);
        return scheduleGenerator;
    }

    @PostMapping(value = "/service2/saveStatistics")
    public static void saveStatistics(@RequestBody Statistics ourStatistics){
        JsonSerialization.serializationOfStatistics(ourStatistics);
    }
}
