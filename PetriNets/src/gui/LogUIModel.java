package gui;

/**
 * This class is responsible
 * for the logging section
 * of the UI
 */
public class LogUIModel {
    private LogType type;
    private String message;

    public LogUIModel(LogType type, String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * returns the
     * log type
     * @return
     */
    public LogType getType() {
        return type;
    }

    /**
     * Sets the log type
     * @param type
     */
    public void setType(LogType type) {
        this.type = type;
    }

    /**
     * Gets the log message
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * sets the log message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Prints formatted log text
     * @return
     */
    @Override
    public String toString(){
        return (this.type == LogType.ERROR? "Error": this.type
                == LogType.INFORMATION ? "Info":"") + ": "+this.message;
    }
    // Create Log Factories

    /**
     * createInfoLog is a factory tool
     * that creats an Information automatically
     *
     * @param message the message component of the log
     * @return
     */
    public static LogUIModel createInfoLog (String message){
        return  new LogUIModel(LogType.INFORMATION,message);
    }

    /**
     * This factory tool automatically creates an
     * error log from the message parameter
     * @param message message component of the log
     * @return
     */
    public static LogUIModel createErrorLog (String message){
        return  new LogUIModel(LogType.ERROR,message);
    }
}
