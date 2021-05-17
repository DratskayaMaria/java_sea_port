package service3;

import com.example.service1.Ship;

import static java.lang.Thread.sleep;

public class TimeManager implements Runnable{
    public long arrivalTimeOfTheLastShip;

    @Override
    public void run() {
            for (ShipInPort shipInPort : ScheduleProcessing.allShips) {

                long arrivalTimeOfCurrentShip = 0;
                try {
                    arrivalTimeOfCurrentShip = (long) (shipInPort.ship.getArrivalDay()) * 24 * 60 +
                            shipInPort.ship.getArrivalTime() +
                            (long) shipInPort.ship.getDeviationFromSchedule() * 24 * 60;
                    sleep((int) (arrivalTimeOfCurrentShip - arrivalTimeOfTheLastShip) / Crane.COEF);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                shipInPort.startTime = Simulation.getTime();
                arrivalTimeOfTheLastShip = arrivalTimeOfCurrentShip;

                if (shipInPort.ship.getNameOfCargoType().equals(Ship.LOOSE)){
                    Simulation.currentShipsWithLooseCargo.add(shipInPort);
                } else if (shipInPort.ship.getNameOfCargoType().equals(Ship.LIQUID)){
                    Simulation.currentShipsWithLiquidCargo.add(shipInPort);
                } else{
                    Simulation.currentShipsWithContainerCargo.add(shipInPort);
                }
            }
        }
}





