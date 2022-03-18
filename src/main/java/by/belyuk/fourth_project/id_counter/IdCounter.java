package by.belyuk.fourth_project.id_counter;

public class IdCounter {
  private static long truckIdCount = 1;
  private static long terminalIdCount = 1;

  public static long countTruckId() {
    return truckIdCount++;
  }

  public static long countTerminalId() {
    return terminalIdCount++;
  }
}
