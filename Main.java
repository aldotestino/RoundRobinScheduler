import java.util.List;

public class Main {
  public static void main(String[] args) {
    Process p1 = new Process(1,0, 10);
    Process p2 = new Process(2,1, 8);
    Process p3 = new Process(3,5, 6);
    Process p4 = new Process(4,7,4);

    RoundRobinScheduler rrs = new RoundRobinScheduler(2, List.of(p1,p2,p3,p4));
    String info = rrs.schedule();
    System.out.println(info);
  }
}
