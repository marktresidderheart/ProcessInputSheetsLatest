import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class PropertiesFile {

    private static String fileName;
    private static String fileLocation;

    public PropertiesFile() {

    }

    public PropertiesFile(String fileLocation, String fileName) {
        this.fileName = fileName;
        this.fileLocation = fileLocation;
    }

    public PropertyValues LoadPropertyValues() {

        String propertiesFile = fileLocation+fileName;

        CSVReader reader;

        PropertyValues propertyValues = new PropertyValues();

        try {
            //Get the CSVReader instance with specifying the delimiter to be used
            reader = new CSVReader(new FileReader(propertiesFile), ',');
            String[] nextLine;
            String[] propertyValue;

            //Read one line at a time
            while ((nextLine = reader.readNext()) != null) {

                switch (nextLine[0]) {
                    case "[INPUT SHEET FILE PROCESSING LOCATION]":
                        propertyValue = reader.readNext();
                        propertyValues.setInputSheetFileProcessingLocation(propertyValue[0]);
                        break;
                    case "[INPUT SHEET FILE INVALID FILE TYPE LOCATION]":
                        propertyValue = reader.readNext();
                        propertyValues.setInputSheetInvalidFileTypeLocation(propertyValue[0]);
                        break;
                    case "[INPUT SHEET FILE PROCESSED LOCATION]":
                        propertyValue = reader.readNext();
                        propertyValues.setInputSheetProcessedLocation(propertyValue[0]);
                        break;
                    case "[PRISM OUTPUT FILE LOCATION]":
                        propertyValue = reader.readNext();
                        propertyValues.setPrismOutputFileLocation(propertyValue[0]);
                        break;
                    case "[AUDIT FILE LOCATION]":
                        propertyValue = reader.readNext();
                        propertyValues.setAuditFileLocation(propertyValue[0]);
                        break;
                    case "[INCENTIVE PLAN MATRIX FILE LOCATION AND NAME]":
                        propertyValue = reader.readNext();
                        propertyValues.setIncentivePlanMatrixFileLocationAndName(propertyValue[0]);
                        break;
                    case "[GOAL DEFINITIONS FILE LOCATION AND NAME]":
                        propertyValue = reader.readNext();
                        propertyValues.setGoalDefinitionsFileLocationAndName(propertyValue[0]);
                        break;
                    case "[INCENTIVE PLAN CATEGORIES FILE LOCATION AND NAME]":
                        propertyValue = reader.readNext();
                        propertyValues.setIncentivePlanCategoriesFileLocationAndName(propertyValue[0]);
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return propertyValues;
    }
}


