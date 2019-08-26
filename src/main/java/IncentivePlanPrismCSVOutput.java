import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.opencsv.CSVWriter;

public class IncentivePlanPrismCSVOutput {
    private static String ADDREPLACE = "Add-Replace";
    private static String FISCALYEAR = "Fiscal Year";
    private static String EMPLOYEEID = "Employee ID";
    private static String EMPLOYEENAME = "Employee Name";
    private static String NCREGION = "Region";
    private static String LOCATION = "Location";
    private static String DEPARTMENT = "Department";
    private static String JOBCODE = "Job Code";
    private static String JOBPROFILE = "Job Profile";
    private static String BUSINESSTITLE = "Business Title";
    private static String TIMEINJOB = "Time In Job";
    private static String HIREDATE = "Hire Date";
    private static String JOBPLANVALIDATION = "Job/plan Validation";
    private static String SALARY = "Salary";
    private static String STATUS = "Status";
    private static String REASON = "Reason";
    private static String INCENTIVEPLAN = "Incentive Plan";
    private static String DAYSOFEMPLOYMENT = "Days of Employment";
    private static String PLANELIGIBILITYSTARTDATE = "Plan Eligibility Start Date";
    private static String PLANELIGIBILITYENDDATE = "Plan Eligibility End Date";
    private static String DAYSINPLAN = "Days In Plan";
    private static String PRORATION = "Proration";
    private static String PERFORMANCERATING = "Performance Rating";
    private static String OVERALLINCENTIVEMAXIMUM = "Overall Incentive Maximum";
    private static String OVERALLINCENTIVETARGET = "Overall Incentive Target";
    private static String OVERALLINCENTIVETHRESHOLD = "Overall Incentive Threshold";
    private static String GOAL1NAME = "Goal 1 Name";
    private static String GOAL1WEIGHT = "Goal 1 Weight";
    private static String GOAL1MAXIMUM = "Goal 1 Maximum";
    private static String GOAL1INCENTOPPATMAXIMUM = "Goal 1 Incentive Opportunity at Maximum (%)";
    private static String GOAL1TARGET = "Goal 1 Target";
    private static String GOAL1INCENTOPPATTARGET = "Goal 1 Incentive Opportunity at Target (%)";
    private static String GOAL1THRESHOLD = "Goal 1 Threshold";
    private static String GOAL1INCENTOPPATTHRESHOLD = "Goal 1 Incentive Opportunity at Threshold (%)";
    private static String GOAL1ACTUALS = "Goal 1 Actuals";
    private static String GOAL1PAYOUTPERCENT = "Goal 1 Payout (%)";
    private static String GOAL1PAYOUTAMOUNT = "Goal 1 Payout ($)";
    private static String GOAL2NAME = "Goal 2 Name";
    private static String GOAL2WEIGHT = "Goal 2 Weight";
    private static String GOAL2MAXIMUM = "Goal 2 Maximum";
    private static String GOAL2INCENTOPPATMAXIMUM = "Goal 2 Incentive Opportunity at Maximum (%)";
    private static String GOAL2TARGET = "Goal 2 Target";
    private static String GOAL2INCENTOPPATTARGET = "Goal 2 Incentive Opportunity at Target (%)";
    private static String GOAL2THRESHOLD = "Goal 2 Threshold";
    private static String GOAL2INCENTOPPATTHRESHOLD = "Goal 2 Incentive Opportunity at Threshold (%)";
    private static String GOAL2ACTUALS = "Goal 2 Actuals";
    private static String GOAL2PAYOUTPERCENT = "Goal 2 Payout (%)";
    private static String GOAL2PAYOUTAMOUNT = "Goal 2 Payout ($)";
    private static String GOAL3NAME = "Goal 3 Name";
    private static String GOAL3WEIGHT = "Goal 3 Weight";
    private static String GOAL3MAXIMUM = "Goal 3 Maximum";
    private static String GOAL3INCENTOPPATMAXIMUM = "Goal 3 Incentive Opportunity at Maximum (%)";
    private static String GOAL3TARGET = "Goal 3 Target";
    private static String GOAL3INCENTOPPATTARGET = "Goal 3 Incentive Opportunity at Target (%)";
    private static String GOAL3THRESHOLD = "Goal 3 Threshold";
    private static String GOAL3INCENTOPPATTHRESHOLD = "Goal 3 Incentive Opportunity at Threshold (%)";
    private static String GOAL3ACTUALS = "Goal 3 Actuals";
    private static String GOAL3PAYOUTPERCENT = "Goal 3 Payout (%)";
    private static String GOAL3PAYOUTAMOUNT = "Goal 3 Payout ($)";
    private static String GOAL4NAME = "Goal 4 Name";
    private static String GOAL4WEIGHT = "Goal 4 Weight";
    private static String GOAL4MAXIMUM = "Goal 4 Maximum";
    private static String GOAL4INCENTOPPATMAXIMUM = "Goal 4 Incentive Opportunity at Maximum (%)";
    private static String GOAL4TARGET = "Goal 4 Target";
    private static String GOAL4INCENTOPPATTARGET = "Goal 4 Incentive Opportunity at Target (%)";
    private static String GOAL4THRESHOLD = "Goal 4 Threshold";
    private static String GOAL4INCENTOPPATTHRESHOLD = "Goal 4 Incentive Opportunity at Threshold (%)";
    private static String GOAL4ACTUALS = "Goal 4 Actuals";
    private static String GOAL4PAYOUTPERCENT = "Goal 4 Payout (%)";
    private static String GOAL4PAYOUTAMOUNT = "Goal 4 Payout ($)";
    private static String GOAL5NAME = "Goal 5 Name";
    private static String GOAL5WEIGHT = "Goal 5 Weight";
    private static String GOAL5MAXIMUM = "Goal 5 Maximum";
    private static String GOAL5INCENTOPPATMAXIMUM = "Goal 5 Incentive Opportunity at Maximum (%)";
    private static String GOAL5TARGET = "Goal 5 Target";
    private static String GOAL5INCENTOPPATTARGET = "Goal 5 Incentive Opportunity at Target (%)";
    private static String GOAL5THRESHOLD = "Goal 5 Threshold";
    private static String GOAL5INCENTOPPATTHRESHOLD = "Goal 5 Incentive Opportunity at Threshold (%)";
    private static String GOAL5ACTUALS = "Goal 5 Actuals";
    private static String GOAL5PAYOUTPERCENT = "Goal 5 Payout (%)";
    private static String GOAL5PAYOUTAMOUNT = "Goal 5 Payout ($)";
    private static String TOTALPAYOUTPERCENT = "Total Payout (%)";
    private static String TOTALPAYOUTAMOUNT = "Total Payout ($)";
    private static String PRORATEDPAYOUTPERCENT = "Prorated Payout (%)";
    private static String PRORATEDPAYOUTAMOUNT = "Prorated Payout ($)";
    private static String LASTINSERTDATETIME = "Last Insert Date Time";

