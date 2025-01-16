public class Logger {
    private static volatile Logger logger;
    private static volatile AbstractLogger abstractLogger;
    private Logger() {
        if (logger != null) {
            throw new IllegalStateException("Object already created"); 
        }
    }
    public static Logger getLogger() {
        if (logger == null) {
            synchronized (Logger.class) {
                if (logger == null) {
                    logger = new Logger();
                    AbstractLogger = LogManager.getChainOfLogs();
                }
            }
        }
        return logger;
    }
    private void createLog(int logLevel, String message) {
        abstractLogger.logMessage(logLevel, message);
    } 
    public void info(String message) {
        createLog(1, message);
    }
    public void error(String message) {
        createLog(2, message);
    }
    public void debug(String message) {
        createLog(3, message);
    }
}
public abstract class AbstractLogger {
    public int level;
    private AbstractLogger abstractLogger;

    public void setNextLogger(AbstractLogger abstractLogger) {
        this.abstractLogger = abstractLogger;
    }

    public void logMessage(int logLevel, String message) {
        if (this.level == logLevel) {
            display(message);
        }
        if (this.abstractLogger != null) {
            abstractLogger.logMessage(logLevel, message);
        }
    }
    protected abstract void display(String message);
}
public class InfoLogger extends AbstractLogger {
    protected InfoLogger(int level) {
        this.level = level;
    }
    @override
    protected void display(String message) {
        System.out.println("INFO: " + message);
    }
}
public class DebugLogger extends AbstractLogger {
    protected DebugLogger(int level) {
        this.level = level;
    }
    @override
    protected void display(String message) {
        System.out.println("INFO: " + message);
    }
}
public class ErrorLogger extends AbstractLogger {
    protected ErrorLogger(int level) {
        this.level = level;
    }
    @override
    protected void display(String message) {
        System.out.println("INFO: " + message);
    }
}
class LogManager {
    static AbstractLogger doChaining(){
       AbstractLogger infoLogger = new InfoLogger(1);

       AbstractLogger errorLogger = new ErrorLogger(2);
       infoLogger.setNextLevelLogger(errorLogger);

       AbstractLogger debugLogger = new DebugLogger(3);
       errorLogger.setNextLevelLogger(debugLogger);

       return infoLogger;
   }
}