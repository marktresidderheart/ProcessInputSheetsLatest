import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.DateFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class Main {

    private static ArrayList<IncentiveWorksheetOutput> errorEmployees = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        //LOAD THE PROPERTIES FILE
        PropertiesFile propFile = new PropertiesFile(System.getProperty("user.dir")+"/","InputSheetProcessingEngine.properties");
        PropertyValues propValues = propFile.LoadPropertyValues();

        //CREATION OF THE AUDIT FILE
        DateTimeFormatter timeStampPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        AuditFile auditFile = new AuditFile(propValues.getAuditFileLocation(),"ProcessInputSheets-"+timeStampPattern.format(java.time.LocalDateTime.now())+".csv");
        auditFile.createAuditFile();

        //LOAD THE PLAN GOAL MATRIX CONTROL TABLE
        ArrayList<PlanGoalMatrix> planGoalMatrices = new ArrayList<>();
        planGoalMatrices = ImportPlanGoalMatrix(propValues.getIncentivePlanMatrixFileLocationAndName());
        auditFile.writeRow("Loading Control Table","Loading Plan Goal Control Table rows",Integer.toString(planGoalMatrices.size()));

        //LOAD THE GOAL DEFINITIONS
        ArrayList<GoalDefinition> goalDefinitions = new ArrayList<>();
        goalDefinitions = ImportGoalDefinitions(propValues.getGoalDefinitionsFileLocationAndName());
        auditFile.writeRow("Loading Control Table","Loading Goal Definition rows",Integer.toString(goalDefinitions.size()));

        //LOAD INCENTIVE PLAN CATEGORIES
        ArrayList<IncentivePlanCategory> incentivePlanCategories = new ArrayList<>();
        incentivePlanCategories = ImportIncentivePlanCategories(propValues.getIncentivePlanCategoriesFileLocationAndName());
        auditFile.writeRow("Loading Incentive Plan Categories Control Table","Loading Incentive Plang Category rows",Integer.toString(incentivePlanCategories.size()));

        //LOAD INPUT SHEET ROWS FOR PROCESSING
        Path newInputSheet = Paths.get(propValues.getInputSheetFileProcessingLocation());
        WatchService watchService = FileSystems.getDefault().newWatchService();
        newInputSheet.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        ArrayList<InputSheet> inputSheetEmployees = new ArrayList<>();

        try {
            boolean valid = true;
            do {
                WatchKey watchKey = watchService.take();

                for (WatchEvent event : watchKey.pollEvents()) {
                    WatchEvent.Kind kind = event.kind();
                    if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {

                        String newInputSheetToProcess = event.context().toString();
                        System.out.println(newInputSheetToProcess);

                        TimeUnit.SECONDS.sleep(2);
                        System.out.println("processing file:"+newInputSheetToProcess);

                        String newInputSheetToProcessExtension = newInputSheetToProcess.substring(newInputSheetToProcess.indexOf("."));

                        System.out.println("file to process extension:"+newInputSheetToProcessExtension);

                        if (newInputSheetToProcessExtension.equals(".xlsx")) {

                            inputSheetEmployees = importInputSheet(propValues.getInputSheetFileProcessingLocation() + newInputSheetToProcess);
                            auditFile.writeRow("Loading Input Sheet", "Loading Input Sheet Rows", Integer.toString(inputSheetEmployees.size()));

                            ArrayList<IncentiveWorksheetOutput> incentiveWorksheetOutputs = new ArrayList<>();
                            incentiveWorksheetOutputs = ProcessWorkSheetOutput(planGoalMatrices, goalDefinitions, incentivePlanCategories, inputSheetEmployees);

                            //CREATION OF THE PRISM OUTPUT FILE
                            IncentivePlanPrismCSVOutput prismOutputFile = new IncentivePlanPrismCSVOutput(propValues.getPrismOutputFileLocation(), "IncentivePrismOutputFile " +
                                    timeStampPattern.format(java.time.LocalDateTime.now()) + " - " + newInputSheetToProcess.substring(0, newInputSheetToProcess.lastIndexOf('.')) + ".csv");
                            prismOutputFile.createIncentivePrismOutputFile();
                            prismOutputFile.WriteIncentiveOutputFile(incentiveWorksheetOutputs);
                            prismOutputFile.saveIncentiveOutputFile();

                            File moveFileToProcessedFolder = new File(propValues.getInputSheetFileProcessingLocation() + newInputSheetToProcess);

                            // renaming the file and moving it to a new location
                            if (moveFileToProcessedFolder.renameTo
                                    (new File(propValues.getInputSheetProcessedLocation() + "Processing Complete - " + timeStampPattern.format(java.time.LocalDateTime.now()) + " - " + newInputSheetToProcess))) {
                                // if file copied successfully then delete the original file
                                moveFileToProcessedFolder.delete();
                                System.out.println("File moved successfully");
                            } else {
                                System.out.println("Failed to move the file");
                            }
                        } else {
                            auditFile.writeRow("Processing new Input Sheet", "Could not process the file.  Incorrect File Type", newInputSheetToProcess);
                            System.out.println("Incorrect file type");

                            File moveIncorrectFileTypeInputSheet = new File(propValues.getInputSheetFileProcessingLocation() + newInputSheetToProcess);

                            if (moveIncorrectFileTypeInputSheet.renameTo
                                    (new File(propValues.getInputSheetFileProcessingLocation() + "Invalid File Type - " + timeStampPattern.format(java.time.LocalDateTime.now()) + " - " + newInputSheetToProcess))) {
                                // if file copied successfully then delete the original file
                                moveIncorrectFileTypeInputSheet.delete();
                                System.out.println("File moved successfully");
                            } else {
                                System.out.println("Failed to move the file");
                            }
                        }
                    }
                }
                valid = watchKey.reset();

            } while (valid);

        } catch (InterruptedException io) {
            System.out.println("IO Exception"+io.toString());
        }

        auditFile.saveAuditFile();
    }

    public static ArrayList<PlanGoalMatrix> ImportPlanGoalMatrix(String fileLocation){

        long countOfLines=0;
        try {
            countOfLines = Files.lines(Paths.get(new File(fileLocation).getPath()), Charset.forName("ISO_8859_1")).count();
            System.out.println(String.format("There are %s lines in the plan goal matrix import file", countOfLines));
        } catch (IOException e) {
            System.out.println("No File Found");
        }

        CSVReader reader = null;
        ArrayList<PlanGoalMatrix> planGoalMatrices = new ArrayList<>();

        try {
            //Get the CSVReader instance with specifying the delimiter to be used
            reader = new CSVReader(new FileReader(fileLocation),',');
            String [] nextLine;

            String planName, planCategory, goalName;
            int fiscalYear = 0;
            float max = 0f;
            float target = 0f;
            float min = 0f;
            float goalWeight = 0f;
            float flatAmount = 0f;

            boolean headerRow = true;

            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
                if (!headerRow) {
                    fiscalYear = Integer.parseInt(nextLine[0].substring(0,4));
                    planName = nextLine[1];
                    planCategory = nextLine[2];
                    if (nextLine[3] != null && !nextLine[3].isEmpty()) {
                        max = Float.parseFloat(nextLine[3]);
                    }
                    if (nextLine[4] != null && !nextLine[4].isEmpty()) {
                        target = Float.parseFloat(nextLine[4]);
                    }
                    if (nextLine[5] != null && !nextLine[5].isEmpty()) {
                        min = Float.parseFloat(nextLine[5]);
                    }
                    goalName = nextLine[6];
                    if (nextLine[7] != null && !nextLine[7].isEmpty()) {
                        goalWeight = Float.parseFloat(nextLine[7]);
                    }
                    if (nextLine[8] != null && !nextLine[8].isEmpty()) {
                        flatAmount = Float.parseFloat(nextLine[8]);
                    }

                    PlanGoalMatrix planGoalMatrix = new PlanGoalMatrix(fiscalYear,planName,planCategory,max,target,min,goalName,goalWeight,flatAmount);

                    planGoalMatrices.add(planGoalMatrix);
                } else {
                    headerRow = false;
                }
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return  planGoalMatrices;
    }
    public static ArrayList<IncentivePlanCategory> ImportIncentivePlanCategories(String fileLocation){

        long countOfLines=0;
        try {
            countOfLines = Files.lines(Paths.get(new File(fileLocation).getPath()), Charset.forName("ISO_8859_1")).count();
            System.out.println(String.format("There are %s lines in the incentive plan category file", countOfLines));
        } catch (IOException e) {
            System.out.println("No File Found");
        }

        CSVReader reader = null;
        ArrayList<IncentivePlanCategory> incentivePlanCategories = new ArrayList<>();

        try {
            //Get the CSVReader instance with specifying the delimiter to be used
            reader = new CSVReader(new FileReader(fileLocation),',');
            String [] nextLine;

            String employeeID = "";
            String jobCode = "";
            String jobProfile = "";
            String incentivePlan = "";
            String incentivePlanCategory = "";

            boolean headerRow = true;

            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
                if (!headerRow) {
                    employeeID = nextLine[0];
                    jobCode = nextLine[4];
                    jobProfile = nextLine[5];
                    incentivePlanCategory = nextLine[6];
                    incentivePlan = nextLine[7];

                    IncentivePlanCategory incentivePlanCategoryItem = new IncentivePlanCategory(employeeID,jobCode,jobProfile,incentivePlan,incentivePlanCategory);

                    incentivePlanCategories.add(incentivePlanCategoryItem);
                } else {
                    headerRow = false;
                }
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return  incentivePlanCategories;
    }

    public static ArrayList<GoalDefinition> ImportGoalDefinitions(String fileLocation){

        long countOfLines=0;
        try {
            countOfLines = Files.lines(Paths.get(new File(fileLocation).getPath()), Charset.forName("ISO_8859_1")).count();
            System.out.println(String.format("There are %s lines in the goal column settings import file", countOfLines));
        } catch (IOException e) {
            System.out.println("No File Found");
        }

        CSVReader reader = null;
        ArrayList<GoalDefinition> goalDefinitions = new ArrayList<>();

        try {
            //Get the CSVReader instance with specifying the delimiter to be used
            reader = new CSVReader(new FileReader(fileLocation),',');
            String [] nextLine;

            String columnName, columnType, linkedGoalName, defaultValue, selectionFile,
                    regionException1, regionException2, regionException3, regionException4, regionException5;
            Integer columnOrder = 0;
            Integer columnGoalOrder = 0;
            boolean columnLocked = false;

            boolean headerRow = true;

            //Read one line at a time
            while ((nextLine = reader.readNext()) != null)
            {
                if (!headerRow) {
                    columnName = nextLine[0];
                    columnType = nextLine[1];
                    linkedGoalName = nextLine[2];
                    if (nextLine[3] != null && !nextLine[3].isEmpty()) {
                        columnOrder = Integer.parseInt(nextLine[3]);
                    }
                    if (nextLine[4] != null && !nextLine[4].isEmpty()) {
                        columnGoalOrder = Integer.parseInt(nextLine[4]);
                    }
                    if (nextLine[5] != null && !nextLine[5].isEmpty()) {
                        if (nextLine[5].equals("1")) {
                            columnLocked = true;
                        } else {
                            columnLocked = false;
                        }
                    }
                    defaultValue = nextLine[6];
                    selectionFile = nextLine[7];
                    regionException1 = nextLine[8];
                    regionException2 = nextLine[9];
                    regionException3 = nextLine[10];
                    regionException4 = nextLine[11];
                    regionException5 = nextLine[12];

                    GoalDefinition goalColumnDefinition = new GoalDefinition(columnName,columnType,linkedGoalName,columnOrder,columnGoalOrder,
                            columnLocked,defaultValue,selectionFile,regionException1,
                            regionException2,regionException3,regionException4,
                            regionException5);

                    goalDefinitions.add(goalColumnDefinition);
                } else {
                    headerRow = false;
                }
            }
            reader.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return  goalDefinitions;
    }

    public static ArrayList<InputSheet> importInputSheet(String fileName) {

        ArrayList<InputSheet> importSheetRows = new ArrayList<>();

        try {

            FileInputStream excelInputSheet = new FileInputStream(new File(fileName));
            Workbook workbook = new XSSFWorkbook(excelInputSheet);
            Sheet datatypeSheet = workbook.getSheet("Employee");
            Iterator<Row> iterator = datatypeSheet.iterator();

            ArrayList<InputSheetColumn> columnHeaders = new ArrayList<>();

            Row headerRow = iterator.next();
            Iterator<Cell> headerCell = headerRow.iterator();
            int columnNum = 0;

            while (headerCell.hasNext()) {
                Cell currentCell = headerCell.next();
                InputSheetColumn inputSheetColumn = new InputSheetColumn(currentCell.getStringCellValue(),columnNum);
                columnHeaders.add(inputSheetColumn);
                columnNum++;
            }

            while (iterator.hasNext()) {

                int columnNumber = 0;
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                InputSheet newImportSheetEmployeeRow = new InputSheet();

                while (cellIterator.hasNext()) {

                    String columnHeader = columnHeaders.get(columnNumber).getColumnName();
                    columnHeader = columnHeader.trim();

                    Cell currentCell = cellIterator.next();
                    String currentCellStringValue = "";


                    switch (columnHeader) {
                        case "Add-Replace":
                            newImportSheetEmployeeRow.setAddReplace(currentCell.getStringCellValue());
                            break;
                        case "Fiscal Year":
                            newImportSheetEmployeeRow.setFiscalYear(currentCell.getStringCellValue());
                            break;
                        case "Incentive Plan":
                            newImportSheetEmployeeRow.setIncentivePlan(currentCell.getStringCellValue());
                            break;
                        case "Employee Name":
                            newImportSheetEmployeeRow.setEmployeeName(currentCell.getStringCellValue());
                            break;
                        case "Job Profile":
                            newImportSheetEmployeeRow.setJobProfile(currentCell.getStringCellValue());
                            break;
                        case "Time In Job":
                            DataFormatter formatterTimeInJob = new DataFormatter();
                            String timeInJob = formatterTimeInJob.formatCellValue(currentCell);
                            Date dateTimeInJob = new SimpleDateFormat("MM/dd/yyyy").parse(timeInJob);
                            newImportSheetEmployeeRow.setTimeInJob(dateTimeInJob);
                            break;
                        case "Plan Eligibility Start Date":
                            DataFormatter formatterPEStartDate = new DataFormatter();
                            String planEligibilityStartDate = formatterPEStartDate.formatCellValue(currentCell);
                            Date datePEStartDate = new SimpleDateFormat("MM/dd/yyyy").parse(planEligibilityStartDate);
                            newImportSheetEmployeeRow.setPlanEligibilityStartDate(datePEStartDate);
                            break;
                        case "Plan Eligibility End Date":
                            DataFormatter formatterPEEndDate = new DataFormatter();
                            String planEligibilityEndDate = formatterPEEndDate.formatCellValue(currentCell);
                            Date datePEEndDate = new SimpleDateFormat("MM/dd/yyyy").parse(planEligibilityEndDate);
                            newImportSheetEmployeeRow.setPlanEligibilityEndDate(datePEEndDate);
                            break;
                        case "Business Unit Label":
                            newImportSheetEmployeeRow.setBusinessUnitLabel(currentCell.getStringCellValue());
                            break;
                        case "Business Unit Maximum Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setBusinessUnitMaximumGoal(currentCell.getNumericCellValue());
                            } else
                                newImportSheetEmployeeRow.setBusinessUnitMaximumGoal(0d);
                            break;
                        case "Business Unit Target Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setBusinessUnitTargetGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setBusinessUnitTargetGoal(0d);
                            }
                            break;
                        case "Business Unit Threshold Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setBusinessUnitThresholdGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setBusinessUnitThresholdGoal(0d);
                            }
                            break;
                        case "RQI Maximum Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setRqiMaximumGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setRqiMaximumGoal(0d);
                            }
                            break;
                        case "RQI Target Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setRqiTargetGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setRqiTargetGoal(0d);
                            }
                            break;
                        case "RQI Threshold Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setRqiThresholdGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setRqiThresholdGoal(0d);
                            }
                            break;
                        case "SBR Maximum Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setSbrMaximumGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setSbrMaximumGoal(0d);
                            }
                            break;
                        case "SBR Target":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setSbrTarget(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setSbrTarget(0d);
                            }
                            break;
                        case "SBR Threshold Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setSbrThresholdGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setSbrThresholdGoal(0d);
                            }
                            break;
                        case "Surplus Label":
                            newImportSheetEmployeeRow.setSurplusLabel(currentCell.getStringCellValue());
                            break;
                        case "Surplus Maximum Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setSurplusMaximumGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setSurplusMaximumGoal(0d);
                            }
                            break;
                        case "Surplus Target Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setSurplusTargetGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setSurplusTargetGoal(0d);
                            }
                            break;
                        case "Surplus Threshold Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setSurplusThresholdGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setSurplusThresholdGoal(0d);
                            }
                            break;
                        case "AWR Maximum Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setAwrMaximumGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setAwrMaximumGoal(0d);
                            }
                            break;
                        case "AWR Target Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setAwrTargetGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setAwrTargetGoal(0d);
                            }
                            break;
                        case "AWR Threshold Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setAwrThresholdGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setAwrThresholdGoal(0d);
                            }
                            break;
                        case "Major Gifts Maximum Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setMajorGiftsMaximumGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setMajorGiftsMaximumGoal(0d);
                            }
                            break;
                        case "Major Gifts Target Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setMajorGiftsTargetGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setMajorGiftsTargetGoal(0d);
                            }
                            break;
                        case "Major Gifts Threshold Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setMajorGiftsThresholdGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setMajorGiftsThresholdGoal(0d);
                            }
                            break;
                        case "Individual Goal Label":
                            newImportSheetEmployeeRow.setIndividualGoalLabel(currentCell.getStringCellValue());
                            break;
                        case "Individual Goal Maximum Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setIndividualGoalMaximumGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setIndividualGoalMaximumGoal(0d);
                            }
                            break;
                        case "Individual Goal Target Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setIndividualGoalTargetGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setIndividualGoalTargetGoal(0d);
                            }
                            break;
                        case "Individual Goal Threshold Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setIndividualGoalThresholdGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setIndividualGoalThresholdGoal(0d);
                            }
                            break;
                        case "Teamwork Label":
                            newImportSheetEmployeeRow.setTeamworkLabel(currentCell.getStringCellValue());
                            break;
                        case "Teamwork Maximum Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setTeamworkMaximumGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setTeamworkMaximumGoal(0d);
                            }
                            break;
                        case "Teamwork Target Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setTeamworkTargetGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setTeamworkTargetGoal(0d);
                            }
                            break;
                        case "Teamwork Threshold Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setTeamworkThresholdGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setTeamworkThresholdGoal(0d);
                            }
                            break;
                        case "In It Together Label":
                            newImportSheetEmployeeRow.setInItTogetherLabel(currentCell.getStringCellValue());
                            break;
                        case "In It Together Maximum Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setInItTogetherMaximumGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setInItTogetherMaximumGoal(0d);
                            }
                            break;
                        case "In It Together Target Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setInItTogetherTargetGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setInItTogetherTargetGoal(0d);
                            }
                            break;
                        case "In It Together Threshold Goal":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            if (!currentCellStringValue.equals("") && !currentCell.getCellType().equals(CellType.BLANK)
                                    && currentCell.getCellType().equals(CellType.NUMERIC)) {
                                newImportSheetEmployeeRow.setInItTogetherThresholdGoal(currentCell.getNumericCellValue());
                            } else {
                                newImportSheetEmployeeRow.setInItTogetherThresholdGoal(0d);
                            }
                            break;
                        case "Employee ID":
                            currentCellStringValue = getCellValueAsString(currentCell);
                            newImportSheetEmployeeRow.setEmployeeID(currentCellStringValue);
                            break;
                        case "Region":
                            newImportSheetEmployeeRow.setRegion(currentCell.getStringCellValue());
                            break;
                        case "Location":
                            newImportSheetEmployeeRow.setLocation(currentCell.getStringCellValue());
                            break;
                        case "Department":
                            newImportSheetEmployeeRow.setDepartment(currentCell.getStringCellValue());
                            break;
                        case "Job Code":
                            newImportSheetEmployeeRow.setJobCode(currentCell.getStringCellValue());
                            break;
                        case "Business Title":
                            newImportSheetEmployeeRow.setBusinessTitle(currentCell.getStringCellValue());
                            break;
                        case "Hire Date":
                            DataFormatter formatterHireDate = new DataFormatter();
                            String hireDate = formatterHireDate.formatCellValue(currentCell);
                            Date dateHireDate = new SimpleDateFormat("MM/dd/yyyy").parse(hireDate);
                            newImportSheetEmployeeRow.setHireDate(dateHireDate);
                            break;
                        default:
                            System.out.println("ALERT ALERT ALERT ALERT we have a problem!!!!!!!! column name not found:"+columnHeader);
                    }
                    columnNumber++;
                }
                importSheetRows.add(newImportSheetEmployeeRow);
                workbook.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return importSheetRows;
    }

    public static Object getCellValue(Cell cell) {
        Object cellValue = null;
        CellType cellType = cell.getCellTypeEnum();// CellType.forInt(cell.getCellType());
        if (cellType == CellType.STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cellType == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                cellValue = cell.getDateCellValue();
            } else {
                cellValue = cell.getNumericCellValue();
            }
        } else if (cellType == CellType.BOOLEAN) {
            cellValue = cell.getBooleanCellValue();
        } else if (cellType == CellType.FORMULA) {
            cellValue = cell.getCellFormula();
        } else if (cellType == CellType.BLANK) {
            cellValue = "";
        }
        return cellValue;
    }

    public static String getCellValueAsString(Cell cell) {
        String strCellValue = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    strCellValue = cell.toString();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        strCellValue = dateFormat.format(cell.getDateCellValue());
                    } else {
                        Double value = cell.getNumericCellValue();
                        Long longValue = value.longValue();
                        strCellValue = new String(longValue.toString());
                    }
                    break;
                case BOOLEAN:
                    strCellValue = new String(new Boolean(cell.getBooleanCellValue()).toString());
                    break;
                case BLANK:
                    strCellValue = "";
                    break;
            }
        }
        return strCellValue;
    }

    public static ArrayList<IncentiveWorksheetOutput> ProcessWorkSheetOutput(ArrayList<PlanGoalMatrix> planGoalMatrices,ArrayList<GoalDefinition> goalDefinitions,
                                                           ArrayList<IncentivePlanCategory> incentivePlanCategories,ArrayList<InputSheet> inputSheetEmployees) {

        ArrayList<IncentiveWorksheetOutput> processedEmployees = new ArrayList<>();

        for (InputSheet employeeToProcess: inputSheetEmployees) {
            IncentiveWorksheetOutput employeePrismOutput = new IncentiveWorksheetOutput();

            int fiscalYearFirstPart = Integer.parseInt(employeeToProcess.getFiscalYear());
            int fiscalYearSecondPart = fiscalYearFirstPart + 1;

            employeePrismOutput.setAddReplace(employeeToProcess.getAddReplace());
            employeePrismOutput.setFiscalYear(fiscalYearFirstPart+"-"+fiscalYearSecondPart);
            employeePrismOutput.setEmployeeID(employeeToProcess.getEmployeeID());
            employeePrismOutput.setEmployeeName(employeeToProcess.getEmployeeName());
            employeePrismOutput.setRegion(returnLongRegion(employeeToProcess.getRegion()));
            employeePrismOutput.setLocation(employeeToProcess.getLocation());
            employeePrismOutput.setDepartment(employeeToProcess.getDepartment());
            employeePrismOutput.setJobCode(employeeToProcess.getJobCode());
            employeePrismOutput.setJobProfile(employeeToProcess.getJobProfile());
            employeePrismOutput.setBusinessTitle(employeeToProcess.getBusinessTitle());
            employeePrismOutput.setTimeInJob(employeeToProcess.getTimeInJob());
            employeePrismOutput.setRecentHireDate(employeeToProcess.getHireDate());
            employeePrismOutput.setJobPlanValidation("");
            //SHORT TERM ANSWER, NEED TO ADDRESS
            employeePrismOutput.setSalary(100000d);
            //SHORT TERM ANSWER, NEED TO ADDRESS
            employeePrismOutput.setStatus("");
            //SHORT TERM ANSWER, NEED TO ADDRESS
            employeePrismOutput.setReason("");
            employeePrismOutput.setIncentivePlan(employeeToProcess.getIncentivePlan());
            employeePrismOutput.setDaysOfEmployeement(0);
            employeePrismOutput.setPlanEligibilityStartDate(employeeToProcess.getPlanEligibilityStartDate());
            employeePrismOutput.setPlanEligibilityEndDate(employeeToProcess.getPlanEligibilityEndDate());

            //ALL THE CODE BELOW TO GET THE DIFFERENCE BETWEEN TWO DATES
            Calendar calBiggerDate = new GregorianCalendar();
            Calendar calSmallerDate = new GregorianCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            Date biggerDate = employeeToProcess.getPlanEligibilityEndDate();
            Date smallerDate = employeeToProcess.getPlanEligibilityStartDate();
            calBiggerDate.setTime(biggerDate);
            calSmallerDate.setTime(smallerDate);
            int daysDifference = daysBetween(calSmallerDate.getTime(),calBiggerDate.getTime());
            employeePrismOutput.setDaysInPlan(daysDifference);

            employeePrismOutput.setProration(Math.round(daysDifference / 365 * 100));
            //SHORT TERM ANSWER, NEED TO ADDRESS
            employeePrismOutput.setPerformanceRating("Skilled");

            double planThreshold = 0d, planTarget = 0d, planMax = 0d;
            boolean foundPlanCategory = false;
            for (IncentivePlanCategory incentivePlanCategory : incentivePlanCategories) {
                if (incentivePlanCategory.getEmployeeID().equals(employeeToProcess.getEmployeeID()) &&
                        incentivePlanCategory.getIncentivePlan().equals(employeeToProcess.getIncentivePlan())) {
                    for (PlanGoalMatrix planGoalMatrix : planGoalMatrices) {
                        if ((planGoalMatrix.getPlanCategory().equals(incentivePlanCategory.getIncentivePlanCategory()) &&
                                planGoalMatrix.getIncentivePlanName().equals(incentivePlanCategory.getIncentivePlan()))) {
                            planThreshold = planGoalMatrix.getMin();
                            planTarget = planGoalMatrix.getTarget();
                            planMax = planGoalMatrix.getMax();
                            foundPlanCategory = true;
                        }
                    }
                }
            }
            employeePrismOutput.setOverallIncentiveThreshold(planThreshold);
            employeePrismOutput.setOverallIncentiveTarget(planTarget);
            employeePrismOutput.setOverallIncentiveMaximum(planMax);

            ArrayList<PrismGoalOutput> prismGoalOutputs = new ArrayList<>();

            // PROCESS BUSINESS UNIT GOAL
            if ((!employeeToProcess.getBusinessUnitLabel().equals("[SELECT FROM DROP DOWN]") && !employeeToProcess.getBusinessUnitLabel().equals("")) || employeeToProcess.getBusinessUnitThresholdGoal() > 0d ||
                    employeeToProcess.getBusinessUnitTargetGoal() > 0 || employeeToProcess.getBusinessUnitMaximumGoal() > 0d ) {

                PrismGoalOutput prismBusinessUnitGoalOutput = new PrismGoalOutput();

                prismBusinessUnitGoalOutput.setGoalName("Business Unit: "+employeeToProcess.getBusinessUnitLabel());

                    double goalWeight = 0d;
                    for (IncentivePlanCategory incentivePlanCategory : incentivePlanCategories) {
                        if ((incentivePlanCategory.getEmployeeID().equals(employeeToProcess.getEmployeeID()) &&
                                incentivePlanCategory.getIncentivePlan().equals(employeeToProcess.getIncentivePlan()))) {
                            for (PlanGoalMatrix planGoalMatrix : planGoalMatrices) {
                                if ((planGoalMatrix.getPlanCategory().equals(incentivePlanCategory.getIncentivePlanCategory()) &&
                                        planGoalMatrix.getIncentivePlanName().equals(incentivePlanCategory.getIncentivePlan())) &&
                                        planGoalMatrix.getGoalName().equals("Business Unit")) {
                                            goalWeight = planGoalMatrix.getGoalWeight();
                                }
                            }
                        }
                    }
                    prismBusinessUnitGoalOutput.setGoalWeight(goalWeight);
                    prismBusinessUnitGoalOutput.setGoalThreshold(employeeToProcess.getBusinessUnitThresholdGoal());
                    prismBusinessUnitGoalOutput.setGoalIncentiveOpportunityAtThreshold(goalWeight * planThreshold / 100);
                    prismBusinessUnitGoalOutput.setGoalMaximum(employeeToProcess.getBusinessUnitMaximumGoal());
                    prismBusinessUnitGoalOutput.setGoalIncentiveOpportunityAtMaximum(goalWeight * planMax / 100);
                    prismBusinessUnitGoalOutput.setGoalTarget(employeeToProcess.getBusinessUnitTargetGoal());
                    prismBusinessUnitGoalOutput.setGoalIncentiveOpportunityAtTarget(goalWeight * planTarget / 100);
                    //SHORT TERM ANSWER, NEED TO ADDRESS
                    prismBusinessUnitGoalOutput.setGoalActuals(0d);
                    prismBusinessUnitGoalOutput.setGoalPayoutPercentage(0d);
                    prismBusinessUnitGoalOutput.setGoalPayoutAmount(0d);
                    prismGoalOutputs.add(prismBusinessUnitGoalOutput);
            }

            // PROCESS RQI GOAL
            if (employeeToProcess.getRqiThresholdGoal() > 0d ||
                    employeeToProcess.getRqiTargetGoal() > 0 || employeeToProcess.getRqiMaximumGoal() > 0d ) {

                PrismGoalOutput prismRQIGoalOutput = new PrismGoalOutput();

                prismRQIGoalOutput.setGoalName("RQI");

                double goalWeight = 0d;
                for (IncentivePlanCategory incentivePlanCategory : incentivePlanCategories) {
                    if ((incentivePlanCategory.getEmployeeID().equals(employeeToProcess.getEmployeeID()) &&
                            incentivePlanCategory.getIncentivePlan().equals(employeeToProcess.getIncentivePlan()))) {
                        for (PlanGoalMatrix planGoalMatrix : planGoalMatrices) {
                            if ((planGoalMatrix.getPlanCategory().equals(incentivePlanCategory.getIncentivePlanCategory()) &&
                                    planGoalMatrix.getIncentivePlanName().equals(incentivePlanCategory.getIncentivePlan())) &&
                                    planGoalMatrix.getGoalName().equals("RQI")) {
                                goalWeight = planGoalMatrix.getGoalWeight();
                            }
                        }
                    }
                }
                prismRQIGoalOutput.setGoalWeight(goalWeight);
                prismRQIGoalOutput.setGoalThreshold(employeeToProcess.getRqiThresholdGoal());
                prismRQIGoalOutput.setGoalIncentiveOpportunityAtThreshold(goalWeight * planThreshold / 100);
                prismRQIGoalOutput.setGoalMaximum(employeeToProcess.getRqiMaximumGoal());
                prismRQIGoalOutput.setGoalIncentiveOpportunityAtMaximum(goalWeight * planMax / 100);
                prismRQIGoalOutput.setGoalTarget(employeeToProcess.getRqiTargetGoal());
                prismRQIGoalOutput.setGoalIncentiveOpportunityAtTarget(goalWeight * planTarget / 100);
                //SHORT TERM ANSWER, NEED TO ADDRESS
                prismRQIGoalOutput.setGoalActuals(0d);
                prismRQIGoalOutput.setGoalPayoutPercentage(0d);
                prismRQIGoalOutput.setGoalPayoutAmount(0d);
                prismGoalOutputs.add(prismRQIGoalOutput);
            }

            // PROCESS SBR GOAL
            if (employeeToProcess.getSbrThresholdGoal() > 0d ||
                    employeeToProcess.getSbrTarget() > 0 || employeeToProcess.getSbrMaximumGoal() > 0d ) {

                PrismGoalOutput prismSBRGoalOutput = new PrismGoalOutput();

                prismSBRGoalOutput.setGoalName("SBR");

                double goalWeight = 0d;
                for (IncentivePlanCategory incentivePlanCategory : incentivePlanCategories) {
                    if ((incentivePlanCategory.getEmployeeID().equals(employeeToProcess.getEmployeeID()) &&
                            incentivePlanCategory.getIncentivePlan().equals(employeeToProcess.getIncentivePlan()))) {
                        for (PlanGoalMatrix planGoalMatrix : planGoalMatrices) {
                            if ((planGoalMatrix.getPlanCategory().equals(incentivePlanCategory.getIncentivePlanCategory()) &&
                                    planGoalMatrix.getIncentivePlanName().equals(incentivePlanCategory.getIncentivePlan())) &&
                                    planGoalMatrix.getGoalName().equals("SBR")) {
                                goalWeight = planGoalMatrix.getGoalWeight();
                            }
                        }
                    }
                }
                prismSBRGoalOutput.setGoalWeight(goalWeight);
                prismSBRGoalOutput.setGoalThreshold(employeeToProcess.getSbrThresholdGoal());
                prismSBRGoalOutput.setGoalIncentiveOpportunityAtThreshold(goalWeight * planThreshold / 100);
                prismSBRGoalOutput.setGoalMaximum(employeeToProcess.getSbrMaximumGoal());
                prismSBRGoalOutput.setGoalIncentiveOpportunityAtMaximum(goalWeight * planMax / 100);
                prismSBRGoalOutput.setGoalTarget(employeeToProcess.getSbrTarget());
                prismSBRGoalOutput.setGoalIncentiveOpportunityAtTarget(goalWeight * planTarget / 100);
                //SHORT TERM ANSWER, NEED TO ADDRESS
                prismSBRGoalOutput.setGoalActuals(0d);
                prismSBRGoalOutput.setGoalPayoutPercentage(0d);
                prismSBRGoalOutput.setGoalPayoutAmount(0d);
                prismGoalOutputs.add(prismSBRGoalOutput);
            }

            // PROCESS SURPLUS GOAL
            if ((!employeeToProcess.getSurplusLabel().equals("[SELECT FROM DROP DOWN]") && !employeeToProcess.getSurplusLabel().equals("")) || employeeToProcess.getSurplusThresholdGoal() > 0d ||
                    employeeToProcess.getSurplusTargetGoal() > 0 || employeeToProcess.getSurplusMaximumGoal() > 0d ) {

                PrismGoalOutput prismSurplusGoalOutput = new PrismGoalOutput();

                prismSurplusGoalOutput.setGoalName("Surplus: "+employeeToProcess.getSurplusLabel());

                double goalWeight = 0d;
                for (IncentivePlanCategory incentivePlanCategory : incentivePlanCategories) {
                    if ((incentivePlanCategory.getEmployeeID().equals(employeeToProcess.getEmployeeID()) &&
                            incentivePlanCategory.getIncentivePlan().equals(employeeToProcess.getIncentivePlan()))) {
                        for (PlanGoalMatrix planGoalMatrix : planGoalMatrices) {
                            if ((planGoalMatrix.getPlanCategory().equals(incentivePlanCategory.getIncentivePlanCategory()) &&
                                    planGoalMatrix.getIncentivePlanName().equals(incentivePlanCategory.getIncentivePlan())) &&
                                    planGoalMatrix.getGoalName().equals("Surplus")) {
                                goalWeight = planGoalMatrix.getGoalWeight();
                            }
                        }
                    }
                }
                prismSurplusGoalOutput.setGoalWeight(goalWeight);
                prismSurplusGoalOutput.setGoalThreshold(employeeToProcess.getSurplusThresholdGoal());
                prismSurplusGoalOutput.setGoalIncentiveOpportunityAtThreshold(goalWeight * planThreshold / 100);
                prismSurplusGoalOutput.setGoalMaximum(employeeToProcess.getSurplusMaximumGoal());
                prismSurplusGoalOutput.setGoalIncentiveOpportunityAtMaximum(goalWeight * planMax / 100);
                prismSurplusGoalOutput.setGoalTarget(employeeToProcess.getSurplusTargetGoal());
                prismSurplusGoalOutput.setGoalIncentiveOpportunityAtTarget(goalWeight * planTarget / 100);
                //SHORT TERM ANSWER, NEED TO ADDRESS
                prismSurplusGoalOutput.setGoalActuals(0d);
                prismSurplusGoalOutput.setGoalPayoutPercentage(0d);
                prismSurplusGoalOutput.setGoalPayoutAmount(0d);
                prismGoalOutputs.add(prismSurplusGoalOutput);
            }


            // PROCESS AWR GOAL
            if (employeeToProcess.getAwrThresholdGoal() > 0d ||
                    employeeToProcess.getAwrTargetGoal() > 0 || employeeToProcess.getAwrMaximumGoal() > 0d ) {

                PrismGoalOutput prismAWRGoalOutput = new PrismGoalOutput();

                prismAWRGoalOutput.setGoalName("AWR");

                double goalWeight = 0d;
                for (IncentivePlanCategory incentivePlanCategory : incentivePlanCategories) {
                    if ((incentivePlanCategory.getEmployeeID().equals(employeeToProcess.getEmployeeID()) &&
                            incentivePlanCategory.getIncentivePlan().equals(employeeToProcess.getIncentivePlan()))) {
                        for (PlanGoalMatrix planGoalMatrix : planGoalMatrices) {
                            if ((planGoalMatrix.getPlanCategory().equals(incentivePlanCategory.getIncentivePlanCategory()) &&
                                    planGoalMatrix.getIncentivePlanName().equals(incentivePlanCategory.getIncentivePlan())) &&
                                    planGoalMatrix.getGoalName().equals("AWR")) {
                                goalWeight = planGoalMatrix.getGoalWeight();
                            }
                        }
                    }
                }
                prismAWRGoalOutput.setGoalWeight(goalWeight);
                prismAWRGoalOutput.setGoalThreshold(employeeToProcess.getAwrThresholdGoal());
                prismAWRGoalOutput.setGoalIncentiveOpportunityAtThreshold(goalWeight * planThreshold / 100);
                prismAWRGoalOutput.setGoalMaximum(employeeToProcess.getAwrMaximumGoal());
                prismAWRGoalOutput.setGoalIncentiveOpportunityAtMaximum(goalWeight * planMax / 100);
                prismAWRGoalOutput.setGoalTarget(employeeToProcess.getAwrTargetGoal());
                prismAWRGoalOutput.setGoalIncentiveOpportunityAtTarget(goalWeight * planTarget / 100);
                //SHORT TERM ANSWER, NEED TO ADDRESS
                prismAWRGoalOutput.setGoalActuals(0d);
                prismAWRGoalOutput.setGoalPayoutPercentage(0d);
                prismAWRGoalOutput.setGoalPayoutAmount(0d);
                prismGoalOutputs.add(prismAWRGoalOutput);
            }

            // PROCESS MAJOR GIFTS GOAL
            if (employeeToProcess.getMajorGiftsThresholdGoal() > 0d ||
                    employeeToProcess.getMajorGiftsTargetGoal() > 0 || employeeToProcess.getMajorGiftsMaximumGoal() > 0d ) {

                PrismGoalOutput prismMajorGiftsGoalOutput = new PrismGoalOutput();

                prismMajorGiftsGoalOutput.setGoalName("Major Gifts");

                double goalWeight = 0d;
                for (IncentivePlanCategory incentivePlanCategory : incentivePlanCategories) {
                    if ((incentivePlanCategory.getEmployeeID().equals(employeeToProcess.getEmployeeID()) &&
                            incentivePlanCategory.getIncentivePlan().equals(employeeToProcess.getIncentivePlan()))) {
                        for (PlanGoalMatrix planGoalMatrix : planGoalMatrices) {
                            if ((planGoalMatrix.getPlanCategory().equals(incentivePlanCategory.getIncentivePlanCategory()) &&
                                    planGoalMatrix.getIncentivePlanName().equals(incentivePlanCategory.getIncentivePlan())) &&
                                    planGoalMatrix.getGoalName().equals("Major Gifts")) {
                                goalWeight = planGoalMatrix.getGoalWeight();
                            }
                        }
                    }
                }
                prismMajorGiftsGoalOutput.setGoalWeight(goalWeight);
                prismMajorGiftsGoalOutput.setGoalThreshold(employeeToProcess.getMajorGiftsThresholdGoal());
                prismMajorGiftsGoalOutput.setGoalIncentiveOpportunityAtThreshold(goalWeight * planThreshold / 100);
                prismMajorGiftsGoalOutput.setGoalMaximum(employeeToProcess.getMajorGiftsMaximumGoal());
                prismMajorGiftsGoalOutput.setGoalIncentiveOpportunityAtMaximum(goalWeight * planMax / 100);
                prismMajorGiftsGoalOutput.setGoalTarget(employeeToProcess.getMajorGiftsTargetGoal());
                prismMajorGiftsGoalOutput.setGoalIncentiveOpportunityAtTarget(goalWeight * planTarget / 100);
                //SHORT TERM ANSWER, NEED TO ADDRESS
                prismMajorGiftsGoalOutput.setGoalActuals(0d);
                prismMajorGiftsGoalOutput.setGoalPayoutPercentage(0d);
                prismMajorGiftsGoalOutput.setGoalPayoutAmount(0d);
                prismGoalOutputs.add(prismMajorGiftsGoalOutput);
            }

            // PROCESS INDIVIDUAL GOAL
            if ((!employeeToProcess.getIndividualGoalLabel().equals("[SELECT FROM DROP DOWN]") && !employeeToProcess.getIndividualGoalLabel().equals("")) || employeeToProcess.getIndividualGoalThresholdGoal() > 0d ||
                    employeeToProcess.getIndividualGoalTargetGoal() > 0 || employeeToProcess.getIndividualGoalMaximumGoal() > 0d ) {

                PrismGoalOutput prismIndividualGoalOutput = new PrismGoalOutput();

                System.out.println("Individual Label:"+employeeToProcess.getBusinessUnitLabel());
                prismIndividualGoalOutput.setGoalName("Individual: "+employeeToProcess.getBusinessUnitLabel());

                double goalWeight = 0d;
                for (IncentivePlanCategory incentivePlanCategory : incentivePlanCategories) {
                    if ((incentivePlanCategory.getEmployeeID().equals(employeeToProcess.getEmployeeID()) &&
                            incentivePlanCategory.getIncentivePlan().equals(employeeToProcess.getIncentivePlan()))) {
                        for (PlanGoalMatrix planGoalMatrix : planGoalMatrices) {
                            if ((planGoalMatrix.getPlanCategory().equals(incentivePlanCategory.getIncentivePlanCategory()) &&
                                    planGoalMatrix.getIncentivePlanName().equals(incentivePlanCategory.getIncentivePlan())) &&
                                    planGoalMatrix.getGoalName().equals("Individual")) {
                                goalWeight = planGoalMatrix.getGoalWeight();
                            }
                        }
                    }
                }
                prismIndividualGoalOutput.setGoalWeight(goalWeight);
                prismIndividualGoalOutput.setGoalThreshold(employeeToProcess.getIndividualGoalThresholdGoal());
                prismIndividualGoalOutput.setGoalIncentiveOpportunityAtThreshold(goalWeight * planThreshold / 100);
                prismIndividualGoalOutput.setGoalMaximum(employeeToProcess.getIndividualGoalMaximumGoal());
                prismIndividualGoalOutput.setGoalIncentiveOpportunityAtMaximum(goalWeight * planMax / 100);
                prismIndividualGoalOutput.setGoalTarget(employeeToProcess.getIndividualGoalTargetGoal());
                prismIndividualGoalOutput.setGoalIncentiveOpportunityAtTarget(goalWeight * planTarget / 100);
                //SHORT TERM ANSWER, NEED TO ADDRESS
                prismIndividualGoalOutput.setGoalActuals(0d);
                prismIndividualGoalOutput.setGoalPayoutPercentage(0d);
                prismIndividualGoalOutput.setGoalPayoutAmount(0d);
                prismGoalOutputs.add(prismIndividualGoalOutput);
            }

            // PROCESS TEAMWORK GOAL
            if ((!employeeToProcess.getTeamworkLabel().equals("[SELECT FROM DROP DOWN]") && !employeeToProcess.getTeamworkLabel().equals("")) || employeeToProcess.getTeamworkThresholdGoal() > 0d ||
                    employeeToProcess.getTeamworkTargetGoal() > 0 || employeeToProcess.getTeamworkMaximumGoal() > 0d ) {

                PrismGoalOutput prismTeamworkGoalOutput = new PrismGoalOutput();

                prismTeamworkGoalOutput.setGoalName("Teamwork: "+employeeToProcess.getBusinessUnitLabel());

                double goalWeight = 0d;
                for (IncentivePlanCategory incentivePlanCategory : incentivePlanCategories) {
                    if ((incentivePlanCategory.getEmployeeID().equals(employeeToProcess.getEmployeeID()) &&
                            incentivePlanCategory.getIncentivePlan().equals(employeeToProcess.getIncentivePlan()))) {
                        for (PlanGoalMatrix planGoalMatrix : planGoalMatrices) {
                            if ((planGoalMatrix.getPlanCategory().equals(incentivePlanCategory.getIncentivePlanCategory()) &&
                                    planGoalMatrix.getIncentivePlanName().equals(incentivePlanCategory.getIncentivePlan())) &&
                                    planGoalMatrix.getGoalName().equals("Teamwork")) {
                                goalWeight = planGoalMatrix.getGoalWeight();
                            }
                        }
                    }
                }
                prismTeamworkGoalOutput.setGoalWeight(goalWeight);
                prismTeamworkGoalOutput.setGoalThreshold(employeeToProcess.getTeamworkThresholdGoal());
                prismTeamworkGoalOutput.setGoalIncentiveOpportunityAtThreshold(goalWeight * planThreshold / 100);
                prismTeamworkGoalOutput.setGoalMaximum(employeeToProcess.getTeamworkMaximumGoal());
                prismTeamworkGoalOutput.setGoalIncentiveOpportunityAtMaximum(goalWeight * planMax / 100);
                prismTeamworkGoalOutput.setGoalTarget(employeeToProcess.getTeamworkTargetGoal());
                prismTeamworkGoalOutput.setGoalIncentiveOpportunityAtTarget(goalWeight * planTarget / 100);
                //SHORT TERM ANSWER, NEED TO ADDRESS
                prismTeamworkGoalOutput.setGoalActuals(0d);
                prismTeamworkGoalOutput.setGoalPayoutPercentage(0d);
                prismTeamworkGoalOutput.setGoalPayoutAmount(0d);
                prismGoalOutputs.add(prismTeamworkGoalOutput);
            }

            // PROCESS IN IT TOGETHER GOAL
            if ((!employeeToProcess.getInItTogetherLabel().equals("[SELECT FROM DROP DOWN]") && !employeeToProcess.getInItTogetherLabel().equals("")) || employeeToProcess.getInItTogetherThresholdGoal() > 0d ||
                    employeeToProcess.getInItTogetherTargetGoal() > 0 || employeeToProcess.getInItTogetherMaximumGoal() > 0d ) {

                PrismGoalOutput prismInItTogetherGoalOutput = new PrismGoalOutput();

                prismInItTogetherGoalOutput.setGoalName("In It Together: "+employeeToProcess.getBusinessUnitLabel());

                double goalWeight = 0d;
                for (IncentivePlanCategory incentivePlanCategory : incentivePlanCategories) {
                    if ((incentivePlanCategory.getEmployeeID().equals(employeeToProcess.getEmployeeID()) &&
                            incentivePlanCategory.getIncentivePlan().equals(employeeToProcess.getIncentivePlan()))) {
                        for (PlanGoalMatrix planGoalMatrix : planGoalMatrices) {
                            if ((planGoalMatrix.getPlanCategory().equals(incentivePlanCategory.getIncentivePlanCategory()) &&
                                    planGoalMatrix.getIncentivePlanName().equals(incentivePlanCategory.getIncentivePlan())) &&
                                    planGoalMatrix.getGoalName().equals("In It Together")) {
                                goalWeight = planGoalMatrix.getGoalWeight();
                            }
                        }
                    }
                }
                prismInItTogetherGoalOutput.setGoalWeight(goalWeight);
                prismInItTogetherGoalOutput.setGoalThreshold(employeeToProcess.getInItTogetherThresholdGoal());
                prismInItTogetherGoalOutput.setGoalIncentiveOpportunityAtThreshold(goalWeight * planThreshold / 100);
                prismInItTogetherGoalOutput.setGoalMaximum(employeeToProcess.getInItTogetherMaximumGoal());
                prismInItTogetherGoalOutput.setGoalIncentiveOpportunityAtMaximum(goalWeight * planMax / 100);
                prismInItTogetherGoalOutput.setGoalTarget(employeeToProcess.getInItTogetherTargetGoal());
                prismInItTogetherGoalOutput.setGoalIncentiveOpportunityAtTarget(goalWeight * planTarget / 100);
                //SHORT TERM ANSWER, NEED TO ADDRESS
                prismInItTogetherGoalOutput.setGoalActuals(0d);
                prismInItTogetherGoalOutput.setGoalPayoutPercentage(0d);
                prismInItTogetherGoalOutput.setGoalPayoutAmount(0d);
                prismGoalOutputs.add(prismInItTogetherGoalOutput);
            }

            System.out.println("Number of goals in arraylist:"+prismGoalOutputs.size());
            if (prismGoalOutputs.size() > 5) {
                System.out.println("NOOOOOOOOOOOOO We have more than 5 goals!!!!!!!!!!!!!!!!");
            }

            if (prismGoalOutputs.size() > 0) {
                employeePrismOutput.setGoal1Name(prismGoalOutputs.get(0).getGoalName());
                employeePrismOutput.setGoal1Weight(prismGoalOutputs.get(0).getGoalWeight());
                employeePrismOutput.setGoal1Threshold(prismGoalOutputs.get(0).getGoalThreshold());
                employeePrismOutput.setGoal1IncentiveOppertunityAtThresholdPercent(prismGoalOutputs.get(0).getGoalIncentiveOpportunityAtThreshold());
                employeePrismOutput.setGoal1Maximum(prismGoalOutputs.get(0).getGoalMaximum());
                employeePrismOutput.setGoal1IncentiveOppertunityAtMaximumPercent(prismGoalOutputs.get(0).getGoalIncentiveOpportunityAtMaximum());
                employeePrismOutput.setGoal1Target(prismGoalOutputs.get(0).getGoalTarget());
                employeePrismOutput.setGoal1IncentiveOppertunityAtTargetPercent(prismGoalOutputs.get(0).getGoalIncentiveOpportunityAtTarget());
                employeePrismOutput.setGoal1Actuals(prismGoalOutputs.get(0).getGoalActuals());
                employeePrismOutput.setGoal1PayoutPercent(prismGoalOutputs.get(0).getGoalPayoutPercentage());
                employeePrismOutput.setGoal1PayoutAmount(prismGoalOutputs.get(0).getGoalPayoutAmount());
                if (prismGoalOutputs.size() > 1) {
                    employeePrismOutput.setGoal2Name(prismGoalOutputs.get(1).getGoalName());
                    employeePrismOutput.setGoal2Weight(prismGoalOutputs.get(1).getGoalWeight());
                    employeePrismOutput.setGoal2Threshold(prismGoalOutputs.get(1).getGoalThreshold());
                    employeePrismOutput.setGoal2IncentiveOppertunityAtThresholdPercent(prismGoalOutputs.get(1).getGoalIncentiveOpportunityAtThreshold());
                    employeePrismOutput.setGoal2Maximum(prismGoalOutputs.get(1).getGoalMaximum());
                    employeePrismOutput.setGoal2IncentiveOppertunityAtMaximumPercent(prismGoalOutputs.get(1).getGoalIncentiveOpportunityAtMaximum());
                    employeePrismOutput.setGoal2Target(prismGoalOutputs.get(1).getGoalTarget());
                    employeePrismOutput.setGoal2IncentiveOppertunityAtTargetPercent(prismGoalOutputs.get(1).getGoalIncentiveOpportunityAtTarget());
                    employeePrismOutput.setGoal2Actuals(prismGoalOutputs.get(1).getGoalActuals());
                    employeePrismOutput.setGoal2PayoutPercent(prismGoalOutputs.get(1).getGoalPayoutPercentage());
                    employeePrismOutput.setGoal2PayoutAmount(prismGoalOutputs.get(1).getGoalPayoutAmount());
                    if (prismGoalOutputs.size() > 2) {
                        employeePrismOutput.setGoal3Name(prismGoalOutputs.get(2).getGoalName());
                        employeePrismOutput.setGoal3Weight(prismGoalOutputs.get(2).getGoalWeight());
                        employeePrismOutput.setGoal3Threshold(prismGoalOutputs.get(2).getGoalThreshold());
                        employeePrismOutput.setGoal3IncentiveOppertunityAtThresholdPercent(prismGoalOutputs.get(2).getGoalIncentiveOpportunityAtThreshold());
                        employeePrismOutput.setGoal3Maximum(prismGoalOutputs.get(2).getGoalMaximum());
                        employeePrismOutput.setGoal3IncentiveOppertunityAtMaximumPercent(prismGoalOutputs.get(2).getGoalIncentiveOpportunityAtMaximum());
                        employeePrismOutput.setGoal3Target(prismGoalOutputs.get(2).getGoalTarget());
                        employeePrismOutput.setGoal3IncentiveOppertunityAtTargetPercent(prismGoalOutputs.get(2).getGoalIncentiveOpportunityAtTarget());
                        employeePrismOutput.setGoal3Actuals(prismGoalOutputs.get(2).getGoalActuals());
                        employeePrismOutput.setGoal3PayoutPercent(prismGoalOutputs.get(2).getGoalPayoutPercentage());
                        employeePrismOutput.setGoal3PayoutAmount(prismGoalOutputs.get(2).getGoalPayoutAmount());
                        if (prismGoalOutputs.size() > 3) {
                            employeePrismOutput.setGoal4Name(prismGoalOutputs.get(3).getGoalName());
                            employeePrismOutput.setGoal4Weight(prismGoalOutputs.get(3).getGoalWeight());
                            employeePrismOutput.setGoal4Threshold(prismGoalOutputs.get(3).getGoalThreshold());
                            employeePrismOutput.setGoal4IncentiveOppertunityAtThresholdPercent(prismGoalOutputs.get(3).getGoalIncentiveOpportunityAtThreshold());
                            employeePrismOutput.setGoal4Maximum(prismGoalOutputs.get(3).getGoalMaximum());
                            employeePrismOutput.setGoal4IncentiveOppertunityAtMaximumPercent(prismGoalOutputs.get(3).getGoalIncentiveOpportunityAtMaximum());
                            employeePrismOutput.setGoal4Target(prismGoalOutputs.get(3).getGoalTarget());
                            employeePrismOutput.setGoal4IncentiveOppertunityAtTargetPercent(prismGoalOutputs.get(3).getGoalIncentiveOpportunityAtTarget());
                            employeePrismOutput.setGoal4Actuals(prismGoalOutputs.get(3).getGoalActuals());
                            employeePrismOutput.setGoal4PayoutPercent(prismGoalOutputs.get(3).getGoalPayoutPercentage());
                            employeePrismOutput.setGoal4PayoutAmount(prismGoalOutputs.get(3).getGoalPayoutAmount());
                            if (prismGoalOutputs.size() > 4) {
                                employeePrismOutput.setGoal5Name(prismGoalOutputs.get(4).getGoalName());
                                employeePrismOutput.setGoal5Weight(prismGoalOutputs.get(4).getGoalWeight());
                                employeePrismOutput.setGoal5Threshold(prismGoalOutputs.get(4).getGoalThreshold());
                                employeePrismOutput.setGoal5IncentiveOppertunityAtThresholdPercent(prismGoalOutputs.get(4).getGoalIncentiveOpportunityAtThreshold());
                                employeePrismOutput.setGoal5Maximum(prismGoalOutputs.get(4).getGoalMaximum());
                                employeePrismOutput.setGoal5IncentiveOppertunityAtMaximumPercent(prismGoalOutputs.get(4).getGoalIncentiveOpportunityAtMaximum());
                                employeePrismOutput.setGoal5Target(prismGoalOutputs.get(4).getGoalTarget());
                                employeePrismOutput.setGoal5IncentiveOppertunityAtTargetPercent(prismGoalOutputs.get(4).getGoalIncentiveOpportunityAtTarget());
                                employeePrismOutput.setGoal5Actuals(prismGoalOutputs.get(4).getGoalActuals());
                                employeePrismOutput.setGoal5PayoutPercent(prismGoalOutputs.get(4).getGoalPayoutPercentage());
                                employeePrismOutput.setGoal5PayoutAmount(prismGoalOutputs.get(4).getGoalPayoutAmount());
                            }
                        }
                    }
                }
            }
            employeePrismOutput.setTotalPayoutAmount(0d);
            employeePrismOutput.setTotalPayoutPercent(0d);
            employeePrismOutput.setProratedPayoutAmount(0d);
            employeePrismOutput.setProratedPayoutPercent(0d);

            Date dateCal = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = dateFormat.format(System.currentTimeMillis());
            employeePrismOutput.setLastUpdated(strDate);

            if (foundPlanCategory) {
                processedEmployees.add(employeePrismOutput);
            } else {
                employeePrismOutput.setReason("Failed to link employee incentive plan to incentive plan category.  Possible cause is employee is terminated.");
                errorEmployees.add(employeePrismOutput);
            }

        }

        if (processedEmployees.size() > 0) {
            System.out.println("YOOOHOOO we have victory");
        }

        System.out.println("Number of problem records:"+errorEmployees.size());
        return processedEmployees;
    }

    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private static String returnNewRegionAbreviation(String region) {

        String newRegion;

        switch (region) {
            case "FDA" :
                newRegion = "ES";
                break;
            case "MWA" :
                newRegion = "MW";
                break;
            case "GSA" :
                newRegion = "SE";
                break;
            case "SWA" :
                newRegion = "SW";
                break;
            case "WSA" :
                newRegion = "WS";
                break;
            case "NAT" :
                newRegion = "NAT";
                break;
            default :
                newRegion = region;
        }

        return newRegion;

    }

    private static String returnOldRegionAbreviation(String region) {

        String oldRegion;

        switch (region) {
            case "ES" :
                oldRegion = "FDA";
                break;
            case "MW" :
                oldRegion = "MWA";
                break;
            case "SE" :
                oldRegion = "GSA";
                break;
            case "SW" :
                oldRegion = "SWA";
                break;
            case "WS" :
                oldRegion = "WSA";
                break;
            case "NAT" :
                oldRegion = "NAT";
                break;
            default :
                oldRegion = region;
        }
        return oldRegion;
    }

    private static String returnLongRegion(String region) {

        String longRegion;

        switch (region) {
            case "ES" :
                longRegion = "Eastern States";
                break;
            case "MW" :
                longRegion = "Mid West";
                break;
            case "SE" :
                longRegion = "South East";
                break;
            case "SW" :
                longRegion = "South West";
                break;
            case "WS" :
                longRegion = "Western States";
                break;
            case "NAT" :
                longRegion = "National Center";
                break;
            default :
                longRegion = region;
        }

        return longRegion;

    }

}
