package logger;

import java.time.LocalDateTime;

public class Logger{
    private static Logger _logger;

    private Logger(){
        this.info("Logger", "new instance of Logger is created");
    }

    public void info(String classname, String msg){
        System.out.println("INFO " + classname + " <-- " + msg + " " + LocalDateTime.now());
    }

    public void warning(String classname, String msg){
        System.out.println("WARNING " + classname + " <-- " + msg + " " + LocalDateTime.now());
    }

    public static Logger instance(){
        if (_logger == null){
            _logger = new Logger();
        }
        return _logger;
    }

}