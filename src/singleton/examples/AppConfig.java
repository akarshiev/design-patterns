package singleton.examples;

public class AppConfig {
    private static AppConfig instance;

    private AppConfig() {
        System.out.println("AppConfig object created");
    }

    public static synchronized AppConfig getInstance() { // this isn't thread safety. so added the code synchronized for
        if (instance == null) {
            instance = new AppConfig();
        }

        return instance;
    }
}