    private static String fileName;
    private static String fileLocation;
    private static File outputPrismFile;
    private static FileWriter outputWriter;
    private static CSVWriter csvWriter;

    public IncentivePlanPrismCSVOutput() {

    }

    public IncentivePlanPrismCSVOutput(String fileLocation, String fileName) {
        this.fileName = fileName;
        this.fileLocation = fileLocation;
    }

    public void createIncentivePrismOutputFile() throws IOException {
        outputPrismFile = new File(fileLocation+fileName);
        outputWriter = new FileWriter(outputPrismFile);
        csvWriter = new CSVWriter(outputWriter);

        String[] header = {ADDREPLACE, FISCALYEAR, EMPLOYEEID, EMPLOYEENAME, NCREGION, LOCATION,
                DEPARTMENT, JOBCODE, JOBPROFILE, BUSINESSTITLE, TIMEINJOB, HIREDATE, JOBPLANVALIDATION,
                SALARY, STATUS, REASON, INCENTIVEPLAN, DAYSOFEMPLOYMENT, PLANELIGIBILITYSTARTDATE,
                PLANELIGIBILITYENDDATE, DAYSINPLAN, PRORATION, PERFORMANCERATING, OVERALLINCENTIVEMAXIMUM,
                OVERALLINCENTIVETARGET, OVERALLINCENTIVETHRESHOLD, GOAL1NAME, GOAL1WEIGHT, GOAL1MAXIMUM,
                GOAL1INCENTOPPATMAXIMUM, GOAL1TARGET, GOAL1INCENTOPPATTARGET, GOAL1THRESHOLD,
                GOAL1INCENTOPPATTHRESHOLD, GOAL1ACTUALS, GOAL1PAYOUTPERCENT, GOAL1PAYOUTAMOUNT, GOAL2NAME,
                GOAL2WEIGHT, GOAL2MAXIMUM, GOAL2INCENTOPPATMAXIMUM, GOAL2TARGET, GOAL2INCENTOPPATTARGET,
                GOAL2THRESHOLD, GOAL2INCENTOPPATTHRESHOLD, GOAL2ACTUALS, GOAL2PAYOUTPERCENT, GOAL2PAYOUTAMOUNT,
                GOAL3NAME, GOAL3WEIGHT, GOAL3MAXIMUM, GOAL3INCENTOPPATMAXIMUM, GOAL3TARGET,
                GOAL3INCENTOPPATTARGET, GOAL3THRESHOLD, GOAL3INCENTOPPATTHRESHOLD, GOAL3ACTUALS,
                GOAL3PAYOUTPERCENT, GOAL3PAYOUTAMOUNT, GOAL4NAME, GOAL4WEIGHT, GOAL4MAXIMUM,
                GOAL4INCENTOPPATMAXIMUM, GOAL4TARGET, GOAL4INCENTOPPATTARGET, GOAL4THRESHOLD,
                GOAL4INCENTOPPATTHRESHOLD, GOAL4ACTUALS, GOAL4PAYOUTPERCENT, GOAL4PAYOUTAMOUNT, GOAL5NAME,
                GOAL5WEIGHT, GOAL5MAXIMUM, GOAL5INCENTOPPATMAXIMUM, GOAL5TARGET, GOAL5INCENTOPPATTARGET,
                GOAL5THRESHOLD, GOAL5INCENTOPPATTHRESHOLD, GOAL5ACTUALS, GOAL5PAYOUTPERCENT, GOAL5PAYOUTAMOUNT,
                TOTALPAYOUTPERCENT, TOTALPAYOUTAMOUNT, PRORATEDPAYOUTPERCENT, PRORATEDPAYOUTAMOUNT, LASTINSERTDATETIME};
        csvWriter.writeNext(header);
    }

