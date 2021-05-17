package com.example.service1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Random;

@JsonAutoDetect
public class Ship implements Comparable<Ship>{
    public static final double LOOSE_CRANE_PRODUCTIVITY = 3;
    public static final double LIQUID_CRANE_PRODUCTIVITY = 3;
    public static final double CONTAINER_CRANE_PRODUCTIVITY = 3;

    public static final int NUMBER_OF_DAYS = 30;
    public static final int NUMBER_OF_MINUTES_PER_DAY = 1440;
    public static final int MAX_CARGO_WEIGHT_LOOSE = 60000;
    public static final int MAX_CARGO_WEIGHT_LIQUID = 60000;
    public static final int MAX_CARGO_WEIGHT_CONTAINERS = 60000;
    public static final int MAX_LENGTH_SHIP_NAME = 12;
    public static final String LOOSE = "loose";
    public static final String LIQUID = "liquid";
    public static final String CONTAINER = "container";
    public static final String[] CARGO_TYPES = new String[]{LOOSE, LIQUID, CONTAINER};

    private int arrivalDay;
    private int arrivalTime;
    private String  shipName;
    private String nameOfCargoType;
    private int weightOfCargoType;
    private int portBerthTime;
    private int unloadingEndDelay;
    private int deviationFromSchedule;

    public Ship(){
        Random random = new Random();
        arrivalDay = random.nextInt(NUMBER_OF_DAYS);
        arrivalTime = random.nextInt(NUMBER_OF_MINUTES_PER_DAY);
        shipName = generateShipName();
        nameOfCargoType = generateNameOfCargoType();
        weightOfCargoType = generateWeightOfCargoType();
        portBerthTime = generatePortBerthTime();
        unloadingEndDelay = 0;
        deviationFromSchedule = 0;
    }

    public Ship(int arrivalDay, int arrivalTime, String shipName,
                String nameOfCargoType, int weightOfCargoType){
        this.arrivalDay =arrivalDay;
        this.arrivalTime = arrivalTime;
        this.shipName = shipName;
        this.nameOfCargoType = nameOfCargoType;
        this.weightOfCargoType = weightOfCargoType;
        this.portBerthTime = generatePortBerthTime();
        this.unloadingEndDelay = 0;
        this.deviationFromSchedule = 0;
    }

    private String generateShipName(){
        String uppercaseSymbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercaseSymbols = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder randomName = new StringBuilder();
        Random random = new Random();

        int count = random.nextInt(MAX_LENGTH_SHIP_NAME) + 1;
        randomName.append(uppercaseSymbols.charAt(random.nextInt(uppercaseSymbols.length())));
        for(int i = 1; i < count; i++)
        {
            randomName.append(lowercaseSymbols.charAt(random.nextInt(lowercaseSymbols.length())));
        }
        return new String(randomName);
    }

    private String generateNameOfCargoType(){
        Random random = new Random();
        return CARGO_TYPES[random.nextInt(CARGO_TYPES.length)];
    }

    private int generateWeightOfCargoType(){
        Random random = new Random();
        if (nameOfCargoType.equals(LOOSE)){
            return random.nextInt(MAX_CARGO_WEIGHT_LOOSE) + 1;
        }else if (nameOfCargoType.equals(LIQUID)){
            return random.nextInt(MAX_CARGO_WEIGHT_LIQUID) + 1;
        }else{
            return random.nextInt(MAX_CARGO_WEIGHT_CONTAINERS) + 1;
        }
    }

    private int generatePortBerthTime(){
        if (nameOfCargoType.equals(LOOSE)){
            return (int)Math.ceil(weightOfCargoType / LOOSE_CRANE_PRODUCTIVITY);
        }else if (nameOfCargoType.equals(LIQUID)){
            return (int)Math.ceil(weightOfCargoType / LIQUID_CRANE_PRODUCTIVITY);
        }else{
            return (int)Math.ceil(weightOfCargoType / CONTAINER_CRANE_PRODUCTIVITY);
        }
    }

