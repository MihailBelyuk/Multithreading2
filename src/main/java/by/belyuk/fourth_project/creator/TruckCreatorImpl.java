package by.belyuk.fourth_project.creator;


import by.belyuk.fourth_project.validator.Validator;
import by.belyuk.fourth_project.entity.Truck;
import by.belyuk.fourth_project.exception.CustomException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TruckCreatorImpl implements TruckCreator {
  private static final TruckCreatorImpl instance = new TruckCreatorImpl();
  private static final Logger logger = LogManager.getLogger();
  private static final String SPLIT_REGEX = " ";
  private static final String TRUCK_PROPERTY_FILE_NAME = "property.truck";
  private static final String EMPTY = "empty";
  private static final String LOADED = "loaded";
  private static final String PERISHABLE = "perishable";
  private static final String NONPERISHABLE = "nonperishable";
  private static final String DIGIT_REGEXP = "\\d+";
  private Validator validator = Validator.getInstance();
  private boolean emptyTruck = false;
  private boolean loadedTruck = true;
  private boolean perishableCargo = true;
  private boolean normalCargo = false;
  private List<Truck> trucks = new ArrayList<>();

  private TruckCreatorImpl() {}

  public static TruckCreatorImpl getInstance() {
    return instance;
  }

  @Override
  public void create(String filePath) {
    if (!validator.validate(filePath)) {
      try {
        throw new CustomException("Filepath is not valid.");
      } catch (CustomException e) {
        logger.log(Level.ERROR, "Filepath is not valid.");
      }
    } else {
      ResourceBundle resourceBundle = ResourceBundle.getBundle(filePath);
      int capacity = 0;
      boolean loadStatus = false;
      boolean cargoType;
      for (String key : resourceBundle.keySet()) {
        Truck truck = null;
        String line = resourceBundle.getString(key);
        String[] words = line.split(SPLIT_REGEX);
        for (String word : words) {
          if (word.matches(DIGIT_REGEXP)) {
            capacity = Integer.parseInt(word);
          }
          if (word.matches(EMPTY)) {
            loadStatus = emptyTruck;
            truck = new Truck(loadStatus, capacity);
          }
          if (word.matches(LOADED)) {
            loadStatus = loadedTruck;
          }
          if (word.matches(PERISHABLE)) {
            cargoType = perishableCargo;
            truck = new Truck(cargoType, loadStatus, capacity);
          }
          if (word.matches(NONPERISHABLE)) {
            cargoType = normalCargo;
            truck = new Truck(cargoType, loadStatus, capacity);
          }
        }
        trucks.add(truck);
      }
    }
  }

  public boolean isPerishableCargo() {
    return perishableCargo;
  }

  public List<Truck> getTrucks() {
    create(TRUCK_PROPERTY_FILE_NAME);
    return trucks;
  }
}
