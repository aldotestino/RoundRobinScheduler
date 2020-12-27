import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class RoundRobinScheduler implements Scheduler {
  final private int quantum;
  private int time;
  private final List<Process> processes;
  private final Queue<Process> processQueue;

  public RoundRobinScheduler(int quantum, List<Process> processes) {
    this.processes = processes.stream().sorted(Comparator.comparingInt(Process::getEta)).collect(Collectors.toList());
    this.quantum = quantum;
    this.time = 0;
    this.processQueue = new LinkedList<>();
    this.processQueue.add(this.processes.get(0));
  }

  @Override
  public String schedule() {
    StringBuilder info = new StringBuilder();
    while(!processQueue.isEmpty()) {
      Process currentProcess = processQueue.poll();
      currentProcess.setState(State.RUNNING);
      currentProcess.exec(quantum, time);
      time+=quantum;
      for (Process process : processes) {
        if (process.getEta() <= time && process.getState() == State.WAITING) {
          process.setState(State.READY);
          processQueue.add(process);
        }
      }
      if(currentProcess.getState() != State.COMPLETED) {
        processQueue.add(currentProcess);
      }else {
        info.append("Completed PID ").append(currentProcess.getPID()).append("\n");
      }
      info.append("Executed PID ").append(currentProcess.getPID())
              .append(" for ").append(quantum).append("ms\n")
              .append("execution time: ").append(time).append("ms")
              .append("\n---------------------------------------------------------\n");
    }
    float totalWaitTime = 0;
    for (Process process : processes) {
      totalWaitTime += process.getWaitTime();
      info.append("PID ").append(process.getPID()).append(" wait time: ").append(process.getWaitTime()).append("ms\n");
    }
    float averageWaitTime = totalWaitTime / processes.size();
    info.append("Average wait time: ").append(averageWaitTime).append("ms");
    return info.toString();
  }
}
