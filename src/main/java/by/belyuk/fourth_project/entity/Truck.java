package by.belyuk.fourth_project.entity;


import by.belyuk.fourth_project.counter.IdCounter;
import by.belyuk.fourth_project.creator.TruckCreatorImpl;
import by.belyuk.fourth_project.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Truck extends Thread {
  private static final Logger logger = LogManager.getLogger();
  private long truckId;
  private TruckCreatorImpl truckCreator = TruckCreatorImpl.getInstance();
  private LogisticBase logisticBase = LogisticBase.getInstance();
  private boolean cargoType;
  private int cargoCapacity;
  private boolean loadStatus;

  public Truck(boolean cargoType, boolean loadStatus, int cargoCapacity) {
    this.truckId = IdCounter.countTruckId();
    this.cargoType = cargoType;
    if (cargoType == truckCreator.isPerishableCargo()) {
      this.setPriority(10);
    } else {
      this.setPriority(1);
    }
    this.cargoCapacity = cargoCapacity;
    this.loadStatus = loadStatus;
  }

  public Truck(boolean loadStatus, int cargoCapacity) {
    this.truckId = IdCounter.countTruckId();
    this.cargoCapacity = cargoCapacity;
    this.loadStatus = loadStatus;
  }

  @Override
  public void run() {

    try {
      logisticBase.loadUnloadTruck(this);
    } catch (CustomException e) {
      e.printStackTrace();
    }
    System.out.println(
        currentThread().getName()
            + " truck id: "
            + getTruckId()
            + " load status: "
            + isLoadStatus()
            + " cargo size: "
            + getCargoCapacity()
            + " perishable: "
            + isCargoType());
  }

  public long getTruckId() {
    return truckId;
  }

  public boolean isCargoType() {
    return cargoType;
  }

  public int getCargoCapacity() {
    return cargoCapacity;
  }

  public boolean isLoadStatus() {
    return loadStatus;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Truck truck = (Truck) o;

    if (truckId != truck.truckId) return false;
    if (cargoType != truck.cargoType) return false;
    if (cargoCapacity != truck.cargoCapacity) return false;
    if (loadStatus != truck.loadStatus) return false;
    return truckCreator != null
        ? truckCreator.equals(truck.truckCreator)
        : truck.truckCreator == null;
  }

  @Override
  public int hashCode() {
    int result = truckCreator != null ? truckCreator.hashCode() : 0;
    result = 31 * result + (int) (truckId ^ (truckId >>> 32));
    result = 31 * result + (cargoType ? 1 : 0);
    result = 31 * result + cargoCapacity;
    result = 31 * result + (loadStatus ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Truck{");
    sb.append("truckCreator=").append(truckCreator);
    sb.append(", truckId=").append(truckId);
    sb.append(", cargoType=").append(cargoType);
    sb.append(", cargoCapacity=").append(cargoCapacity);
    sb.append(", loadStatus=").append(loadStatus);
    sb.append('}');
    return sb.toString();
  }
}