    public void WriteIncentiveOutputFile(ArrayList<IncentiveWorksheetOutput> incentiveWorksheetOutputs) {

        SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd/YYYY");

        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        for (IncentiveWorksheetOutput incentiveWorksheetOutput : incentiveWorksheetOutputs) {
            String timeInJob = formatDate.format(incentiveWorksheetOutput.getTimeInJob());
            String hireDate = formatDate.format(incentiveWorksheetOutput.getRecentHireDate());
            String planEligibilityStartDate = formatDate.format(incentiveWorksheetOutput.getPlanEligibilityStartDate());
            String planEligibilityEndDate = formatDate.format(incentiveWorksheetOutput.getPlanEligibilityEndDate());

            String[] outputRow = {incentiveWorksheetOutput.getAddReplace(),incentiveWorksheetOutput.getFiscalYear(),
                    incentiveWorksheetOutput.getEmployeeID(),incentiveWorksheetOutput.getEmployeeName(),incentiveWorksheetOutput.getRegion(),
                    incentiveWorksheetOutput.getLocation(),incentiveWorksheetOutput.getDepartment(),incentiveWorksheetOutput.getJobCode(),
                    incentiveWorksheetOutput.getJobProfile(),incentiveWorksheetOutput.getBusinessTitle(),timeInJob,
                    hireDate, incentiveWorksheetOutput.getJobPlanValidation(),Double.toString(incentiveWorksheetOutput.getSalary()),
                    incentiveWorksheetOutput.getStatus(),incentiveWorksheetOutput.getReason(),incentiveWorksheetOutput.getIncentivePlan(),
                    Double.toString(incentiveWorksheetOutput.getDaysOfEmployeement()),planEligibilityStartDate,
                    planEligibilityEndDate,Double.toString(incentiveWorksheetOutput.getDaysInPlan()),
                    Double.toString(incentiveWorksheetOutput.getProration()),incentiveWorksheetOutput.getPerformanceRating(),Double.toString(incentiveWorksheetOutput.getOverallIncentiveMaximum()),
                    Double.toString(incentiveWorksheetOutput.getOverallIncentiveTarget()),Double.toString(incentiveWorksheetOutput.getOverallIncentiveThreshold()),
                    incentiveWorksheetOutput.getGoal1Name(),decimalFormat.format(incentiveWorksheetOutput.getGoal1Weight()),decimalFormat.format(incentiveWorksheetOutput.getGoal1Maximum()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal1IncentiveOppertunityAtMaximumPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal1Target()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal1IncentiveOppertunityAtTargetPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal1Threshold()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal1IncentiveOppertunityAtThresholdPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal1Actuals()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal1PayoutPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal1PayoutAmount()),
                    incentiveWorksheetOutput.getGoal2Name(),decimalFormat.format(incentiveWorksheetOutput.getGoal2Weight()),decimalFormat.format(incentiveWorksheetOutput.getGoal2Maximum()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal2IncentiveOppertunityAtMaximumPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal2Target()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal2IncentiveOppertunityAtTargetPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal2Threshold()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal2IncentiveOppertunityAtThresholdPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal2Actuals()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal2PayoutPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal2PayoutAmount()),
                    incentiveWorksheetOutput.getGoal3Name(),decimalFormat.format(incentiveWorksheetOutput.getGoal3Weight()),decimalFormat.format(incentiveWorksheetOutput.getGoal3Maximum()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal3IncentiveOppertunityAtMaximumPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal3Target()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal3IncentiveOppertunityAtTargetPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal3Threshold()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal3IncentiveOppertunityAtThresholdPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal3Actuals()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal3PayoutPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal3PayoutAmount()),
                    incentiveWorksheetOutput.getGoal4Name(),decimalFormat.format(incentiveWorksheetOutput.getGoal4Weight()),decimalFormat.format(incentiveWorksheetOutput.getGoal4Maximum()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal4IncentiveOppertunityAtMaximumPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal4Target()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal4IncentiveOppertunityAtTargetPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal4Threshold()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal4IncentiveOppertunityAtThresholdPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal4Actuals()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal4PayoutPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal4PayoutAmount()),
                    incentiveWorksheetOutput.getGoal5Name(),decimalFormat.format(incentiveWorksheetOutput.getGoal5Weight()),decimalFormat.format(incentiveWorksheetOutput.getGoal5Maximum()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal5IncentiveOppertunityAtMaximumPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal5Target()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal5IncentiveOppertunityAtTargetPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal5Threshold()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal5IncentiveOppertunityAtThresholdPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal5Actuals()),
                    decimalFormat.format(incentiveWorksheetOutput.getGoal5PayoutPercent()),decimalFormat.format(incentiveWorksheetOutput.getGoal5PayoutAmount()),
                    decimalFormat.format(incentiveWorksheetOutput.getTotalPayoutPercent()),decimalFormat.format(incentiveWorksheetOutput.getTotalPayoutAmount()),
                    decimalFormat.format(incentiveWorksheetOutput.getProratedPayoutPercent()),decimalFormat.format(incentiveWorksheetOutput.getProratedPayoutAmount()),
                    incentiveWorksheetOutput.getLastUpdated() };

            csvWriter.writeNext(outputRow);

        };

    }

    public static void saveIncentiveOutputFile() throws IOException {
        csvWriter.close();
    }

}
