package by.belyuk.fourth_project.entity;

import by.belyuk.fourth_project.id_counter.IdCounter;

public class Terminal {
  private long terminalId;
  private boolean inProcess;

  public Terminal() {
    this.terminalId = IdCounter.countTerminalId();
  }

  public boolean isInProcess() {
    return inProcess;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Terminal terminal = (Terminal) o;

    return terminalId == terminal.terminalId;
  }

  @Override
  public int hashCode() {
    return (int) (terminalId ^ (terminalId >>> 32));
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Terminal{");
    sb.append("terminalId=").append(terminalId);
    sb.append('}');
    return sb.toString();
  }
}
