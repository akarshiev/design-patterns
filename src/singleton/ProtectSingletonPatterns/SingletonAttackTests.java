package singleton.ProtectSingletonPatterns;

import singleton.ProtectSingletonPatterns.AppSecurityManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonAttackTests {

    public static void testReflectionBreak() {
        System.out.println("\n--- Reflection Attack ---");

        try {
            AppSecurityManager original = AppSecurityManager.getInstance();

            Constructor<AppSecurityManager> constructor = AppSecurityManager.class.getDeclaredConstructor();

            constructor.setAccessible(true);

            AppSecurityManager another = constructor.newInstance();

            System.out.println("Original: " + original);
            System.out.println("Another:  " + another);
            System.out.println("Same instance: " + (original == another));

        } catch (InvocationTargetException e) {
            System.out.println("Reflection blocked: " + e.getCause());
        } catch (ReflectiveOperationException e) {
            System.out.println("Reflection failed: " + e.getMessage());
        }
    }

    public static void testSerializationBreak() {
        System.out.println("\n--- Serialization Attack ---");

        try {
            AppSecurityManager original = AppSecurityManager.getInstance();

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

            try (ObjectOutputStream out = new ObjectOutputStream(byteStream)) {
                out.writeObject(original);
            }

            AppSecurityManager restored;

            try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(byteStream.toByteArray()))) {
                restored = (AppSecurityManager) in.readObject();
            }

            System.out.println("Original: " + original);
            System.out.println("Restored: " + restored);
            System.out.println("Same instance: " + (original == restored));

        } catch (Exception e) {
            System.out.println("Serialization test failed: " + e.getMessage());
        }
    }

    public static void testCloneBreak() {
        System.out.println("\n--- Clone Attack ---");

        try {
            AppSecurityManager original = AppSecurityManager.getInstance();

            AppSecurityManager another = (AppSecurityManager) original.clone();

            System.out.println("Original: " + original);
            System.out.println("Another:  " + another);
            System.out.println("Same instance: " + (original == another));

        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning blocked: " + e.getMessage());
        }
    }
}
