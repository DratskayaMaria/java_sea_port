package com.example.service2;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.example.service1.Ship;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class RecordAddingInFile {
    public static final String ADD = "ADD";
    public static final String STOP = "STOP";
    public static final String OWN = "OWN";
    public static final String RANDOM = "RANDOM";

    public static int addRecord(){

        int numberOfAddedShips = 0;

        try{

            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

            while (true)
            {
                System.out.println("If you want to add a ship record, enter " + ADD + ".\n" +
                        "If you want to finish adding records, enter " + STOP + ".");
                Scanner in = new Scanner(System.in);
                String str = in.nextLine();

                if (str.equals(ADD)){

                    System.out.println("If you want to add your own record, enter " + OWN + ".\n" +
                            "If you want to add a random record, enter " + RANDOM+ ".");

                    String record = in.nextLine();

                    if (record.equals(RANDOM)){
                        Ship ship = new Ship();
                        System.out.println("Random record:");
                        System.out.println(ship.toString());
                        writer.writeValue(new FileOutputStream(JsonSerialization.NAME_OF_SCHEDULE_JSON_FILE, true), ship);
                        numberOfAddedShips++;
                        System.out.println("The random record has been added.");
                    }else if (record.equals(OWN)){
                        System.out.println("Enter ship name:");
                        String shipName = in.nextLine();
                        if(!Ship.isShipNameCorrect(shipName)){
                            continue;
                        }
                        System.out.println("Enter name of cargo type:");
                        String nameOfCargoType = in.nextLine();
                        System.out.println("Enter weight of cargo:");
                        int weightOgCargoType = in.nextInt();
                        if(!Ship.areCargoTypeAndWeightCorrect(nameOfCargoType, weightOgCargoType)){
                            continue;
                        }
                        System.out.println("Enter arrival day:");
                        int arrivalDay = in.nextInt();
                        if(!Ship.isArrivalDayCorrect(arrivalDay)) {
                            continue;
                        }
                        System.out.println("Enter arrival time:");
                        int arrivalTime = in.nextInt();
                        if(!Ship.isArrivalTimeCorrect(arrivalTime)) {
                            continue;
                        }

                        Ship ship = new Ship(arrivalDay, arrivalTime, shipName, nameOfCargoType, weightOgCargoType);

                        writer.writeValue(new FileOutputStream(JsonSerialization.NAME_OF_SCHEDULE_JSON_FILE, true), ship);
                        numberOfAddedShips++;
                        System.out.println("Your own record has been added.");

                    }else{
                        System.out.println("You entered the wrong command. Try again.");
                    }
                }else if(str.equals(STOP)){
                    break;
                }else{
                    System.out.println("You entered the wrong command. Try again.");
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return numberOfAddedShips;
    }
}
