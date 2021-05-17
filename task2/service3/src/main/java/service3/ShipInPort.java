package service3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.service1.Ship;

public class ShipInPort implements Comparable<ShipInPort>{
    public Ship ship;

    @JsonIgnore
    public long startForUnloadingWithOneCrane;
    @JsonIgnore
    public long startForUnloadingWithTwoCranes;
    @JsonIgnore
    public int weightOfUnloadedCargo;
    @JsonIgnore
    public long startTime;

    public long timeOfArrivalInThePort;
    public long waitingTimeInTheQueue;
    public long unloadStartTime;
    public long unloadingDuration;

    public ShipInPort(){

    }

    public ShipInPort(Ship ship){
        this.ship = ship;
        this.startForUnloadingWithOneCrane = 0;
        this.startForUnloadingWithTwoCranes = 0;
        this.weightOfUnloadedCargo = 0;
        this.startTime = 0;
        this.timeOfArrivalInThePort = 0;
        this.waitingTimeInTheQueue = 0;
        this.unloadStartTime = 0;
        this.unloadingDuration = 0;
    }

    @Override
    public String toString() {
        long timeOfArrivalInThePortDays = timeOfArrivalInThePort / (24 * 60);
        long timeOfArrivalInThePortHours = (timeOfArrivalInThePort % (24 * 60)) / 60;
        long timeOfArrivalInThePortMinutes = (timeOfArrivalInThePort % (24 * 60)) % 60;

        long waitingTimeInTheQueueDays = waitingTimeInTheQueue / (24 * 60);
        long waitingTimeInTheQueueHours = (waitingTimeInTheQueue % (24 * 60)) / 60;
        long waitingTimeInTheQueueMinutes = (waitingTimeInTheQueue % (24 * 60)) % 60;

        long unloadStartTimeDays = unloadStartTime / (24 * 60);
        long unloadStartTimeHours = (unloadStartTime % (24 * 60)) / 60;
        long unloadStartTimeMinutes = (unloadStartTime% (24 * 60)) % 60;

        long unloadingDurationDays = unloadingDuration / (24 * 60);
        long unloadingDurationHours = (unloadingDuration % (24 * 60)) / 60;
        long unloadingDurationMinutes = (unloadingDuration % (24 * 60)) % 60;

        return "ShipInPort{" +
                "ship=" + ship +
                ",\n timeOfArrivalInThePort=" + timeOfArrivalInThePortDays +
                ":" + timeOfArrivalInThePortHours + ":" + timeOfArrivalInThePortMinutes +
                ", waitingTimeInTheQueue=" + waitingTimeInTheQueueDays +
                ":" + waitingTimeInTheQueueHours + ":" + waitingTimeInTheQueueMinutes +
                ", unloadStartTime=" + unloadStartTimeDays +
                ":" + unloadStartTimeHours + ":" + unloadStartTimeMinutes +
                ", unloadingDuration=" + unloadingDurationDays +
                ":" + unloadingDurationHours + ":" + unloadingDurationMinutes +
                '}';
    }

    @Override
    public int compareTo(ShipInPort shipInPort) {
        Long timeOfArrivalInThePort1 = this.timeOfArrivalInThePort;
        Long timeOfArrivalInThePort2 = shipInPort.timeOfArrivalInThePort;
        return timeOfArrivalInThePort1.compareTo(timeOfArrivalInThePort2);
    }
}

