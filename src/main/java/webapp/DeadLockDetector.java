package webapp;

import java.lang.management.*;
import java.util.logging.Logger;

public class DeadLockDetector implements Runnable {

    private static final Logger log = Logger.getLogger(DeadLockDetector.class.getName());

    private static DeadLockDetector instance;

    private final ThreadMXBean tmx;

    public static DeadLockDetector getInstance() {
        return instance;
    }

    private DeadLockDetector() {
        tmx = ManagementFactory.getThreadMXBean();
        instance = this;
    }

    public final void run() {

        boolean deadlock = false;
        while (!deadlock) {
            try {
                long[] ids = tmx.findDeadlockedThreads();

                // Deadlock detected
                if (ids != null) {

                    deadlock = true;
                    ThreadInfo[] tis = tmx.getThreadInfo(ids, true, true);
                    String info = "DeadLock Found!\n";
                    for (ThreadInfo ti : tis) {
                        info += ti.toString();
                    }

                    for (ThreadInfo ti : tis) {
                        LockInfo[] locks = ti.getLockedSynchronizers();
                        MonitorInfo[] monitors = ti.getLockedMonitors();
                        if (locks.length == 0 && monitors.length == 0) {
                            // This thread isn't a reason, it's just blocked by external deadlock
                            continue;
                        }

                        ThreadInfo dl = ti;
                        info += "Java-level deadlock:\n";
                        info += "\t" + dl.getThreadName() + " is waiting to lock " + dl.getLockInfo().toString() + " which is held by " + dl.getLockOwnerName() + "\n";
                        while ((dl = tmx.getThreadInfo(new long[]{dl.getLockOwnerId()}, true, true)[0]).getThreadId() != ti.getThreadId()) {
                            info += "\t" + dl.getThreadName() + " is waiting to lock " + dl.getLockInfo().toString() + " which is held by " + dl.getLockOwnerName() + "\n";
                        }
                    }


                    log.severe(info);
                    log.severe("Shutting down server with exit code = 2, startup script will do authomatic restart.");
                    System.exit(2);
                }

                Thread.sleep(200);
            }
            catch (Exception e) {
                log.severe(e.getLocalizedMessage());
            }
        }
    }

    public static void DeadLockDetectorStart() {
        Thread t = new Thread(new DeadLockDetector());
        t.setName("DeadLock Monitor");
        t.setDaemon(true);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
        log.info("DeadLock Detector started.");
    }
}
