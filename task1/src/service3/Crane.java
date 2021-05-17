package service3;

import service1.Ship;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class Crane implements Callable<LinkedBlockingQueue<ShipInPort>>{
    public static final double LOOSE_CRANE_PRODUCTIVITY = 3;
    public static final double LIQUID_CRANE_PRODUCTIVITY = 3;
    public static final double CONTAINER_CRANE_PRODUCTIVITY = 3;
    public static final int CRANE_PRICE = 30000;

    public static final long COEF = 10;

    private Future timer;

    private ShipInPort shipInPort;
    private LinkedBlockingQueue<ShipInPort> currentShips;
    private LinkedBlockingQueue<ShipInPort> unloadingShips;
    private LinkedBlockingQueue<ShipInPort> unloadedShips;
    private String cargoType;
    private double performance;

    public Crane(Future timer, ShipInPort shipInPort, LinkedBlockingQueue<ShipInPort> currentShips,
                 LinkedBlockingQueue<ShipInPort> unloadingShips, LinkedBlockingQueue<ShipInPort> unloadedShips,
                 String cargoType) {
        this.timer = timer;
        this.shipInPort = shipInPort;
        this.currentShips = currentShips;
        this.unloadingShips = unloadingShips;
        this.unloadedShips = unloadedShips;
        this.cargoType = cargoType;

        if (cargoType.equals(Ship.LOOSE)) {
            this.performance = LOOSE_CRANE_PRODUCTIVITY;
        }else if (cargoType.equals(Ship.LIQUID)) {
            this.performance = LIQUID_CRANE_PRODUCTIVITY;
        }else{
            this.performance = CONTAINER_CRANE_PRODUCTIVITY;
        }
    }

    @Override
    public LinkedBlockingQueue<ShipInPort> call() {

        while (true) {

            if (!currentShips.isEmpty()) {
                try {
                    Simulation.sumOfLengthsOfUnloadingQueue += currentShips.size();
                    Simulation.numberOfLengthsOfUnloadingQueue++;
                    shipInPort = currentShips.take();
                    shipInPort.startForUnloadingWithOneCrane = Simulation.getTime();
                    unloadingShips.put(shipInPort);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else if (!unloadingShips.isEmpty()) {
                try {
                    shipInPort = unloadingShips.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                shipInPort.startForUnloadingWithTwoCranes = Simulation.getTime();
                shipInPort.weightOfUnloadedCargo = (int) ((shipInPort.startForUnloadingWithTwoCranes -
                        shipInPort.startForUnloadingWithOneCrane) * performance);

                if ((shipInPort.ship.getWeightOfCargoType() <= shipInPort.weightOfUnloadedCargo)) {
                    timeRecordingWithOneCrane(shipInPort);
                    try {
                        unloadedShips.put(shipInPort);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                int unloadingTimeWithTwoCranes = (int) Math.ceil((double)(shipInPort.ship.getWeightOfCargoType() -
                        shipInPort.weightOfUnloadedCargo) / (performance * 2));
                int unloadingTimeWithOneCrane = (int) Math.ceil(shipInPort.weightOfUnloadedCargo / performance);
                try {
                    sleep((unloadingTimeWithTwoCranes + unloadingTimeWithOneCrane) / COEF);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    timeRecordingWithTwoCranes(shipInPort);
                    unloadedShips.put(shipInPort);
                    if (unloadedShips.size() == Simulation.allShipsAmount) {
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (unloadedShips.size() == Simulation.allShipsAmount) {
                break;
            }

        }
        return unloadedShips;

    }

    private void timeRecordingWithOneCrane(ShipInPort shipInPort){
        shipInPort.timeOfArrivalInThePort = (shipInPort.ship.getArrivalDay())  * 24 * 60 +
                shipInPort.ship.getArrivalTime() + shipInPort.ship.getDeviationFromSchedule() * 24 * 60;
        shipInPort.waitingTimeInTheQueue = (shipInPort.startForUnloadingWithOneCrane -
                shipInPort.startTime);
        shipInPort.unloadStartTime = shipInPort.timeOfArrivalInThePort + shipInPort.waitingTimeInTheQueue;
        shipInPort.unloadingDuration = shipInPort.ship.getPortBerthTime()
                + shipInPort.ship.getUnloadingEndDelay();
    }

    private void timeRecordingWithTwoCranes(ShipInPort shipInPort){
        shipInPort.timeOfArrivalInThePort = (shipInPort.ship.getArrivalDay())  * 24 * 60 +
                shipInPort.ship.getArrivalTime() + shipInPort.ship.getDeviationFromSchedule() * 24 * 60;
        shipInPort.waitingTimeInTheQueue = (shipInPort.startForUnloadingWithOneCrane -
                shipInPort.startTime);
        shipInPort.unloadStartTime = shipInPort.timeOfArrivalInThePort + shipInPort.waitingTimeInTheQueue;
        shipInPort.unloadingDuration = (long) ((shipInPort.ship.getWeightOfCargoType() -
                        shipInPort.weightOfUnloadedCargo) / (performance * 2) +
                        shipInPort.weightOfUnloadedCargo / performance) +
                        shipInPort.ship.getUnloadingEndDelay();
    }
}

