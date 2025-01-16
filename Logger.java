import java.util.*;

public abstract class Logger {
    public static final int INFO = 1;
    public static final int DEBUG = 2;
    public static final int ERROR = 3;

    Logger logger;
    public Logger (Logger logger) {
        this.logger = logger;
    }
    public void log(int logLevel, String message) {
        if (logger!=null) {
            logger.log(logLevel, message);
        }
    }
}

public class InfoLogger extends Logger {
    public InfoLogger(Logger logger) {
        super(logger);
    }

    public void log(int logLevel, String message) {
        if (logLevel==Logger.INFO) {
            // sys out
        } else {
            super.log(logLevel, message);
        }
    }
}

public class DebugLogger extends Logger {
    public DebugLogger(Logger logger) {
        super(logger);
    }

    public void log(int logLevel, String message) {
        if (logLevel==Logger.DEBUG) {
            // sys out
        } else {
            super.log(logLevel, message);
        }
    }
}

public class ErrorLogger extends Logger {
    public ErrorLogger(Logger logger) {
        super(logger);
    }

    public void log(int logLevel, String message) {
        if (logLevel==Logger.ERROR) {
            // sys out
        } else {
            super.log(logLevel, message);
        }
    }
}

public class Main {

    public static void main(String args[]) {

        Logger logObject = new InfoLogger(new DebugLogger(new ErrorLogger(null)));

        logObject.log(Loggor.ERROR, "exception happens");
        logObject.log(Loggor.DEBUG, "need to debug this ");
        logObject.log(Loggor.INFO, "just for info ");

    }
}