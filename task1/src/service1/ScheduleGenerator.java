package service1;

import java.util.ArrayList;
import java.util.Random;

public class ScheduleGenerator{
    public static final int MAX_NUMBER_OF_SHIPS = 1000;
    public static final int MIN_NUMBER_OF_SHIPS = 600;

    private ArrayList<Ship> ships = new ArrayList<Ship>();

    public void generateSchedule(){
        int diff = MAX_NUMBER_OF_SHIPS - MIN_NUMBER_OF_SHIPS;
        Random random = new Random();
        int numberOfShips = random.nextInt(diff + 1) + MIN_NUMBER_OF_SHIPS;

        for (int i = 0; i < numberOfShips; i++) {
            ships.add(new Ship());
        }
    }

    public void addShip(Ship ship){
        ships.add(ship);
    }

    public ArrayList<Ship> getSchedule(){
        return ships;
    }

    public void printInfoAboutSchedule(){
        System.out.println("Number of generated ships: " + ships.size());
        ships.forEach(ship -> System.out.println(ship.toString()));
    }
}

