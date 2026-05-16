package com.rishabhtech.dp.creational.singleton;

/**
 * ============================= SINGLETON DESIGN PATTERN =============================
 *
 * Category: Creational
 *
 * Intent:
 *   Ensure a class has only ONE instance throughout the application
 *   and provide a global point of access to it.
 *
 * When to use:
 *   - Only one object is needed to coordinate actions (e.g., DB connection pool, Logger, Config).
 *   - Shared resource that should not be duplicated (e.g., ATM machine, Print Spooler).
 *   - Controlling access to a shared resource in a multi-threaded environment.
 *
 * Real-world examples:
 *   - java.lang.Runtime (Runtime.getRuntime())
 *   - Spring Beans (default scope is singleton)
 *   - Database Connection Pool
 *   - Logger instances
 *   - Caching
 *
 * Key Rules:
 *   1. Private constructor — prevents external instantiation via `new`.
 *   2. Static instance — holds the single object reference.
 *   3. Public static method — provides global access (getInstance()).
 *
 * 5 Ways to achieve Singleton:
 *   1. Eager Initialization        — instance created at class loading time.
 *   2. Lazy Initialization          — instance created only when first requested.
 *   3. Synchronized Method          — thread-safe but slower (locks on every call).
 *   4. Double-Checked Locking       — thread-safe + performant (locks only once).
 *   5. Enum Singleton               — simplest, safest (handles serialization & reflection).
 *
 * How to break Singleton (interview topic):
 *   - Reflection: can call private constructor via setAccessible(true).
 *   - Serialization: deserialization creates a new instance (fix: override readResolve()).
 *   - Cloning: if class implements Cloneable (fix: throw exception in clone()).
 *   - Multithreading: race condition in lazy init (fix: synchronized / double-check / enum).
 *
 * Best Practice:
 *   Use Enum Singleton (way #5) — it's concise, thread-safe, serialization-safe,
 *   and reflection-proof out of the box.
 *
 * =====================================================================================
 */

// ======================== 1. EAGER INITIALIZATION ========================
// Instance is created at class loading time (JVM guarantees thread safety here).
// Pros: Simple, thread-safe without synchronization.
// Cons: Instance is created even if never used — wastes memory if object is heavy.
class DBConnection {

    // Created when the class is loaded by the ClassLoader — guaranteed single instance.
    private static final DBConnection INSTANCE = new DBConnection();

    // Private constructor — no one can do `new DBConnection()` from outside.
    private DBConnection() {}

    public static DBConnection getInstance() {
        return INSTANCE;
    }
}

// ======================== 2. LAZY INITIALIZATION ========================
// Instance is created only when getInstance() is called for the first time.
// Pros: Saves memory — object created only when needed.
// Cons: NOT thread-safe — two threads can create two instances simultaneously.
class DBConnectionLazy {

    private static DBConnectionLazy instance;

    private DBConnectionLazy() {}

    public static DBConnectionLazy getInstance() {
        // Problem: If thread T1 and T2 both reach here at the same time
        // and see instance == null, both will create a new object — breaking singleton!
        if (instance == null)
            instance = new DBConnectionLazy();
        return instance;
    }
}

// ======================== 3. SYNCHRONIZED METHOD ========================
// Fixes the thread-safety issue of Lazy by adding `synchronized` to the method.
// Pros: Thread-safe — only one thread can enter getInstance() at a time.
// Cons: EXPENSIVE — every single call acquires a lock, even after the instance
//        is already created. With 1000s of threads, this becomes a bottleneck.
class DBConnectionSynchronized {

    private static DBConnectionSynchronized instance;

    private DBConnectionSynchronized() {}

    // The lock ensures only one thread enters at a time.
    // After T1 creates the object, T2 enters and sees instance != null, so it returns it.
    // But the lock is acquired on EVERY call — unnecessary after first creation.
    public synchronized static DBConnectionSynchronized getInstance() {
        if (instance == null) {
            instance = new DBConnectionSynchronized();
        }
        return instance;
    }
}

// ======================== 4. DOUBLE-CHECKED LOCKING ========================
// Best of both worlds: lazy + thread-safe + performant.
// Lock is acquired ONLY during first creation. After that, no lock overhead.
// Note: `volatile` is important — prevents instruction reordering by the JVM.
class DBConnectionDoubleLock {

    // volatile ensures all threads see the updated value immediately
    // and prevents partial construction visibility.
    private static volatile DBConnectionDoubleLock instance;

    private DBConnectionDoubleLock() {}

    public static DBConnectionDoubleLock getInstance() {
        // First check (no lock) — fast path for already-created instance.
        // If T1 and T2 both see null here, both proceed to the synchronized block.
        if (instance == null) {
            // Only one thread enters this block at a time.
            synchronized (DBConnectionDoubleLock.class) {
                // Second check (inside lock) — T1 creates the object;
                // when T2 enters, it sees instance is no longer null and skips creation.
                if (instance == null)
                    instance = new DBConnectionDoubleLock();
            }
        }
        // After first creation, all future calls skip the synchronized block entirely.
        return instance;
    }
}

// ======================== 5. ENUM SINGLETON (RECOMMENDED) ========================
// Simplest and safest way. JVM guarantees:
//   - Only one instance (handled by enum mechanism).
//   - Thread-safe (enum constants are initialized once by the ClassLoader).
//   - Serialization-safe (no extra readResolve() needed).
//   - Reflection-proof (JVM prevents instantiation of enums via reflection).
enum DBConnectionEnum {

    INSTANCE;  // This IS the singleton instance

    // Add your fields and methods here
    public void connect() {
        System.out.println("Connected to DB");
    }

    // Usage: DBConnectionEnum.INSTANCE.connect();
}

public class Singleton {
}
