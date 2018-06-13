import org.apache.logging.log4j.LogManager;

import java.util.LinkedList;

public class CopiesLogger {

    private static final org.apache.logging.log4j.Logger checkSumLogger = LogManager.getLogger(CopiesLogger.class);
    private LinkedList<String> copies;

    public CopiesLogger() {
        copies = new LinkedList<>();
    }

    public void addCopy(String copy) {
        copies.add(copy);
    }

    public void getCopiesLog() {
        // Дубликаты:
        for (String s : copies) {
            checkSumLogger.info(s);
        }
    }
}
