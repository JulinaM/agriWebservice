package com.julina.agri.common;

/**
 * Created by julina on 8/26/14.
 */
public interface AgriException {
    public class NullPointerException extends Exception{
        public NullPointerException(String message){
            super(message);
        }
        public NullPointerException(int message){
            super(String.valueOf(message));
        }
        public NullPointerException(String message, Throwable throwable){
            super(message, throwable);
        }
    }
    public class DataModelException extends Exception{
        public DataModelException(String message){
            super(message);
        }
        public DataModelException(int message){
            super(String.valueOf(message));
        }
        public DataModelException(String message, Throwable throwable){
            super(message, throwable);
        }
    }


}
