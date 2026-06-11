package task1_deadlock;

import java.util.concurrent.Semaphore;

class Account {
    String name;
    int balance;
    Semaphore lock = new Semaphore(1);

    Account(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }
}

class Transfer {
    static volatile boolean t1Completed = false;
    static volatile boolean t2Completed = false;

    static void transfer(Account from, Account to, int amount) {
        try {
            System.out.println(Thread.currentThread().getName() + " trying to lock FROM " + from.name);
            from.lock.acquire();
            System.out.println(Thread.currentThread().getName() + " locked FROM " + from.name);

            // Intentional sleep window allowing the opposing worker thread to seize its source lock
            Thread.sleep(100);

            System.out.println(Thread.currentThread().getName() + " trying to lock TO " + to.name);
            to.lock.acquire();
            System.out.println(Thread.currentThread().getName() + " locked TO " + to.name);

            from.balance -= amount;
            to.balance += amount;

            System.out.println(Thread.currentThread().getName() + " transfer completed");

            if (Thread.currentThread().getName().equals("Thread-1")) t1Completed = true;
            if (Thread.currentThread().getName().equals("Thread-2")) t2Completed = true;

            to.lock.release();
            from.lock.release();
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted.");
        }
    }
}

public class DeadlockSimulation {
    public static void main(String[] args) {
        Account accountA = new Account("Account-A", 1000);
        Account accountB = new Account("Account-B", 1000);

        System.out.println("--- Starting DEADLOCK Simulation (Task 1) ---");
        System.out.println("Starting Balance - Account A: " + accountA.balance + " | Account B: " + accountB.balance);

        Thread t1 = new Thread(() -> Transfer.transfer(accountA, accountB, 100), "Thread-1");
        Thread t2 = new Thread(() -> Transfer.transfer(accountB, accountA, 200), "Thread-2");

        t1.start();
        t2.start();

        // Active Watchdog Thread Monitor
        Thread watchdog = new Thread(() -> {
            try {
                Thread.sleep(2000); // 2-second timeout window
                if (!Transfer.t1Completed || !Transfer.t2Completed) {
                    System.out.println("\n=============================================");
                    System.out.println("Deadlock detected: transactions are stuck");
                    System.out.println("Worker 1 is waiting for Account-B");
                    System.out.println("Worker 2 is waiting for Account-A");
                    System.out.println("=============================================");
                    
                    // Display trapped final balances to demonstrate no mutations occurred
                    System.out.println("Current Balance - Account A: " + accountA.balance + " | Account B: " + accountB.balance);
                    System.exit(1); 
                }
            } catch (InterruptedException e) {
                // Normal termination exit path if threads finish before watchdog
            }
        });
        watchdog.setDaemon(true);
        watchdog.start();
    }
}
