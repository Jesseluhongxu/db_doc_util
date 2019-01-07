package com.gxy.db.excpetion;

/**
 * @author guoxingyong
 * @since 2019/1/7 9:28
 */
public class ExportDbStructureExcpetion extends RuntimeException{

    public ExportDbStructureExcpetion() {
    }

    public ExportDbStructureExcpetion(String message) {
        super(message);
    }

    public ExportDbStructureExcpetion(String message, Throwable cause) {
        super(message, cause);
    }

    public ExportDbStructureExcpetion(Throwable cause) {
        super(cause);
    }
}
