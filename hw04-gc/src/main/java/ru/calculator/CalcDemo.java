package ru.calculator;


/*
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
*/


import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;

public class CalcDemo {
    public static void main(String[] args) {
        long counter = 100_000_000;
        var summator = new Summator();
        long startTime = System.currentTimeMillis();

        for (var idx = 0; idx < counter; idx++) {
            var data = new Data(idx);
            summator.calc(data);

            if (idx % 10_000_000 == 0) {
                System.out.println(LocalDateTime.now() + " current idx:" + idx);
            }
        }

        long delta = System.currentTimeMillis() - startTime;
        System.out.println(summator.getPrevValue());
        System.out.println(summator.getPrevPrevValue());
        System.out.println(summator.getSumLastThreeValues());
        System.out.println(summator.getSomeValue());
        System.out.println(summator.getSum());

        printGCStats();
        System.out.println("spend msec:" + delta + ", sec:" + (delta / 1000));
    }

    public static void printGCStats() {
        long totalGarbageCollections = 0;
        long garbageCollectionTime = 0;
        for(GarbageCollectorMXBean gc :
                ManagementFactory.getGarbageCollectorMXBeans()) {
            long count = gc.getCollectionCount();
            if(count >= 0) {
                totalGarbageCollections += count;
            }
            long time = gc.getCollectionTime();
            if(time >= 0) {
                garbageCollectionTime += time;
            }
        }
        System.out.println("Total Garbage Collections: "
                           + totalGarbageCollections);
        System.out.println("Total Garbage Collection Time (ms): "
                           + garbageCollectionTime);
    }
}
