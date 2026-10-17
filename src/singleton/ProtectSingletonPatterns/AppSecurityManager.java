package singleton.ProtectSingletonPatterns;

public class AppSecurityManager implements java.io.Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private static volatile AppSecurityManager instance;

    private int loginAttempts;

    private AppSecurityManager() {
        if (instance != null) {
            throw new IllegalStateException("Singleton instance already exists!");
        }

        loginAttempts = 0;
    }

    public static AppSecurityManager getInstance() {
        if (instance == null) {
            synchronized (AppSecurityManager.class) {
                if (instance == null) {
                    instance = new AppSecurityManager();
                }
            }
        }

        return instance;
    }

    public void login() {
        loginAttempts++;
        System.out.println("Login attempt: " + loginAttempts);
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    // Serialization orqali yangi instance yaratilishidan himoya
    private Object readResolve() {
        return getInstance();
    }

    // Cloning orqali yangi instance yaratilishidan himoya
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton cannot be cloned!");
    }
}
