package com.rishabhtech.dp.creational.prototype;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ============================= PROTOTYPE DESIGN PATTERN =============================
 *
 * Category: Creational
 *
 * Intent:
 *   Create new objects by copying (cloning) an existing object (the prototype)
 *   instead of creating from scratch using the `new` keyword.
 *
 * When to use:
 *   - Object creation is expensive (e.g., DB calls, network calls, heavy computation).
 *   - You need many objects with similar state and only slight variations.
 *   - You want to avoid subclasses of an object creator (contrast with Factory pattern).
 *   - The class to instantiate is specified at runtime.
 *
 * Key Participants:
 *   1. Prototype (interface)  – declares the clone() method.
 *   2. ConcretePrototype      – implements clone() to copy itself.
 *   3. Client                 – creates a new object by asking a prototype to clone itself.
 *
 * Shallow vs Deep Copy:
 *   - Shallow copy: copies primitive fields; reference fields still point to same objects.
 *   - Deep copy: recursively copies all referenced objects too (use when object graph is mutable).
 *
 * Pros:
 *   - Avoids costly initialization; clones are cheaper than fresh creation.
 *   - Hides complexity of creating new instances from the client.
 *   - Allows adding/removing objects at runtime.
 *
 * Cons:
 *   - Cloning complex objects with circular references can be tricky.
 *   - Each subclass must implement clone(), which can be burdensome.
 *
 * Real-world examples:
 *   - java.lang.Object#clone() (Java's built-in prototype support)
 *   - Copying a configured Spring Bean definition
 *   - Game development: cloning enemy/unit templates with preset stats
 *
 * ===================== INTERVIEW: Can we use Cloneable? =====================
 *
 * Q: Can we use Java's Cloneable interface for Prototype pattern?
 * A: Yes, but it's NOT recommended. Here's why:
 *
 *   Custom Prototype (this file)             vs   Java's Cloneable
 *   ─────────────────────────────────────────────────────────────────
 *   You define clone() in your interface      |   clone() is in Object (protected)
 *   Full control over copy logic              |   super.clone() does shallow copy, you fix the rest
 *   No exceptions to handle                   |   Must handle CloneNotSupportedException
 *   Clean, type-safe                          |   Returns Object, needs casting
 *   Follows Open/Closed principle             |   Bypasses constructor — can break invariants
 *
 * Q: Then why does Cloneable exist?
 * A: It's Java's built-in prototype support from JDK 1.0. super.clone() uses
 *    native code to shallow-copy all fields automatically, so you don't have to
 *    write `new Xyz(this.a, this.b, ...)` for every field. Useful when class has
 *    50+ fields — you just deep-copy the mutable ones.
 *
 * Q: What's the best approach?
 * A: Prefer custom Prototype interface (or copy constructors) for new code.
 *    Use Cloneable only when working with legacy code that already depends on it.
 *    Joshua Bloch (Effective Java, Item 13): "Cloneable is broken. Avoid it."
 *
 * =====================================================================================
 */
public interface Prototype {
    Prototype clone();
}

/**
 * ConcretePrototype – Student implements the Prototype interface
 * and provides its own cloning logic (shallow copy of all fields).
 */
@AllArgsConstructor
class Student implements Prototype {

    int age;
    private int rollNumber;
    String name;

    /**
     * Creates a shallow copy of this Student.
     * Since all fields here are primitives or immutable (String),
     * a shallow copy is sufficient and behaves like a deep copy.
     */
    @Override
    public Prototype clone() {
        return new Student(age, rollNumber, name);
    }

    // Main Method – demonstrates cloning a Student object
    static void main() {
        // Original object created normally (could be expensive in real scenarios)
        Student obj = new Student(20, 75, "Ram");

        // Clone: new object with same state, without calling constructor logic again
        Student obj2 = (Student) obj.clone();
    }
}

/**
 * ============================= DEEP COPY EXAMPLE =============================
 *
 * Employee has a mutable reference field (List<String> skills).
 * A shallow copy would share the same List between original and clone,
 * so modifying one would affect the other — which is usually a bug.
 *
 * Deep copy creates a NEW List with the same elements, so both objects
 * are fully independent after cloning.
 */
@AllArgsConstructor
@ToString
class Employee implements Prototype {

    private String name;
    private int id;
    private List<String> skills;  // Mutable reference field — needs deep copy

    /**
     * DEEP COPY: creates new copies of all mutable reference fields.
     * Primitives and immutable objects (String) are copied by value automatically.
     */
    @Override
    public Prototype clone() {
        // Deep copy the skills list so clone has its own independent copy
        List<String> clonedSkills = new ArrayList<>(this.skills);
        return new Employee(this.name, this.id, clonedSkills);
    }

