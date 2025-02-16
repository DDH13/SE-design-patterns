package logger;

public class ConsoleLogger extends MyLogger {
    @Override
    protected void log(LogLevel level, String message) {
        System.out.println("[" + level.name() + "] " + message);
    }
    
}
