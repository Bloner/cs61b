package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> a = new AList<>();
        AList<Double> b = new AList<>();
        AList<Integer> d = new AList<>();
        d.addLast(10000);
        d.addLast(10000);
        d.addLast(10000);
        d.addLast(10000);
        d.addLast(10000);
        d.addLast(10000);
        d.addLast(10000);
        d.addLast(10000);

        a.addLast(1000);
        a.addLast(2000);
        a.addLast(4000);
        a.addLast(8000);
        a.addLast(16000);
        a.addLast(32000);
        a.addLast(64000);
        a.addLast(128000);
        for(int i = 0; i < a.size(); i++){
            SLList<Integer> c = new SLList<>();
            for(int j = 0; j < a.get(i); j++){
                c.addLast(j);
            }

            Stopwatch sw = new Stopwatch();
            for(int j = 0; j < 10000; j++){
                c.getLast();
            }
            double timeInSeconds = sw.elapsedTime();
            b.addLast(timeInSeconds);
        }

        printTimingTable(a, b, d);
    }

}
