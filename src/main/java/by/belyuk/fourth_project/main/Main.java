package by.belyuk.fourth_project.main;

import by.belyuk.fourth_project.creator.TruckCreatorImpl;
import by.belyuk.fourth_project.entity.Truck;

public class Main {
  public static void main(String[] args) {
    TruckCreatorImpl truckCreator = TruckCreatorImpl.getInstance();
    for (Truck truck : truckCreator.getTrucks()) {
      truck.start();
    }
  }
}
