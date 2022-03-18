package by.belyuk.fourth_project.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Validator {
  private static final Logger logger = LogManager.getLogger();
  private static final Validator validator = new Validator();

  private Validator() {}

  public static Validator getInstance() {
    return validator;
  }

  public boolean validate(String filePath) {
    boolean isValid = false;
    if (filePath != null && !filePath.isEmpty()) {
      isValid = true;
      logger.log(Level.INFO, "File path is valid.");
    }
    return isValid;
  }
}
