public class Process {
  final private int eta;
  final private int peakTime;
  final private int PID;
  private State state;
  private int execTime;
  private int waitTime;
  private int lastExecutionTime;

  public Process(int PID, int eta, int peakTime) {
    this.eta = eta;
    this.peakTime = peakTime;
    this.PID = PID;
    this.state = State.WAITING;
    this.execTime = 0;
    this.waitTime = 0;
    this.lastExecutionTime = eta;
  }

  public int getEta() {
    return eta;
  }

  public int getPeakTime() {
    return peakTime;
  }

  public int getPID() {
    return PID;
  }

  public State getState() {
    return state;
  }

  public int getExecTime() {
    return execTime;
  }

  public void setState(State state) {
    this.state = state;
  }

  public int getWaitTime() {
    return waitTime;
  }

  public void exec(int quantum, int time) {
    waitTime += time - lastExecutionTime;
    lastExecutionTime = time + quantum;
    execTime += quantum;
    if(execTime == peakTime) {
      state = State.COMPLETED;
    }else {
      state = State.READY;
    }
  }
}
