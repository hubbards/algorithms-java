/**
 * This program consists of some implementations of (greedy and dynamic
 * programming) algorithms for some scheduling problems.
 *
 * @author Spencer Hubbard
 *
 */
// TODO: document algorithms
public class Schedule {
  public static void main(String[] args) {
    // example of (unweighted) interval scheduling problem
    float[] start = { 1, 3, 0, 4, 3, 5, 6, 8 };
    float[] finish = { 4, 5, 6, 7, 8, 9, 10, 11 };
    boolean[] solutionI = intervalScheduleI(start, finish);
    boolean[] solutionR = intervalScheduleR(start, finish);

    // print schedule
    System.out.println("Iterative Solution for Unweighted Interval Scheduling Example");
    printIntervalSchedule(start, finish, solutionI);
    System.out.println("Recursive Solution for Unweighted Interval Scheduling Example");
    printIntervalSchedule(start, finish, solutionR);

    // TODO: example of (weighted) interval scheduling problem

    // example of minimum lateness problem
    float[] deadline = { 6, 8, 9, 9, 14, 15 };
    float[] processing = { 3, 2, 1, 4, 3, 2 };
    start = minLateness(deadline, processing);

    // print schedule
    System.out.println("Solution for Minimum Lateness Example");
    printMinLateness(deadline, processing, start);
  }

  //

  /**
   * Iterative greedy algorithm for (unweighted) interval scheduling problem.
   *
   * @param start
   * start times for requests, i.e., <code>start[i]</code> is the start time
   * of the <code>i</code>th request.
   *
   * @param finish
   * finish times for requests, i.e., <code>finish[i]</code> is the finish
   * time of the <code>i</code>th request. Moreover, the requests are assumed
   * to be ordered by increasing finish time.
   *
   * @return
   * largest compatible subset of given requests.
   */
  // TODO: document running time
  // TODO: rewrite for requests not necessarily sorted by earliest finish time
  public static boolean[] intervalScheduleI(float[] start, float[] finish) {
    // TODO: check that requests are sorted by finish time
    int n = start.length;
    boolean[] schedule = new boolean[n];
    int i = 0;
    while (i < n) {
      // request i has next earliest finish time
      schedule[i] = true;
      int j = i + 1;
      while (j < n && start[j] < finish[i]) {
        // requests j and i are not compatible
        schedule[j] = false;
        j++;
      }
      i = j;
    }
    return schedule;
  }

  /**
   * Recursive greedy algorithm for (unweighted) interval scheduling problem.
   *
   * TODO: document running time
   *
   * TODO: rewrite for requests not necessarily sorted by earliest finish time
   *
   * @param start
   * start times for requests, i.e., <code>start[i]</code> is the start time
   * of the <code>i</code>th request.
   *
   * @param finish
   * finish times for requests, i.e., <code>finish[i]</code> is the finish
   * time of the <code>i</code>th request. Moreover, the requests are assumed
   * to be ordered by increasing finish time.
   *
   * @return
   * largest compatible subset of given requests.
   */
  public static boolean[] intervalScheduleR(float[] start, float[] finish) {
    // TODO: check that requests are sorted by finish time
    int n = start.length;
    boolean[] schedule = new boolean[n];
    schedule[0] = true;
    intervalScheduleR(start, finish, schedule, 0, 1);
    return schedule;
  }

  // Helper method.
  private static void intervalScheduleR(float[] start, float[] finish, boolean[] schedule, int i, int j) {
    if (j < start.length) {
      // more requests to check
      if (finish[i] <= start[j]) {
        // requests i and j are compatable
        schedule[j] = true;
        intervalScheduleR(start, finish, schedule, j, j + 1);
      } else {
        // requests i and j are not compatable
        schedule[j] = false;
        intervalScheduleR(start, finish, schedule, i, j + 1);
      }
    }
  }

