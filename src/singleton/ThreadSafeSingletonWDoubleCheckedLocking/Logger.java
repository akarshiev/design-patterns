package singleton.ThreadSafeSingletonWDoubleCheckedLocking;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static volatile Logger instance;

    private static File logFile;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private Logger() {
        this.logFile = new File("app.log");
        initializeLogFile();
    }

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }

        return instance;
    }

    private static void initializeLogFile() {
        try {
            if (logFile.createNewFile()) {
                System.out.println("Log file created " + logFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logEntry = "[" + timestamp + "] " + "[" + level + " ] " + message;

        try (BufferedWriter write = new BufferedWriter(new FileWriter(logFile, true))) {

            write.write(logEntry);
            write.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
