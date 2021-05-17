package com.example.service1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service1Controller {

    @GetMapping(value = "/service1/generateSchedule")
    public static ScheduleGenerator generateSchedule() {
        ScheduleGenerator schedule = new ScheduleGenerator();
        schedule.generateSchedule();
        return schedule;
    }
}