  // Helper method. Prints a given schedule to standard output.
  private static void printIntervalSchedule(float[] start, float[] finish, boolean[] schedule) {
    int n = start.length;
    System.out.println();
    for (int i = 0; i < n; i++) {
      System.out.printf("Start: %4.1f Finish: %4.1f Schedule: ", start[i], finish[i]);
      if (schedule[i]) {
        // request i is contained in schedule
        System.out.println("X");
      } else {
        // request i is not contained in schedule
        System.out.println(" ");
      }
    }
    System.out.println();
  }

  /**
   * Dynamic programming algorithm for (weighted) interval scheduling problem.
   *
   * @param start
   * start times for requests, i.e., <code>start[i]</code> is the start time
   * of the <code>i</code>th request.
   *
   * @param finish
   * finish times for requests, i.e., <code>finish[i]</code> is the finish
   * time of the <code>i</code>th request.
   *
   * @param weight
   * weight for requests, i.e., <code>weight[i]</code> is the weight / payoff
   * for scheduling the <code>i</code>th request.
   *
   * @return
   * compatible subset of given requests with maximum weight.
   */
  // TODO: document running time
  public static boolean[] intervalSchedule(float[] start, float[] finish, float[] weight) {
    // TODO: implement
    throw new RuntimeException("method not implemented");
  }

  // Helper method. Prints a given schedule to standard output.
  private static void printIntervalSchedule(float[] start, float[] finish, float[] weight, boolean[] schedule) {
    int n = start.length;
    System.out.println();
    for (int i = 0; i < n; i++) {
      System.out.printf("Start: %4.1f Finish: %4.1f Weight: %4.1f Schedule: ", start[i], finish[i], weight[i]);
      if (schedule[i]) {
        // request i is contained in schedule
        System.out.println("X");
      } else {
        // request i is not contained in schedule
        System.out.println(" ");
      }
    }
    System.out.println();
  }

  // TODO: comment
  public static int[] intervalPartition(float[] start, float[] finish) {
    // TODO: implement greedy algorithm for interval partition problem
    throw new RuntimeException("method not implemented");
  }

  /**
   * Greedy algorithm for minimum lateness problem.
   *
   * @param deadline
   * deadlines for requests, i.e., <code>deadline[i]</code> is the deadline
   * for the <code>i</code>th request.
   *
   * @param processing
   * processing time for requests, i.e., <code>processing[i]</code> is the
   * time to process the <code>i</code>th request.
   *
   * @return
   * start times for schedule with minimum (maximum) lateness.
   */
  public static float[] minLateness(float[] deadline, float[] processing) {
    // TODO: check that requests are sorted by earliest deadline
    int n = deadline.length;
    float[] start = new float[n];
    // request 0 starts at 0
    start[0] = 0;
    for (int i = 1; i < n; i++) {
      // request i starts when request i - 1 finishes
      start[i] = start[i - 1] + processing[i - 1];
    }
    return start;
  }

  /*
   * Helper method. Finds index of request with maximum lateness for a given
   * schedule.
   */
  private static int maxLateness(float[] deadline, float[] processing, float[] start) {
    int n = start.length;
    int i = 0;
    float max = start[0] + processing[0] - deadline[0];
    for (int j = 1; j < n; j++) {
      // lateness for request j
      float tmp = start[j] + processing[j] - deadline[j];
      if (max < tmp) {
        // lateness for request i < lateness for request j
        i = j;
        max = tmp;
      }
    }
    return i;
  }

  // Helper method. Prints a given schedule to standard output.
  private static void printMinLateness(float[] deadline, float[] processing, float[] start) {
    int n = deadline.length;
    int i = maxLateness(deadline, processing, start);
    System.out.println();
    for (int j = 0; j < n; j++) {
      float finish = start[j] + processing[j];
      float lateness = start[j] + processing[j] - deadline[j];
      System.out.printf("Start: %4.1f Finish: %4.1f Lateness: %4.1f", start[j], finish, lateness);
      if (j == i) {
        System.out.println(" <- max lateness");
      } else {
        System.out.println();
      }
    }
    System.out.println();
  }
}
