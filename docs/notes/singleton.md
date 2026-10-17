# Singleton Pattern

Singleton — **creational** design pattern. Ensures a class has **exactly one instance** and
provides a **global access point** to it.

## When to use

- Exactly one shared resource: logger, config manager, cache, connection pool, registry.
- The instance is expensive to create, or having more than one would break correctness
  (e.g. two log files, two connection pools).

**Do not use** when a plain static utility works, when the object is immutable and stateless,
or when you need many instances later (factories/registries are usually better).

## Rules of a well-formed singleton

1. Private constructor (blocks `new`).
2. Static field holding the instance.
3. Public static accessor (`getInstance()` / `INSTANCE`).
4. Serialization, reflection and cloning must not create a second instance (see below).
5. If lazy and used from several threads → thread-safe initialization.

## 1. Naive lazy initialization — `src/singleton/examples/AppConfig.java`

```java
public static synchronized AppConfig getInstance() {
    if (instance == null) {
        instance = new AppConfig();
    }
    return instance;
}
```

Correct, but every call pays for `synchronized` even after the instance exists.
`src/singleton/examples/RaceConditionInSimpleSingelton.java` shows what happens without it.

## 2. Double-checked locking — `src/singleton/ThreadSafeSingletonWDoubleCheckedLocking/Logger.java`

```java
private static volatile Logger instance;   // volatile is mandatory

public static Logger getInstance() {
    if (instance == null) {                // check 1 (no lock)
        synchronized (Logger.class) {
            if (instance == null) {        // check 2 (under lock)
                instance = new Logger();
            }
        }
    }
    return instance;
}
```

- First check avoids locking after warm-up; second check avoids creating two instances.
- `volatile` is required: without it the JVM may publish a *partially constructed* object
  (instruction reordering / CPU cache visibility).

## 3. Lazy initialization without locks — the Initialization-on-Demand Holder idiom

Used by `src/singleton/SingletonRegistryForMultipleInstances/DatabaseConnectionPool.java`:

```java
private static class Holder {
    private static final DatabaseConnectionPool INSTANCE = new DatabaseConnectionPool();
}

public static DatabaseConnectionPool getInstance() {
    return Holder.INSTANCE;
}
```

The JLS guarantees `Holder` is loaded only on first use of `getInstance()` — lazy **and**
thread-safe with zero synchronization cost.

## 4. Enum singleton — `src/singleton/SingletonWithEnum/CacheManager.java`

```java
public enum CacheManager {
    INSTANCE;
    // ...
}
```

Effectively a singleton that is immune to reflection, serialization and subclassing for free.
Recommended by *Effective Java* when you don't need lazy-loading tricks.

## 5. Singleton vs. registry of many instances

`DatabaseConnectionPool` is deliberately *not* "one object for everything": it is one
**pool** (singleton) that manages many `Connection` instances. A registry/pool is the right
abstraction when the resource itself is multiple.

## Protecting a singleton

`src/singleton/ProtectSingletonPatterns/` attacks the pattern and blocks each attack:

| Attack | How it works | Defense |
|---|---|---|
| Reflection | `Constructor.setAccessible(true)` then `newInstance()` | throw in the constructor if an instance already exists |
| Serialization | deserialize a copy of the instance | `readResolve()` / enum type |
| Cloning | `clone()` on a `Cloneable` singleton | throw in `clone()` / don't implement `Cloneable` |

`SingletonAttackTests` runs all three attacks against `AppSecurityManager`.

## Thread-safety summary

| Variant | Lazy? | Thread-safe? | Cost |
|---|---|---|---|
| `synchronized` accessor | yes | yes | lock on **every** call |
| Double-checked locking + `volatile` | yes | yes | one volatile read per call |
| Holder idiom | yes | yes | none |
| Enum | no (constructor time) | yes | none |

## Pitfalls

- Class loaders / multiple `ClassLoader`s → each one gets its own "singleton".
- `ObjectInputStream` can create a hidden second instance without `readResolve()`.
- Mutable state in a singleton is hidden global state — it couples tests together.
- Overuse leads to tight coupling; prefer dependency injection over reaching for the
  global accessor everywhere.