    // Demonstrates why deep copy matters
    static void main() {
        Employee original = new Employee("Rishabh", 101, Arrays.asList("Java", "Spring"));


        Employee cloned = (Employee) original.clone();

        // Modify the clone's skills
        cloned.skills.add("Docker");

        // Original is NOT affected — proof that deep copy works correctly
        System.out.println("Original: " + original);  // skills = [Java, Spring]
        System.out.println("Cloned:   " + cloned);    // skills = [Java, Spring, Docker]
    }
}

/**
 * ============================= DEEP COPY WITH NESTED OBJECT (Address) =============================
 *
 * Person has a mutable reference to an Address object.
 * This is a more realistic deep copy scenario — an object inside another object.
 *
 * Shallow copy problem:
 *   Person original = new Person("Ram", address);
 *   Person clone = shallowCopy(original);
 *   clone.address.city = "Mumbai";    // This ALSO changes original's city! (same reference)
 *
 * Deep copy fix:
 *   Clone the Address object too, so both Person objects have independent Address instances.
 */
@AllArgsConstructor
@ToString
class Address {
    String city;
    String state;
    int pinCode;

    // Deep copy of Address — returns a completely new Address object
    Address deepCopy() {
        return new Address(this.city, this.state, this.pinCode);
    }
}

@AllArgsConstructor
@ToString
class Person implements Prototype {

    private String name;
    private int age;
    private Address address;  // Mutable nested object — MUST be deep copied

    /**
     * DEEP COPY: copies primitives/Strings by value,
     * and creates a NEW Address object for the clone.
     * Without address.deepCopy(), both original and clone would share the same Address.
     */
    @Override
    public Prototype clone() {
        return new Person(this.name, this.age, this.address.deepCopy());
    }

    static void main() {
        Address address = new Address("Delhi", "Delhi", 110001);
        Person original = new Person("Ram", 25, address);
        Person cloned = (Person) original.clone();

        // Modify the clone's address
        cloned.address.city = "Mumbai";
        cloned.address.state = "Maharashtra";
        cloned.address.pinCode = 400001;

        // Original is NOT affected — proof that nested object was deep copied
        System.out.println("Original: " + original);  // city=Delhi
        System.out.println("Cloned:   " + cloned);    // city=Mumbai
    }
}

/**
 * ============================= USING JAVA's CLONEABLE INTERFACE =============================
 *
 * Java provides a built-in cloning mechanism:
 *   1. Implement the marker interface `Cloneable`
 *   2. Override `Object.clone()` and call `super.clone()`
 *
 * How super.clone() works:
 *   - The JVM creates a new object and copies ALL fields (shallow copy) automatically.
 *   - You don't need to manually copy each primitive/field — the JVM does it natively.
 *   - But for mutable reference fields, you STILL need to deep copy them yourself.
 *
 * Why Cloneable is considered flawed (Effective Java, Item 13):
 *   - It's a marker interface with no clone() method — confusing contract.
 *   - Object.clone() is protected, so you must override and make it public.
 *   - Throws checked CloneNotSupportedException — awkward to handle.
 *   - Bypasses constructors — can break class invariants.
 *
 * Despite its flaws, it's still widely used in legacy codebases and interviews.
 * Your custom Prototype interface is the modern/cleaner alternative.
 *
 * ==========================================================================================
 */
@AllArgsConstructor
@ToString
class Department implements Cloneable {

    private String name;
    private int code;
    private List<String> employees;  // Mutable — needs deep copy

    /**
     * Uses Java's built-in clone mechanism.
     * super.clone() handles all primitive + reference copying (shallow).
     * We then manually deep-copy the mutable List field.
     */

    @Override
    public Department clone() {
        try {
            Department cloned = (Department) super.clone();  // JVM shallow copies all fields
            cloned.employees = new ArrayList<>(this.employees);  // Deep copy mutable field
            return cloned;
        } catch (CloneNotSupportedException e) {
            // Won't happen since we implement Cloneable, but Java forces us to handle it
            throw new RuntimeException(e);
        }
    }

    static void main() {
        Department original = new Department("Engineering", 42, new ArrayList<>(Arrays.asList("Alice", "Bob")));
        Department cloned = original.clone();  // No casting needed — overridden return type

        cloned.employees.add("Charlie");

        System.out.println("Original: " + original);  // employees = [Alice, Bob]
        System.out.println("Cloned:   " + cloned);    // employees = [Alice, Bob, Charlie]
    }
}
