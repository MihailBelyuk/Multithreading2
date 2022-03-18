package by.belyuk.fourth_project.entity;

import by.belyuk.fourth_project.creator.TruckCreatorImpl;
import by.belyuk.fourth_project.exception.CustomException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {
  private static final Logger logger = LogManager.getLogger();
  private static final int TIME_FOR_PROCESSING_SINGLE_CARGO_POSITION = 10;
  private static final int TERMINAL_QUANTITY = 5;
  private static LogisticBase instance;
  private static ReentrantLock lock = new ReentrantLock();
  private static AtomicBoolean isCreate = new AtomicBoolean(false);
  private TruckCreatorImpl truckCreator = TruckCreatorImpl.getInstance();
  private BlockingQueue terminals;
  private Terminal terminal;

  private LogisticBase() {
    terminals = new LinkedBlockingDeque();
    for (int i = 0; i < TERMINAL_QUANTITY; i++) {
      terminal = new Terminal();
      try {
        terminals.put(terminal);
      } catch (InterruptedException e) {
        logger.log(Level.ERROR, " Put-process was interrupted", e);
      }
    }
  }

  public static LogisticBase getInstance() {
    if (!isCreate.get()) {
      lock.lock();
      try {
        if (instance == null) {
          instance = new LogisticBase();
          isCreate.set(true);
        }
      } finally {
        lock.unlock();
      }
    }
    return instance;
  }

  public void loadUnloadTruck(Truck truck) throws CustomException {
    if (truck == null) {
      logger.log(Level.ERROR, "Provided method parameter is null");
      throw new CustomException("Provided method parameter is null");
    } else {
      try {
        lock.lock();
        terminals.take();
        if (!terminal.isInProcess()) {
          TimeUnit.MILLISECONDS.sleep(
              (long) TIME_FOR_PROCESSING_SINGLE_CARGO_POSITION * truck.getCargoCapacity());
          terminals.put(this.terminal);
        }
      } catch (InterruptedException e) {
        logger.log(Level.ERROR, "Current thread error.");
        Thread.currentThread().interrupt();
      } finally {
        lock.unlock();
      }
    }
  }

  public Terminal getTerminal() {
    return terminal;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LogisticBase that = (LogisticBase) o;

    if (truckCreator != null ? !truckCreator.equals(that.truckCreator) : that.truckCreator != null)
      return false;
    if (terminals != null ? !terminals.equals(that.terminals) : that.terminals != null)
      return false;
    return terminal != null ? terminal.equals(that.terminal) : that.terminal == null;
  }

  @Override
  public int hashCode() {
    int result = truckCreator != null ? truckCreator.hashCode() : 0;
    result = 31 * result + (terminals != null ? terminals.hashCode() : 0);
    result = 31 * result + (terminal != null ? terminal.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("LogisticBase{");
    sb.append("truckCreator=").append(truckCreator);
    sb.append(", terminals=").append(terminals);
    sb.append(", terminal=").append(terminal);
    sb.append('}');
    return sb.toString();
  }
}
