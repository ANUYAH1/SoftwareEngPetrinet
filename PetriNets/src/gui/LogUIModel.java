package gui;

public class LogUIModel {
    private LogType type;
    private String message;

    public LogUIModel(LogType type, String message) {
        this.type = type;
        this.message = message;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString(){
        return (this.type == LogType.ERROR? "Error": this.type
                == LogType.INFORMATION ? "Info":"") + ": "+this.message;
    }
    // Create Log Factories
    public static LogUIModel createInfoLog (String message){
        return  new LogUIModel(LogType.INFORMATION,message);
    }
    public static LogUIModel createErrorLog (String message){
        return  new LogUIModel(LogType.ERROR,message);
    }
}
