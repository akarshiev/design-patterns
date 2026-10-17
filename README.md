# design-patterns

Java examples of classic **creational design patterns** — Singleton and Factory Method —
written as small, self-contained exercises. Plain Java (no framework), runnable with
`javac` / `java` only.

## Project structure

```
design-patterns/
├── README.md
├── .gitignore
├── docs/
│   ├── notes/
│   │   ├── solid-principles.md
│   │   └── singleton.md
│   └── exercises/
│       ├── singleton-exercises.pdf
│       └── factory-method-exercises.pdf
├── src/
│   ├── singleton/
│   │   ├── examples/
│   │   ├── lazyInitialization/
│   │   ├── ProtectSingletonPatterns/
│   │   ├── SingletonRegistryForMultipleInstances/
│   │   ├── SingletonWithEnum/
│   │   └── ThreadSafeSingletonWDoubleCheckedLocking/
│   ├── factory/
│   │   ├── document/
│   │   ├── notification/
│   │   ├── payment_processor/
│   │   └── shape/
│   └── Main.java
└── tests/
```

## Patterns

### Singleton (`src/singleton/`)

| Package | Idea |
|---|---|
| `examples` | naive lazy singleton + a deliberate race-condition demo |
| `lazyInitialization` | `ConfigurationManager` reads `config.properties` once |
| `ThreadSafeSingletonWDoubleCheckedLocking` | `Logger` writes `app.log` with DCL + `volatile` |
| `SingletonWithEnum` | `CacheManager` enum singleton with TTL cache |
| `SingletonRegistryForMultipleInstances` | one connection pool managing many `Connection`s (holder idiom) |
| `ProtectSingletonPatterns` | anti-attack tests: reflection, serialization, cloning |

### Factory Method (`src/factory/`)

| Package | Idea |
|---|---|
| `shape` | switch-based factory for `Circle` / `Rectangle` / `Square` |
| `payment_processor` | `PaymentFactory` → CreditCard / Payme / Click |
| `document` | `DocumentFactory` + `DocumentType` enum → Text / PDF / Spreadsheet |
| `notification` | registry-based factory: creators registered at runtime |

## Build & run

Requires JDK 21+ (uses switch expressions and unnamed variables).

```bash
# compile everything into out/
javac -d out $(find src -name "*.java")

# run the demo entry point
java -cp out Main
```

`config.properties` (used by `ConfigurationManager`) and the generated `app.log`
must stay in the working directory.

## Docs

- [docs/notes/solid-principles.md](docs/notes/solid-principles.md) — SOLID principles mapped to these examples
- [docs/notes/singleton.md](docs/notes/singleton.md) — singleton variants, thread-safety, attacks and defenses
- [docs/exercises/](docs/exercises/) — practice tasks (PDF) for both patterns