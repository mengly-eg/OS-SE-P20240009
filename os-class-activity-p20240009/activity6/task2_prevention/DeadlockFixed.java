package task2_prevention;

import java.util.concurrent.Semaphore;

class Account {
    String name;
    int balance;

    Account(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }
}

class Transfer {
    // Global lock engine covering the entire composite transaction pipeline
    static Semaphore mutex = new Semaphore(1);

    static void transfer(Account from, Account to, int amount) {
        try {
            mutex.acquire(); // Seize the core banking critical section
            
            System.out.println(Thread.currentThread().getName() + " entered critical section. Processing " + from.name + " -> " + to.name);
            
            // Retaining internal latency window to prove systemic immunity to deadlock
            Thread.sleep(100);

            from.balance -= amount;
            to.balance += amount;

            System.out.println(Thread.currentThread().getName() + " successfully moved " + amount + " units.");
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " leaving critical section.");
            mutex.release(); // Safe structural unlock inside the finally block
        }
    }
}

public class DeadlockFixed {
    public static void main(String[] args) throws InterruptedException {
        Account accountA = new Account("Account-A", 1000);
        Account accountB = new Account("Account-B", 1000);

        int initialTotal = accountA.balance + accountB.balance;
        System.out.println("--- Starting DEADLOCK PREVENTION Simulation (Task 2) ---");
        System.out.println("Starting total: " + initialTotal);

        Thread t1 = new Thread(() -> Transfer.transfer(accountA, accountB, 100), "Worker-1");
        Thread t2 = new Thread(() -> Transfer.transfer(accountB, accountA, 200), "Worker-2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        int finalTotal = accountA.balance + accountB.balance;
        System.out.println("\nFinal A: " + accountA.balance);
        System.out.println("Final B: " + accountB.balance);
        System.out.println("Final total: " + finalTotal);
        
        if (initialTotal == finalTotal) {
            System.out.println("No deadlock occurred");
        } else {
            System.out.println("CRITICAL FAULT: Bank balance integrity compromised!");
        }
    }
}
