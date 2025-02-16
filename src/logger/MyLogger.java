package logger;

public abstract class MyLogger {
    private static MyLogger instance;

    // Singleton instance method
    public static synchronized MyLogger getInstance(String type) {
        if (instance == null) {
            instance = createLogger(type);  // Delegate to concrete subclass
        }
        return instance;
    }

    // Abstract method to be implemented by concrete subclasses
    protected abstract void log(LogLevel level, String message);

    // Enum for log levels
    public enum LogLevel {
        INFO, DEBUG, ERROR, WARN
    }

    // Convenience methods for different log levels
    public void logInfo(String message) {
        log(LogLevel.INFO, message);
    }

    public void logDebug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void logError(String message) {
        log(LogLevel.ERROR, message);
    }

    public void logWarn(String message) {
        log(LogLevel.WARN, message);
    }

    // Factory method to allow easy extension with different logging types
    protected static MyLogger createLogger(String type) {
        if (type.equals("console")) {
            return new ConsoleLogger();
        } else if (type.equals("file")) {
            return new FileLogger();
        } else {
            throw new IllegalArgumentException("Invalid logger type.");
        }
    }
}
