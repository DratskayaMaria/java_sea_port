package service3;

import com.example.service1.ScheduleGenerator;
import com.example.service1.Ship;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ScheduleProcessing {
    public static final int MAX_UNLOADING_END_DELAY = 1440;
    public static final int MIN_UNLOADING_END_DELAY = 0;
    public static final int MAX_DEVIATION_FROM_SCHEDULE = 7;
    public static final int MIN_DEVIATION_FROM_SCHEDULE = -7;

    public static List<ShipInPort> shipsWithLooseCargo = new LinkedList<>();
    public static List<ShipInPort> shipsWithLiquidCargo = new LinkedList<>();
    public static List<ShipInPort> shipsWithContainerCargo = new LinkedList<>();
    public static List<ShipInPort> allShips = new LinkedList<>();


    public static void formInformationAboutTypeCargoQueues(ScheduleGenerator schedule) {
        setDelayInSchedule(schedule);
        for (Ship ship: schedule.getSchedule()) {
            ShipInPort shipInPort = new ShipInPort(ship);
            allShips.add(shipInPort);

            if (ship.getNameOfCargoType().equals(Ship.LOOSE)) {
                shipsWithLooseCargo.add(shipInPort);
            }else if (ship.getNameOfCargoType().equals(Ship.LIQUID)) {
                shipsWithLiquidCargo.add(shipInPort);
            }else{
                shipsWithContainerCargo.add(shipInPort);
            }
        }
    }

    public static void setDelayInSchedule(ScheduleGenerator schedule){
        int diffUnloadingEndDelay = MAX_UNLOADING_END_DELAY - MIN_UNLOADING_END_DELAY;
        int diffDeviationFromSchedule = MAX_DEVIATION_FROM_SCHEDULE - MIN_DEVIATION_FROM_SCHEDULE;

        for (Ship ship: schedule.getSchedule()) {
            Random random = new Random();
            int unloadingEndDelay = random.nextInt(diffUnloadingEndDelay + 1) + MIN_UNLOADING_END_DELAY;
            int deviationFromSchedule = random.nextInt(diffDeviationFromSchedule + 1) + MIN_DEVIATION_FROM_SCHEDULE;

            ship.setUnloadingEndDelay(unloadingEndDelay);

            if (ship.getArrivalDay() + deviationFromSchedule < 0){
                ship.setArrivalDay(0);
                ship.setDeviationFromSchedule(0);
            }else {
                ship.setDeviationFromSchedule(deviationFromSchedule);
            }
        }

        schedule.getSchedule().sort(Ship::compareTo);
    }
}
