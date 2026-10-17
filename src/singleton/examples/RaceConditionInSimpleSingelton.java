package singleton.examples;

public class RaceConditionInSimpleSingelton {
    private static RaceConditionInSimpleSingelton instance;

    private RaceConditionInSimpleSingelton() {
        System.out.println("Instance created: " + Thread.currentThread().getName());
    }

    public static RaceConditionInSimpleSingelton getInstance() {
        if (instance == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }

            instance = new RaceConditionInSimpleSingelton();
        }

        return instance;
    }
}
