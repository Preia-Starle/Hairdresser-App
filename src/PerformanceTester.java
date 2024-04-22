
public class PerformanceTester {

    public static long runPerformanceTest(Runnable codeBlock) {
        long totalTime = 0;

        long startTime = System.nanoTime();
        codeBlock.run();
        long endTime = System.nanoTime();
        totalTime += endTime - startTime;

        return totalTime;

    }

}
