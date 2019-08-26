import java.io.*;
import com.opencsv.CSVWriter;

public class AuditFile {

    private static String fileName;
    private static String fileLocation;
    private static File outputAuditFile;
    private static FileWriter outputWriter;
    private static CSVWriter csvWriter;

    public AuditFile() {

    }

    public AuditFile(String fileLocation, String fileName) {
        this.fileName = fileName;
        this.fileLocation = fileLocation;
    }

    public void createAuditFile() throws IOException {
        outputAuditFile = new File(fileLocation+fileName);
        outputWriter = new FileWriter(outputAuditFile);
        csvWriter = new CSVWriter(outputWriter);

        String[] header = { "Action", "Field", "Value" };
        csvWriter.writeNext(header);
    }

    public static void writeRow(String action, String field, String value) throws IOException {
        String[] rowDetails = {action,field,value};
        csvWriter.writeNext(rowDetails);
    }

    public static void saveAuditFile() throws IOException {
        csvWriter.close();
    }

    public static String getFileName() {
        return fileName;
    }

    public static void setFileName(String fileName) {
        AuditFile.fileName = fileName;
    }

    public static String getFileLocation() {
        return fileLocation;
    }

    public static void setFileLocation(String fileLocation) {
        AuditFile.fileLocation = fileLocation;
    }
}
