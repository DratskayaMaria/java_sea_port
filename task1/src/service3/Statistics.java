package service3;

import service1.Ship;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Statistics {
    public static final int FINE = 100;

    private LinkedBlockingQueue<ShipInPort> unloadedShips;
    private int sumOfLengthsOfUnloadingQueue;
    private int numberOfLengthsOfUnloadingQueue;

    private int numberOfUnloadedShips;
    private int averageLengthQueueForUnloading;
    private int averageWaitingTimeInTheQueue;
    private int maxUnloadingDelay;
    private int averageUnloadingDelay;
    private int amountOfFineLoose;
    private int amountOfFineLiqiud;
    private int amountOfFineContainer;
    private int requiredNumberOfLooseCranes;
    private int requiredNumberOfLiquidCranes;
    private int requiredNumberOfContainerCranes;

    public Statistics(LinkedBlockingQueue<ShipInPort> unloadedShips, int sumOfLengthsOfUnloadingQueue,
                      int numberOfLengthsOfUnloadingQueue, int requiredNumberOfLooseCranes,
                      int requiredNumberOfLiquidCranes, int requiredNumberOfContainerCranes) {
        this.unloadedShips = unloadedShips;
        this.sumOfLengthsOfUnloadingQueue = sumOfLengthsOfUnloadingQueue;
        this.numberOfLengthsOfUnloadingQueue = numberOfLengthsOfUnloadingQueue;

        this.numberOfUnloadedShips = unloadedShips.size();
        this.averageLengthQueueForUnloading = sumOfLengthsOfUnloadingQueue /
                numberOfLengthsOfUnloadingQueue;
        this.averageWaitingTimeInTheQueue = findAverageWaitingTimeInTheQueue();
        this.maxUnloadingDelay = findMaxUnloadingDelay();
        this.averageUnloadingDelay = findAverageUnloadingDelay();

        this.amountOfFineLoose = findAmountOfFine(Ship.LOOSE);
        this.amountOfFineLiqiud = findAmountOfFine(Ship.LIQUID);
        this.amountOfFineContainer = findAmountOfFine(Ship.CONTAINER);
        this.requiredNumberOfLooseCranes = requiredNumberOfLooseCranes;
        this.requiredNumberOfLiquidCranes = requiredNumberOfLiquidCranes;
        this.requiredNumberOfContainerCranes = requiredNumberOfContainerCranes;
    }

    private int findAverageWaitingTimeInTheQueue(){
        int sumOfTheWaitingTime = 0;
        for (ShipInPort shipInPort: unloadedShips) {
            sumOfTheWaitingTime += shipInPort.waitingTimeInTheQueue;
        }
        return sumOfTheWaitingTime / unloadedShips.size();
    }

    private int findMaxUnloadingDelay(){
        int ourMaxUnloadingDelay = ScheduleProcessing.MIN_UNLOADING_END_DELAY;
        for (ShipInPort shipInPort: unloadedShips) {
            if (shipInPort.ship.getUnloadingEndDelay() > ourMaxUnloadingDelay){
                ourMaxUnloadingDelay = shipInPort.ship.getUnloadingEndDelay();
            }
        }
        return ourMaxUnloadingDelay;
    }

    private int findAverageUnloadingDelay(){
        int sumOfUnloadingDelay = 0;
        for (ShipInPort shipInPort: unloadedShips) {
            sumOfUnloadingDelay += shipInPort.ship.getUnloadingEndDelay();
        }
        return sumOfUnloadingDelay / unloadedShips.size();
    }

    private int findAmountOfFine(String typeOfCargo){
        int sumOfAmountOfFineLoose = 0;
        for (ShipInPort shipInPort: unloadedShips) {
            if (shipInPort.ship.getNameOfCargoType().equals(typeOfCargo)) {
                sumOfAmountOfFineLoose += Math.ceil(shipInPort.waitingTimeInTheQueue / 60) * FINE;
            }
        }
        return sumOfAmountOfFineLoose;
    }

    public Queue<ShipInPort> getUnloadedShips() {
        return unloadedShips;
    }

    public void setUnloadedShips(LinkedBlockingQueue<ShipInPort> unloadedShips) {
        this.unloadedShips = unloadedShips;
    }

    public int getSumOfLengthsOfUnloadingQueue() {
        return sumOfLengthsOfUnloadingQueue;
    }

    public void setSumOfLengthsOfUnloadingQueue(int sumOfLengthsOfUnloadingQueue) {
        this.sumOfLengthsOfUnloadingQueue = sumOfLengthsOfUnloadingQueue;
    }

    public int getNumberOfLengthsOfUnloadingQueue() {
        return numberOfLengthsOfUnloadingQueue;
    }

    public void setNumberOfLengthsOfUnloadingQueue(int numberOfLengthsOfUnloadingQueue) {
        this.numberOfLengthsOfUnloadingQueue = numberOfLengthsOfUnloadingQueue;
    }

    public int getNumberOfUnloadedShips() {
        return numberOfUnloadedShips;
    }

    public void setNumberOfUnloadedShips(int numberOfUnloadedShips) {
        this.numberOfUnloadedShips = numberOfUnloadedShips;
    }

    public int getAverageLengthQueueForUnloading() {
        return averageLengthQueueForUnloading;
    }

    public void setAverageLengthQueueForUnloading(int averageLengthQueueForUnloading) {
        this.averageLengthQueueForUnloading = averageLengthQueueForUnloading;
    }

    public int getAverageWaitingTimeInTheQueue() {
        return averageWaitingTimeInTheQueue;
    }

    public void setAverageWaitingTimeInTheQueue(int averageWaitingTimeInTheQueue) {
        this.averageWaitingTimeInTheQueue = averageWaitingTimeInTheQueue;
    }

    public int getMaxUnloadingDelay() {
        return maxUnloadingDelay;
    }

    public void setMaxUnloadingDelay(int maxUnloadingDelay) {
        this.maxUnloadingDelay = maxUnloadingDelay;
    }

    public int getAverageUnloadingDelay() {
        return averageUnloadingDelay;
    }

    public void setAverageUnloadingDelay(int averageUnloadingDelay) {
        this.averageUnloadingDelay = averageUnloadingDelay;
    }

    public int getAmountOfFineLoose() {
        return amountOfFineLoose;
    }

    public void setAmountOfFineLoose(int amountOfFineLoose) {
        this.amountOfFineLoose = amountOfFineLoose;
    }

    public int getAmountOfFineLiqiud() {
        return amountOfFineLiqiud;
    }

    public void setAmountOfFineLiqiud(int amountOfFineLiqiud) {
        this.amountOfFineLiqiud = amountOfFineLiqiud;
    }

    public int getAmountOfFineContainer() {
        return amountOfFineContainer;
    }

    public void setAmountOfFineContainer(int amountOfFineContainer) {
        this.amountOfFineContainer = amountOfFineContainer;
    }

    public int getRequiredNumberOfLooseCranes() {
        return requiredNumberOfLooseCranes;
    }

    public void setRequiredNumberOfLooseCranes(int requiredNumberOfLooseCranes) {
        this.requiredNumberOfLooseCranes = requiredNumberOfLooseCranes;
    }

    public int getRequiredNumberOfLiquidCranes() {
        return requiredNumberOfLiquidCranes;
    }

    public void setRequiredNumberOfLiquidCranes(int requiredNumberOfLiquidCranes) {
        this.requiredNumberOfLiquidCranes = requiredNumberOfLiquidCranes;
    }

    public int getRequiredNumberOfContainerCranes() {
        return requiredNumberOfContainerCranes;
    }

    public void setRequiredNumberOfContainerCranes(int requiredNumberOfContainerCranes) {
        this.requiredNumberOfContainerCranes = requiredNumberOfContainerCranes;
    }

    @Override
    public String toString() {
        long averageWaitingTimeInTheQueueDays = averageWaitingTimeInTheQueue / (24 * 60);
        long averageWaitingTimeInTheQueueHours = (averageWaitingTimeInTheQueue % (24 * 60)) / 60;
        long averageWaitingTimeInTheQueueMinutes = (averageWaitingTimeInTheQueue % (24 * 60)) % 60;

        long maxUnloadingDelayDays = maxUnloadingDelay / (24 * 60);
        long maxUnloadingDelayHours = (maxUnloadingDelay % (24 * 60)) / 60;
        long maxUnloadingDelayMinutes = (maxUnloadingDelay % (24 * 60)) % 60;

        long averageUnloadingDelayDays = averageUnloadingDelay / (24 * 60);
        long averageUnloadingDelayHours = (averageUnloadingDelay % (24 * 60)) / 60;
        long averageUnloadingDelayMinutes = (averageUnloadingDelay % (24 * 60)) % 60;

        return "Statistics{" +
                "\nnumberOfUnloadedShips=" + numberOfUnloadedShips +
                "\naverageLengthQueueForUnloading=" + averageLengthQueueForUnloading +
                "\naverageWaitingTimeInTheQueue=" + averageWaitingTimeInTheQueueDays +
                ":" + averageWaitingTimeInTheQueueHours + ":" + averageWaitingTimeInTheQueueMinutes +
                "\nmaxUnloadingDelay=" + maxUnloadingDelayDays +
                ":" + maxUnloadingDelayHours + ":" + maxUnloadingDelayMinutes +
                "\naverageUnloadingDelay=" + averageUnloadingDelayDays +
                ":" + averageUnloadingDelayHours + ":" + averageUnloadingDelayMinutes +
                "\namountOfFineLoose=" + amountOfFineLoose +
                "\namountOfFineLiqiud=" + amountOfFineLiqiud +
                "\namountOfFineContainer=" + amountOfFineContainer +
                "\nrequiredNumberOfLooseCranes=" + requiredNumberOfLooseCranes +
                "\nrequiredNumberOfLiquidCranes=" + requiredNumberOfLiquidCranes +
                "\nrequiredNumberOfContainerCranes=" + requiredNumberOfContainerCranes +
                '}';
    }
}
