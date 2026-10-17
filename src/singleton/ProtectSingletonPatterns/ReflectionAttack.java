package singleton.ProtectSingletonPatterns;

import java.lang.reflect.Constructor;

public class ReflectionAttack {
    public static void main(String[] args) throws Exception {
        AppSecurityManager first = AppSecurityManager.getInstance();

        Constructor<AppSecurityManager> constructor = AppSecurityManager.class.getDeclaredConstructor();

        constructor.setAccessible(true);

        AppSecurityManager second = constructor.newInstance();

        System.out.println(first == second);
    }
}
