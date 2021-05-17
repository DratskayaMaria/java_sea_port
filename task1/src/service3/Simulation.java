package service3;

import service1.Ship;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

public class Simulation {
    public static int sumOfLengthsOfUnloadingQueue = 0;
    public static int numberOfLengthsOfUnloadingQueue = 0;

    public static LinkedBlockingQueue<ShipInPort> currentShipsWithLooseCargo = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<ShipInPort> currentShipsWithLiquidCargo = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<ShipInPort> currentShipsWithContainerCargo = new LinkedBlockingQueue<>();

    public static LinkedBlockingQueue<ShipInPort> unloadingShipsWithLooseCargo = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<ShipInPort> unloadingShipsWithLiquidCargo = new LinkedBlockingQueue<>();
    public static LinkedBlockingQueue<ShipInPort> unloadingShipsWithContainerCargo = new LinkedBlockingQueue<>();

    public static LinkedBlockingQueue<ShipInPort> unloadedShips = new LinkedBlockingQueue<>();

    static AtomicLong simulationStartTime = new AtomicLong();

    static int allShipsAmount;


    public static void startMinimization(){
        boolean isLooseMimimized = false;
        boolean isLiquidMimimized = false;
        boolean isContainerMimimized = false;

        int numberOfLooseCranes = 1;
        int numberOfLiquidCranes = 1;
        int numberOfContainerCranes = 1;

        while (true) {
            Statistics ourStatistics = startModeling(numberOfLooseCranes, numberOfLiquidCranes, numberOfContainerCranes);

            int plusNumberOfLooseCranes = ourStatistics.getAmountOfFineLoose() / Crane.CRANE_PRICE;
            int plusNumberOfLiquidCranes = ourStatistics.getAmountOfFineLiqiud() / Crane.CRANE_PRICE;
            int plusNumberOfContainerCranes = ourStatistics.getAmountOfFineContainer() / Crane.CRANE_PRICE;
            if (plusNumberOfLooseCranes == 0){
                isLooseMimimized = true;
                numberOfLooseCranes = ourStatistics.getRequiredNumberOfLooseCranes();
            }
            if (plusNumberOfLiquidCranes == 0){
                isLiquidMimimized = true;
                numberOfLiquidCranes = ourStatistics.getRequiredNumberOfLiquidCranes();
            }
            if (plusNumberOfContainerCranes == 0){
                isContainerMimimized = true;
                numberOfContainerCranes = ourStatistics.getRequiredNumberOfContainerCranes();
            }

            if (isLooseMimimized && isLiquidMimimized && isContainerMimimized){
                System.out.println("\n\n---THE MOST EFFICIENT STATISTICS---");
                System.out.println(ourStatistics.toString());
                System.out.println("\n\n---UNLOADED SHIPS---");
                for (ShipInPort shipInPort : ourStatistics.getUnloadedShips()) {
                    System.out.println(shipInPort.toString());
                }
                break;
            }

            numberOfLooseCranes += plusNumberOfLooseCranes;
            numberOfLiquidCranes += plusNumberOfLiquidCranes;
            numberOfContainerCranes += plusNumberOfContainerCranes;
        }

    }

    public static Statistics startModeling(int numberOfLooseCranes, int numberOfLiquidCranes,
                                       int numberOfContainerCranes) {
        allShipsAmount = ScheduleProcessing.allShips.size();

        sumOfLengthsOfUnloadingQueue = 0;
        numberOfLengthsOfUnloadingQueue = 0;

        currentShipsWithLooseCargo = new LinkedBlockingQueue<>();
        currentShipsWithLiquidCargo = new LinkedBlockingQueue<>();
        currentShipsWithContainerCargo = new LinkedBlockingQueue<>();

        unloadingShipsWithLooseCargo = new LinkedBlockingQueue<>();
        unloadingShipsWithLiquidCargo = new LinkedBlockingQueue<>();
        unloadingShipsWithContainerCargo = new LinkedBlockingQueue<>();

        unloadedShips = new LinkedBlockingQueue<>();
        ExecutorService timer = Executors.newSingleThreadExecutor();
        Future timerFuture = timer.submit(new TimeManager());

        simulationStartTime.set(System.currentTimeMillis());

        ExecutorService looseThreads = Executors.newFixedThreadPool(numberOfLooseCranes);
        ExecutorService liquidThreads = Executors.newFixedThreadPool(numberOfLiquidCranes);
        ExecutorService containerThreads = Executors.newFixedThreadPool(numberOfContainerCranes);

        Future<LinkedBlockingQueue<ShipInPort>> unloadedShipsFuture = null;
        for (int i = 0; i < numberOfLooseCranes; i++) {
            unloadedShipsFuture = looseThreads.submit(new Crane(timerFuture,
                    currentShipsWithLooseCargo.isEmpty() ? null : currentShipsWithLooseCargo.peek(),
                    currentShipsWithLooseCargo, unloadingShipsWithLooseCargo, unloadedShips,
                    Ship.LOOSE));
        }

        for (int i = 0; i < numberOfLiquidCranes; i++) {
            unloadedShipsFuture = liquidThreads.submit(new Crane(timerFuture,
                    currentShipsWithLiquidCargo.isEmpty() ? null : currentShipsWithLiquidCargo.peek(),
                    currentShipsWithLiquidCargo, unloadingShipsWithLiquidCargo, unloadedShips,
                    Ship.LIQUID));
        }

        for (int i = 0; i < numberOfContainerCranes; i++) {
            unloadedShipsFuture = containerThreads.submit(new Crane(timerFuture,
                    currentShipsWithContainerCargo.isEmpty() ? null : currentShipsWithContainerCargo.peek(),
                    currentShipsWithContainerCargo, unloadingShipsWithContainerCargo, unloadedShips,
                    Ship.CONTAINER));
        }

        timer.shutdown();
        looseThreads.shutdown();
        liquidThreads.shutdown();
        containerThreads.shutdown();

        try {
            unloadedShips = unloadedShipsFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        Simulation.unloadedShips.stream().sorted(ShipInPort::compareTo);

        return new Statistics(unloadedShips,
                sumOfLengthsOfUnloadingQueue, numberOfLengthsOfUnloadingQueue, numberOfLooseCranes,
                numberOfLiquidCranes, numberOfContainerCranes);

    }

    public static long getTime() {
        long stop = System.currentTimeMillis();
        return stop - simulationStartTime.get();
    }

}

