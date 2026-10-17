# SOLID Principles

Five design principles that make object-oriented systems easier to **extend, test and maintain**.
Acronym coined by Robert C. Martin (Uncle Bob).

The Factory Method examples in `src/factory/` mostly demonstrate **OCP** and **DIP**;
the Singleton notes are in [singleton.md](singleton.md).

---

## S — Single Responsibility Principle

> A class should have **one, and only one, reason to change**.

Bad: a class that loads config, writes logs and talks to the database — three unrelated
reasons to rewrite it.

Good (this repo):

- `factory.payment_processor.PaymentFactory` — only creates payments.
- `factory.payment_processor.CreditCardPayment` — only processes one payment method.

Splitting by responsibility keeps changes local: changing payment rules never touches
notification or document code.

## O — Open/Closed Principle

> Software entities should be **open for extension, closed for modification**.

Adding a feature = new code, not edits inside existing, working code.

Good (this repo):

- `factory.document.DocumentFactory` switches on `DocumentType`; adding a new document kind
  means a new enum constant + a new `Document` implementation, existing branches stay intact.
- `factory.notification.NotificationFactory` is **open without modifying the factory at all**:
  `registerNotification(name, creator)` registers new creators at runtime.

Bad: editing a 200-line `if/else` chain every time a fourth payment provider appears — and
risking the three working ones.

## L — Liskov Substitution Principle

> Subtypes must be usable wherever the base type is expected, without breaking the program.

If `Shape` promises `area() > 0`, `Square.area()` must honour that; a subtype that throws
"not supported" for a base-class method violates LSP.

In this repo every `Payment` implementation (`CreditCardPayment`, `PaymePayment`,
`ClickPayment`) supports the same operations, so `PaymentFactory.createPayment(...)` callers
can treat all of them interchangeably.

Classic violation: `Square extends Rectangle` where `setWidth()` also changes height —
code written against `Rectangle` misbehaves with a `Square`.

## I — Interface Segregation Principle

> Clients should not be forced to depend on methods they do not use.

Prefer several small, focused interfaces over one fat "god interface".

In this repo the interface is kept small and matched to each consumer:

- `factory.notification.Notification` — send only.
- `factory.shape.Shape` — geometry only.
- `factory.document.Document` — document only.

Splitting interfaces means implementing a new type requires implementing *relevant* behaviour,
and callers can't accidentally invoke something meaningless for them.

## D — Dependency Inversion Principle

> High-level modules should not depend on low-level modules. Both should depend on
> **abstractions**. Abstractions should not depend on details — details depend on abstractions.

The client depends on the abstract product, never on a concrete class:

```java
Payment p = PaymentFactory.createPayment("payme");   // client knows `Payment`, not `PaymePayment`
```

The same inversion powers the registry factory:

```java
NotificationFactory.registerNotification("email", (to, msg, cfg, opts) -> new EmailNotification(...));
Notification n = NotificationFactory.createNotification("email", recipient, message, null);
```

Swapping `EmailNotification` for `PushNotification` needs **no change** in the calling code —
that is the whole point of the pattern family: *create behind an abstraction, use through the
abstraction*.

---

## Quick checklist

| Principle | Question to ask in code review |
|---|---|
| SRP | What is this class's one reason to change? |
| OCP | Does adding a feature mean editing existing methods? |
| LSP | Can any subtype replace another without surprising callers? |
| ISP | Are there methods some implementers always throw on? |
| DIP | Does high-level code `new` concrete classes directly? |

## Further reading

- Robert C. Martin, *Clean Architecture*
- Robert C. Martin, *Agile Software Development: Principles, Patterns, and Practices*
- Martin Fowler, *Refactoring* — smells that lead to these violations