    public int getArrivalDay() {
        return arrivalDay;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public String getShipName(){
        return shipName;
    }

    public String getNameOfCargoType(){
        return nameOfCargoType;
    }

    public int getWeightOfCargoType(){
        return weightOfCargoType;
    }

    public int getPortBerthTime(){
        return portBerthTime;
    }

    public int getUnloadingEndDelay(){
        return unloadingEndDelay;
    }

    public int getDeviationFromSchedule(){
        return deviationFromSchedule;
    }


    public int setArrivalDay(int arrivalDay) {
        return this.arrivalDay = arrivalDay;
    }

    public int setArrivalTime(int arrivalTime) {
        return this.arrivalTime = arrivalTime;
    }

    public String setShipName(String shipName){
        return this.shipName = shipName;
    }

    public String setNameOfCargoType(String nameOfCargoType){
        return this.nameOfCargoType = nameOfCargoType;
    }

    public int setWeightOfCargoType(int weightOfCargoType){
        return this.weightOfCargoType = weightOfCargoType;
    }

    public int setPortBerthTime(int portBerthTime){
        return this.portBerthTime = portBerthTime;
    }

    public int setUnloadingEndDelay(int unloadingEndDelay){
        return this.unloadingEndDelay = unloadingEndDelay;
    }

    public int setDeviationFromSchedule(int deviationFromSchedule) { return this.deviationFromSchedule = deviationFromSchedule; }

    @Override
    public String toString() {
        int arrivalTimeDays = arrivalTime / (24 * 60);
        int arrivalTimeHours = (arrivalTime % (24 * 60)) / 60;
        int arrivalTimeMinutes = (arrivalTime % (24 * 60)) % 60;

        int portBerthTimeDays = portBerthTime / (24 * 60);
        int portBerthTimeHours = (portBerthTime % (24 * 60)) / 60;
        int portBerthTimeMinutes = (portBerthTime % (24 * 60)) % 60;

        int unloadingEndDelayDays = unloadingEndDelay / (24 * 60);
        int unloadingEndDelayHours = (unloadingEndDelay % (24 * 60)) / 60;
        int unloadingEndDelayMinutes = (unloadingEndDelay % (24 * 60)) % 60;

        return "Ship{" +
                "arrivalDay=" + (arrivalDay + 1) +
                ", arrivalTime=" + arrivalTimeDays + ":" + arrivalTimeHours
                + ":" + arrivalTimeMinutes +
                ", shipName='" + shipName + '\'' +
                ", nameOfCargoType='" + nameOfCargoType + '\'' +
                ", weightOfCargoType=" + weightOfCargoType +
                ", portBerthTime=" + portBerthTimeDays + ":" + portBerthTimeHours
                + ":" + portBerthTimeMinutes +
                ", unloadingEndDelay=" + unloadingEndDelayDays + ":" + unloadingEndDelayHours
                + ":" + unloadingEndDelayMinutes +
                ", deviationFromSchedule=" + deviationFromSchedule +
                '}' + "\n";
    }

    @Override
    public int compareTo(Ship ship) {
        Integer arrivalDay1 = this.getArrivalDay() + this.getDeviationFromSchedule();
        Integer arrivalDay2 = ship.getArrivalDay() + ship.getDeviationFromSchedule();
        int arrivalDayComp = arrivalDay1.compareTo(arrivalDay2);

        if (arrivalDayComp != 0) {
            return arrivalDayComp;
        }

        Integer arrivalTime1 = this.getArrivalTime();
        Integer arrivalTime2 = ship.getArrivalTime();
        return arrivalTime1.compareTo(arrivalTime2);
    }

    public static boolean isArrivalDayCorrect(int arrivalDay){
        if (arrivalDay < 0 || arrivalDay >= NUMBER_OF_DAYS){
            System.out.println("Incorrect arrival day. It should be Integer between 1 and 30.");
            return false;
        }
        return true;
    }

    public static boolean isArrivalTimeCorrect(int arrivalTime){
        if (arrivalTime < 0 || arrivalTime >= NUMBER_OF_MINUTES_PER_DAY){
            System.out.println("Incorrect arrival time. It should be Integer between 0 and 1439.");
            return false;
        }
        return true;
    }

    public static boolean isShipNameCorrect(String shipName){
        char symbol = shipName.charAt(0);
        if (!Character.isUpperCase(symbol)){
            System.out.println("Incorrect ship name. The first letter is uppercase.");
            return false;
        }
        for (int i = 1; i < shipName.length() - 1; i++){
            symbol = shipName.charAt(i);
            if (!Character.isLowerCase(symbol) && !(symbol == '-')){
                System.out.println("Incorrect ship name. Letters other than the first are in lower case. " +
                        "The name can also contain a hyphen.");
                return false;
            }
        }
        symbol = shipName.charAt(shipName.length() - 1);
        if (!Character.isLowerCase(symbol)){
            System.out.println("Incorrect ship name. The last letter is lowercase.");
            return false;
        }

        return true;
    }

    public static boolean areCargoTypeAndWeightCorrect(String nameOfCargoType, int weightOfCargoType){
        boolean isCargoTypeInArray = false;
        for (String nameOfType:CARGO_TYPES) {

            if (nameOfType.equals(nameOfCargoType)) {
                isCargoTypeInArray = true;
                break;
            }
        }
        if(!isCargoTypeInArray){
            System.out.println("Incorrect cargo type. It should be loose, liquid or container.");
            return false;
        }

        if (nameOfCargoType.equals(LOOSE)) {
            if (weightOfCargoType < 1 || weightOfCargoType > MAX_CARGO_WEIGHT_LOOSE){
                System.out.println("Incorrect cargo weight. It should be between 1 and 20 000 tones.");
                return false;
            }
        } else if (nameOfCargoType.equals(LIQUID)) {
            if (weightOfCargoType < 1 || weightOfCargoType > MAX_CARGO_WEIGHT_LIQUID) {
                System.out.println("Incorrect cargo weight. It should be between 1 and 20 000 tones.");
                return false;
            }
        }else{
            if (weightOfCargoType < 1 || weightOfCargoType > MAX_CARGO_WEIGHT_CONTAINERS){
                System.out.println("Incorrect cargo weight. It should be between 1 and 4 000 containers.");
                return false;
            }
        }

        return true;
    }

}


