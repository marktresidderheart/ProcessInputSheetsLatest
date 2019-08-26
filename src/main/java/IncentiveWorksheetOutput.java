import com.sun.jmx.snmp.Timestamp;
import java.util.Date;

public class IncentiveWorksheetOutput {

    private String addReplace;
    private String fiscalYear;
    private String employeeID;
    private String employeeName;
    private String region;
    private String location;
    private String department;
    private String jobCode;
    private String jobProfile;
    private String businessTitle;
    private Date timeInJob;
    private Date recentHireDate;
    private String jobPlanValidation;
    private double salary;
    private String status;
    private String reason;
    private String incentivePlan;
    private int daysOfEmployeement;
    private Date planEligibilityStartDate;
    private Date planEligibilityEndDate;
    private double daysInPlan;
    private double proration;
    private String performanceRating;
    private double overallIncentiveThreshold;
    private double overallIncentiveTarget;
    private double overallIncentiveMaximum;
    private String goal1Name;
    private double goal1Weight;
    private double goal1Threshold;
    private double goal1IncentiveOppertunityAtThresholdPercent;
    private double goal1Maximum;
    private double goal1IncentiveOppertunityAtMaximumPercent;
    private double goal1Target;
    private double goal1IncentiveOppertunityAtTargetPercent;
    private double goal1Actuals;
    private double goal1PayoutPercent;
    private double goal1PayoutAmount;
    private String goal2Name;
    private double goal2Weight;
    private double goal2Threshold;
    private double goal2IncentiveOppertunityAtThresholdPercent;
    private double goal2Maximum;
    private double goal2IncentiveOppertunityAtMaximumPercent;
    private double goal2Target;
    private double goal2IncentiveOppertunityAtTargetPercent;
    private double goal2Actuals;
    private double goal2PayoutPercent;
    private double goal2PayoutAmount;
    private String goal3Name;
    private double goal3Weight;
    private double goal3Threshold;
    private double goal3IncentiveOppertunityAtThresholdPercent;
    private double goal3Maximum;
    private double goal3IncentiveOppertunityAtMaximumPercent;
    private double goal3Target;
    private double goal3IncentiveOppertunityAtTargetPercent;
    private double goal3Actuals;
    private double goal3PayoutPercent;
    private double goal3PayoutAmount;
    private String goal4Name;
    private double goal4Weight;
    private double goal4Threshold;
    private double goal4IncentiveOppertunityAtThresholdPercent;
    private double goal4Maximum;
    private double goal4IncentiveOppertunityAtMaximumPercent;
    private double goal4Target;
    private double goal4IncentiveOppertunityAtTargetPercent;
    private double goal4Actuals;
    private double goal4PayoutPercent;
    private double goal4PayoutAmount;
    private String goal5Name;
    private double goal5Weight;
    private double goal5Threshold;
    private double goal5IncentiveOppertunityAtThresholdPercent;
    private double goal5Maximum;
    private double goal5IncentiveOppertunityAtMaximumPercent;
    private double goal5Target;
    private double goal5IncentiveOppertunityAtTargetPercent;
    private double goal5Actuals;
    private double goal5PayoutPercent;
    private double goal5PayoutAmount;
    private double totalPayoutPercent;
    private double totalPayoutAmount;
    private double proratedPayoutPercent;
    private double proratedPayoutAmount;
    private String lastUpdated;

    public IncentiveWorksheetOutput () {

    }

    public IncentiveWorksheetOutput (String addReplace,String fiscalYear,String employeeID,String employeeName,
                                     String region,String location,String department,String jobCode,String jobProfile,
                                     String businessTitle,Date timeInJob,Date recentHireDate, String jobPlanValidation,double salary,
                                     String status,String reason,String incentivePlan,int daysOfEmployeement,
                                     Date planEligibilityStartDate,Date planEligibilityEndDate,double daysInPlan,
                                     double proration,String performanceRating,double overallIncentiveThreshold,
                                     double overallIncentiveTarget,double overallIncentiveMaximum,String goal1Name,
                                     double goal1Weight,double goal1Threshold,double goal1IncentiveOppertunityAtThresholdPercent,
                                     double goal1Maximum,double goal1IncentiveOppertunityAtMaximumPercent,double goal1Target,
                                     double goal1IncentiveOppertunityAtTargetPercent,double goal1Actuals,double goal1PayoutPercent,
                                     double goal1PayoutAmount,String goal2Name,double goal2Weight,double goal2Threshold,
                                     double goal2IncentiveOppertunityAtThresholdPercent,double goal2Maximum,
                                     double goal2IncentiveOppertunityAtMaximumPercent,double goal2Target,
                                     double goal2IncentiveOppertunityAtTargetPercent,double goal2Actuals,double goal2PayoutPercent,
                                     double goal2PayoutAmount,String goal3Name,double goal3Weight,double goal3Threshold,
                                     double goal3IncentiveOppertunityAtThresholdPercent,double goal3Maximum,
                                     double goal3IncentiveOppertunityAtMaximumPercent,double goal3Target,
                                     double goal3IncentiveOppertunityAtTargetPercent,double goal3Actuals,double goal3PayoutPercent,
                                     double goal3PayoutAmount,String goal4Name,double goal4Weight,double goal4Threshold,
                                     double goal4IncentiveOppertunityAtThresholdPercent,double goal4Maximum,
                                     double goal4IncentiveOppertunityAtMaximumPercent,double goal4Target,
                                     double goal4IncentiveOppertunityAtTargetPercent,double goal4Actuals,double goal4PayoutPercent,
                                     double goal4PayoutAmount,String goal5Name,double goal5Weight,double goal5Threshold,
                                     double goal5IncentiveOppertunityAtThresholdPercent,double goal5Maximum,
                                     double goal5IncentiveOppertunityAtMaximumPercent,double goal5Target,
                                     double goal5IncentiveOppertunityAtTargetPercent,double goal5Actuals,double goal5PayoutPercent,
                                     double goal5PayoutAmount,double totalPayoutPercent,double totalPayoutAmount,
                                     double proratedPayoutPercent,double proratedPayoutAmount,String lastUpdated) {

        this.addReplace = addReplace;
        this.fiscalYear = fiscalYear;
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.region = region;
        this.location = location;
        this.department = department;
        this.jobCode = jobCode;
        this.jobProfile = jobProfile;
        this.businessTitle = businessTitle;
        this.timeInJob = timeInJob;
        this.recentHireDate = recentHireDate;
        this.jobPlanValidation = jobPlanValidation;
        this.salary = salary;
        this.status = status;
        this.reason = reason;
        this.incentivePlan = incentivePlan;
        this.daysOfEmployeement = daysOfEmployeement;
        this.planEligibilityStartDate = planEligibilityStartDate;
        this.planEligibilityEndDate = planEligibilityEndDate;
        this.daysInPlan = daysInPlan;
        this.proration = proration;
        this.performanceRating = performanceRating;
        this.overallIncentiveThreshold = overallIncentiveThreshold;
        this.overallIncentiveTarget = overallIncentiveTarget;
        this.overallIncentiveMaximum = overallIncentiveMaximum;
        this.goal1Name = goal1Name;
        this.goal1Weight = goal1Weight;
        this.goal1Threshold = goal1Threshold;
        this.goal1IncentiveOppertunityAtThresholdPercent = goal1IncentiveOppertunityAtThresholdPercent;
        this.goal1Maximum = goal1Maximum;
        this.goal1IncentiveOppertunityAtMaximumPercent = goal1IncentiveOppertunityAtMaximumPercent;
        this.goal1Target = goal1Target;
        this.goal1IncentiveOppertunityAtTargetPercent = goal1IncentiveOppertunityAtTargetPercent;
        this.goal1Actuals = goal1Actuals;
        this.goal1PayoutPercent = goal1PayoutPercent;
        this.goal1PayoutAmount = goal1PayoutAmount;
        this.goal2Name = goal2Name;
        this.goal2Weight = goal2Weight;
        this.goal2Threshold = goal2Threshold;
        this.goal2IncentiveOppertunityAtThresholdPercent = goal2IncentiveOppertunityAtThresholdPercent;
        this.goal2Maximum = goal2Maximum;
        this.goal2IncentiveOppertunityAtMaximumPercent = goal2IncentiveOppertunityAtMaximumPercent;
        this.goal2Target = goal2Target;
        this.goal2IncentiveOppertunityAtTargetPercent = goal2IncentiveOppertunityAtTargetPercent;
        this.goal2Actuals = goal2Actuals;
        this.goal2PayoutPercent = goal2PayoutPercent;
        this.goal2PayoutAmount = goal2PayoutAmount;
        this.goal3Name = goal3Name;
        this.goal3Weight = goal3Weight;
        this.goal3Threshold = goal3Threshold;
        this.goal3IncentiveOppertunityAtThresholdPercent = goal3IncentiveOppertunityAtThresholdPercent;
        this.goal3Maximum = goal3Maximum;
        this.goal3IncentiveOppertunityAtMaximumPercent = goal3IncentiveOppertunityAtMaximumPercent;
        this.goal3Target = goal3Target;
        this.goal3IncentiveOppertunityAtTargetPercent = goal3IncentiveOppertunityAtTargetPercent;
        this.goal3Actuals = goal3Actuals;
        this.goal3PayoutPercent = goal3PayoutPercent;
        this.goal3PayoutAmount = goal3PayoutAmount;
        this.goal4Name = goal4Name;
        this.goal4Weight = goal4Weight;
        this.goal4Threshold = goal4Threshold;
        this.goal4IncentiveOppertunityAtThresholdPercent = goal4IncentiveOppertunityAtThresholdPercent;
        this.goal4Maximum = goal4Maximum;
        this.goal4IncentiveOppertunityAtMaximumPercent = goal4IncentiveOppertunityAtMaximumPercent;
        this.goal4Target = goal4Target;
        this.goal4IncentiveOppertunityAtTargetPercent = goal4IncentiveOppertunityAtTargetPercent;
        this.goal4Actuals = goal4Actuals;
        this.goal4PayoutPercent = goal4PayoutPercent;
        this.goal4PayoutAmount = goal4PayoutAmount;
        this.goal5Name = goal5Name;
        this.goal5Weight = goal5Weight;
        this.goal5Threshold = goal5Threshold;
        this.goal5IncentiveOppertunityAtThresholdPercent = goal5IncentiveOppertunityAtThresholdPercent;
        this.goal5Maximum = goal5Maximum;
        this.goal5IncentiveOppertunityAtMaximumPercent = goal5IncentiveOppertunityAtMaximumPercent;
        this.goal5Target = goal5Target;
        this.goal5IncentiveOppertunityAtTargetPercent = goal5IncentiveOppertunityAtTargetPercent;
        this.goal5Actuals = goal5Actuals;
        this.goal5PayoutPercent = goal5PayoutPercent;
        this.goal5PayoutAmount = goal5PayoutAmount;
        this.totalPayoutPercent = totalPayoutPercent;
        this.totalPayoutAmount = totalPayoutAmount;
        this.proratedPayoutPercent = proratedPayoutPercent;
        this.proratedPayoutAmount = proratedPayoutAmount;
        this.lastUpdated = lastUpdated;
    }

    public String getAddReplace() {
        return addReplace;
    }

    public void setAddReplace(String addReplace) {
        this.addReplace = addReplace;
    }

    public String getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(String fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }

    public String getBusinessTitle() {
        return businessTitle;
    }

    public void setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
    }

    public Date getTimeInJob() {
        return timeInJob;
    }

    public void setTimeInJob(Date timeInJob) {
        this.timeInJob = timeInJob;
    }

    public Date getRecentHireDate() {
        return recentHireDate;
    }

    public void setRecentHireDate(Date recentHireDate) {
        this.recentHireDate = recentHireDate;
    }

    public String getJobPlanValidation() {
        return jobPlanValidation;
    }

    public void setJobPlanValidation(String jobPlanValidation) {
        this.jobPlanValidation = jobPlanValidation;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIncentivePlan() {
        return incentivePlan;
    }

    public void setIncentivePlan(String incentivePlan) {
        this.incentivePlan = incentivePlan;
    }

    public int getDaysOfEmployeement() {
        return daysOfEmployeement;
    }

    public void setDaysOfEmployeement(int daysOfEmployeement) {
        this.daysOfEmployeement = daysOfEmployeement;
    }

    public Date getPlanEligibilityStartDate() {
        return planEligibilityStartDate;
    }

    public void setPlanEligibilityStartDate(Date planEligibilityStartDate) {
        this.planEligibilityStartDate = planEligibilityStartDate;
    }

    public Date getPlanEligibilityEndDate() {
        return planEligibilityEndDate;
    }

    public void setPlanEligibilityEndDate(Date planEligibilityEndDate) {
        this.planEligibilityEndDate = planEligibilityEndDate;
    }

    public double getDaysInPlan() {
        return daysInPlan;
    }

    public void setDaysInPlan(double daysInPlan) {
        this.daysInPlan = daysInPlan;
    }

    public double getProration() {
        return proration;
    }

    public void setProration(double proration) {
        this.proration = proration;
    }

    public String getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(String performanceRating) {
        this.performanceRating = performanceRating;
    }

    public double getOverallIncentiveThreshold() {
        return overallIncentiveThreshold;
    }

    public void setOverallIncentiveThreshold(double overallIncentiveThreshold) {
        this.overallIncentiveThreshold = overallIncentiveThreshold;
    }

    public double getOverallIncentiveTarget() {
        return overallIncentiveTarget;
    }

    public void setOverallIncentiveTarget(double overallIncentiveTarget) {
        this.overallIncentiveTarget = overallIncentiveTarget;
    }

    public double getOverallIncentiveMaximum() {
        return overallIncentiveMaximum;
    }

    public void setOverallIncentiveMaximum(double overallIncentiveMaximum) {
        this.overallIncentiveMaximum = overallIncentiveMaximum;
    }

    public String getGoal1Name() {
        return goal1Name;
    }

    public void setGoal1Name(String goal1Name) {
        this.goal1Name = goal1Name;
    }

    public double getGoal1Weight() {
        return goal1Weight;
    }

    public void setGoal1Weight(double goal1Weight) {
        this.goal1Weight = goal1Weight;
    }

    public double getGoal1Threshold() {
        return goal1Threshold;
    }

    public void setGoal1Threshold(double goal1Threshold) {
        this.goal1Threshold = goal1Threshold;
    }

    public double getGoal1IncentiveOppertunityAtThresholdPercent() {
        return goal1IncentiveOppertunityAtThresholdPercent;
    }

    public void setGoal1IncentiveOppertunityAtThresholdPercent(double goal1IncentiveOppertunityAtThresholdPercent) {
        this.goal1IncentiveOppertunityAtThresholdPercent = goal1IncentiveOppertunityAtThresholdPercent;
    }

    public double getGoal1Maximum() {
        return goal1Maximum;
    }

    public void setGoal1Maximum(double goal1Maximum) {
        this.goal1Maximum = goal1Maximum;
    }

    public double getGoal1IncentiveOppertunityAtMaximumPercent() {
        return goal1IncentiveOppertunityAtMaximumPercent;
    }

    public void setGoal1IncentiveOppertunityAtMaximumPercent(double goal1IncentiveOppertunityAtMaximumPercent) {
        this.goal1IncentiveOppertunityAtMaximumPercent = goal1IncentiveOppertunityAtMaximumPercent;
    }

    public double getGoal1Target() {
        return goal1Target;
    }

    public void setGoal1Target(double goal1Target) {
        this.goal1Target = goal1Target;
    }

    public double getGoal1IncentiveOppertunityAtTargetPercent() {
        return goal1IncentiveOppertunityAtTargetPercent;
    }

    public void setGoal1IncentiveOppertunityAtTargetPercent(double goal1IncentiveOppertunityAtTargetPercent) {
        this.goal1IncentiveOppertunityAtTargetPercent = goal1IncentiveOppertunityAtTargetPercent;
    }

    public double getGoal1Actuals() {
        return goal1Actuals;
    }

    public void setGoal1Actuals(double goal1Actuals) {
        this.goal1Actuals = goal1Actuals;
    }

    public double getGoal1PayoutPercent() {
        return goal1PayoutPercent;
    }

    public void setGoal1PayoutPercent(double goal1PayoutPercent) {
        this.goal1PayoutPercent = goal1PayoutPercent;
    }

    public double getGoal1PayoutAmount() {
        return goal1PayoutAmount;
    }

    public void setGoal1PayoutAmount(double goal1PayoutAmount) {
        this.goal1PayoutAmount = goal1PayoutAmount;
    }

    public String getGoal2Name() {
        return goal2Name;
    }

    public void setGoal2Name(String goal2Name) {
        this.goal2Name = goal2Name;
    }

    public double getGoal2Weight() {
        return goal2Weight;
    }

    public void setGoal2Weight(double goal2Weight) {
        this.goal2Weight = goal2Weight;
    }

    public double getGoal2Threshold() {
        return goal2Threshold;
    }

    public void setGoal2Threshold(double goal2Threshold) {
        this.goal2Threshold = goal2Threshold;
    }

    public double getGoal2IncentiveOppertunityAtThresholdPercent() {
        return goal2IncentiveOppertunityAtThresholdPercent;
    }

    public void setGoal2IncentiveOppertunityAtThresholdPercent(double goal2IncentiveOppertunityAtThresholdPercent) {
        this.goal2IncentiveOppertunityAtThresholdPercent = goal2IncentiveOppertunityAtThresholdPercent;
    }

    public double getGoal2Maximum() {
        return goal2Maximum;
    }

    public void setGoal2Maximum(double goal2Maximum) {
        this.goal2Maximum = goal2Maximum;
    }

    public double getGoal2IncentiveOppertunityAtMaximumPercent() {
        return goal2IncentiveOppertunityAtMaximumPercent;
    }

    public void setGoal2IncentiveOppertunityAtMaximumPercent(double goal2IncentiveOppertunityAtMaximumPercent) {
        this.goal2IncentiveOppertunityAtMaximumPercent = goal2IncentiveOppertunityAtMaximumPercent;
    }

    public double getGoal2Target() {
        return goal2Target;
    }

    public void setGoal2Target(double goal2Target) {
        this.goal2Target = goal2Target;
    }

    public double getGoal2IncentiveOppertunityAtTargetPercent() {
        return goal2IncentiveOppertunityAtTargetPercent;
    }

    public void setGoal2IncentiveOppertunityAtTargetPercent(double goal2IncentiveOppertunityAtTargetPercent) {
        this.goal2IncentiveOppertunityAtTargetPercent = goal2IncentiveOppertunityAtTargetPercent;
    }

    public double getGoal2Actuals() {
        return goal2Actuals;
    }

    public void setGoal2Actuals(double goal2Actuals) {
        this.goal2Actuals = goal2Actuals;
    }

    public double getGoal2PayoutPercent() {
        return goal2PayoutPercent;
    }

    public void setGoal2PayoutPercent(double goal2PayoutPercent) {
        this.goal2PayoutPercent = goal2PayoutPercent;
    }

    public double getGoal2PayoutAmount() {
        return goal2PayoutAmount;
    }

    public void setGoal2PayoutAmount(double goal2PayoutAmount) {
        this.goal2PayoutAmount = goal2PayoutAmount;
    }

    public String getGoal3Name() {
        return goal3Name;
    }

    public void setGoal3Name(String goal3Name) {
        this.goal3Name = goal3Name;
    }

    public double getGoal3Weight() {
        return goal3Weight;
    }

    public void setGoal3Weight(double goal3Weight) {
        this.goal3Weight = goal3Weight;
    }

    public double getGoal3Threshold() {
        return goal3Threshold;
    }

    public void setGoal3Threshold(double goal3Threshold) {
        this.goal3Threshold = goal3Threshold;
    }

    public double getGoal3IncentiveOppertunityAtThresholdPercent() {
        return goal3IncentiveOppertunityAtThresholdPercent;
    }

    public void setGoal3IncentiveOppertunityAtThresholdPercent(double goal3IncentiveOppertunityAtThresholdPercent) {
        this.goal3IncentiveOppertunityAtThresholdPercent = goal3IncentiveOppertunityAtThresholdPercent;
    }

    public double getGoal3Maximum() {
        return goal3Maximum;
    }

    public void setGoal3Maximum(double goal3Maximum) {
        this.goal3Maximum = goal3Maximum;
    }

    public double getGoal3IncentiveOppertunityAtMaximumPercent() {
        return goal3IncentiveOppertunityAtMaximumPercent;
    }

    public void setGoal3IncentiveOppertunityAtMaximumPercent(double goal3IncentiveOppertunityAtMaximumPercent) {
        this.goal3IncentiveOppertunityAtMaximumPercent = goal3IncentiveOppertunityAtMaximumPercent;
    }

    public double getGoal3Target() {
        return goal3Target;
    }

    public void setGoal3Target(double goal3Target) {
        this.goal3Target = goal3Target;
    }

    public double getGoal3IncentiveOppertunityAtTargetPercent() {
        return goal3IncentiveOppertunityAtTargetPercent;
    }

    public void setGoal3IncentiveOppertunityAtTargetPercent(double goal3IncentiveOppertunityAtTargetPercent) {
        this.goal3IncentiveOppertunityAtTargetPercent = goal3IncentiveOppertunityAtTargetPercent;
    }

    public double getGoal3Actuals() {
        return goal3Actuals;
    }

    public void setGoal3Actuals(double goal3Actuals) {
        this.goal3Actuals = goal3Actuals;
    }

    public double getGoal3PayoutPercent() {
        return goal3PayoutPercent;
    }

    public void setGoal3PayoutPercent(double goal3PayoutPercent) {
        this.goal3PayoutPercent = goal3PayoutPercent;
    }

    public double getGoal3PayoutAmount() {
        return goal3PayoutAmount;
    }

    public void setGoal3PayoutAmount(double goal3PayoutAmount) {
        this.goal3PayoutAmount = goal3PayoutAmount;
    }

    public String getGoal4Name() {
        return goal4Name;
    }

    public void setGoal4Name(String goal4Name) {
        this.goal4Name = goal4Name;
    }

    public double getGoal4Weight() {
        return goal4Weight;
    }

    public void setGoal4Weight(double goal4Weight) {
        this.goal4Weight = goal4Weight;
    }

    public double getGoal4Threshold() {
        return goal4Threshold;
    }

    public void setGoal4Threshold(double goal4Threshold) {
        this.goal4Threshold = goal4Threshold;
    }

    public double getGoal4IncentiveOppertunityAtThresholdPercent() {
        return goal4IncentiveOppertunityAtThresholdPercent;
    }

    public void setGoal4IncentiveOppertunityAtThresholdPercent(double goal4IncentiveOppertunityAtThresholdPercent) {
        this.goal4IncentiveOppertunityAtThresholdPercent = goal4IncentiveOppertunityAtThresholdPercent;
    }

    public double getGoal4Maximum() {
        return goal4Maximum;
    }

    public void setGoal4Maximum(double goal4Maximum) {
        this.goal4Maximum = goal4Maximum;
    }

    public double getGoal4IncentiveOppertunityAtMaximumPercent() {
        return goal4IncentiveOppertunityAtMaximumPercent;
    }

    public void setGoal4IncentiveOppertunityAtMaximumPercent(double goal4IncentiveOppertunityAtMaximumPercent) {
        this.goal4IncentiveOppertunityAtMaximumPercent = goal4IncentiveOppertunityAtMaximumPercent;
    }

    public double getGoal4Target() {
        return goal4Target;
    }

    public void setGoal4Target(double goal4Target) {
        this.goal4Target = goal4Target;
    }

    public double getGoal4IncentiveOppertunityAtTargetPercent() {
        return goal4IncentiveOppertunityAtTargetPercent;
    }

    public void setGoal4IncentiveOppertunityAtTargetPercent(double goal4IncentiveOppertunityAtTargetPercent) {
        this.goal4IncentiveOppertunityAtTargetPercent = goal4IncentiveOppertunityAtTargetPercent;
    }

    public double getGoal4Actuals() {
        return goal4Actuals;
    }

    public void setGoal4Actuals(double goal4Actuals) {
        this.goal4Actuals = goal4Actuals;
    }

    public double getGoal4PayoutPercent() {
        return goal4PayoutPercent;
    }

    public void setGoal4PayoutPercent(double goal4PayoutPercent) {
        this.goal4PayoutPercent = goal4PayoutPercent;
    }

    public double getGoal4PayoutAmount() {
        return goal4PayoutAmount;
    }

    public void setGoal4PayoutAmount(double goal4PayoutAmount) {
        this.goal4PayoutAmount = goal4PayoutAmount;
    }

    public String getGoal5Name() {
        return goal5Name;
    }

    public void setGoal5Name(String goal5Name) {
        this.goal5Name = goal5Name;
    }

    public double getGoal5Weight() {
        return goal5Weight;
    }

    public void setGoal5Weight(double goal5Weight) {
        this.goal5Weight = goal5Weight;
    }

    public double getGoal5Threshold() {
        return goal5Threshold;
    }

    public void setGoal5Threshold(double goal5Threshold) {
        this.goal5Threshold = goal5Threshold;
    }

    public double getGoal5IncentiveOppertunityAtThresholdPercent() {
        return goal5IncentiveOppertunityAtThresholdPercent;
    }

    public void setGoal5IncentiveOppertunityAtThresholdPercent(double goal5IncentiveOppertunityAtThresholdPercent) {
        this.goal5IncentiveOppertunityAtThresholdPercent = goal5IncentiveOppertunityAtThresholdPercent;
    }

    public double getGoal5Maximum() {
        return goal5Maximum;
    }

    public void setGoal5Maximum(double goal5Maximum) {
        this.goal5Maximum = goal5Maximum;
    }

    public double getGoal5IncentiveOppertunityAtMaximumPercent() {
        return goal5IncentiveOppertunityAtMaximumPercent;
    }

    public void setGoal5IncentiveOppertunityAtMaximumPercent(double goal5IncentiveOppertunityAtMaximumPercent) {
        this.goal5IncentiveOppertunityAtMaximumPercent = goal5IncentiveOppertunityAtMaximumPercent;
    }

    public double getGoal5Target() {
        return goal5Target;
    }

    public void setGoal5Target(double goal5Target) {
        this.goal5Target = goal5Target;
    }

    public double getGoal5IncentiveOppertunityAtTargetPercent() {
        return goal5IncentiveOppertunityAtTargetPercent;
    }

    public void setGoal5IncentiveOppertunityAtTargetPercent(double goal5IncentiveOppertunityAtTargetPercent) {
        this.goal5IncentiveOppertunityAtTargetPercent = goal5IncentiveOppertunityAtTargetPercent;
    }

    public double getGoal5Actuals() {
        return goal5Actuals;
    }

    public void setGoal5Actuals(double goal5Actuals) {
        this.goal5Actuals = goal5Actuals;
    }

    public double getGoal5PayoutPercent() {
        return goal5PayoutPercent;
    }

    public void setGoal5PayoutPercent(double goal5PayoutPercent) {
        this.goal5PayoutPercent = goal5PayoutPercent;
    }

    public double getGoal5PayoutAmount() {
        return goal5PayoutAmount;
    }

    public void setGoal5PayoutAmount(double goal5PayoutAmount) {
        this.goal5PayoutAmount = goal5PayoutAmount;
    }

    public double getTotalPayoutPercent() {
        return totalPayoutPercent;
    }

    public void setTotalPayoutPercent(double totalPayoutPercent) {
        this.totalPayoutPercent = totalPayoutPercent;
    }

    public double getTotalPayoutAmount() {
        return totalPayoutAmount;
    }

    public void setTotalPayoutAmount(double totalPayoutAmount) {
        this.totalPayoutAmount = totalPayoutAmount;
    }

    public double getProratedPayoutPercent() {
        return proratedPayoutPercent;
    }

    public void setProratedPayoutPercent(double proratedPayoutPercent) {
        this.proratedPayoutPercent = proratedPayoutPercent;
    }

    public double getProratedPayoutAmount() {
        return proratedPayoutAmount;
    }

    public void setProratedPayoutAmount(double proratedPayoutAmount) {
        this.proratedPayoutAmount = proratedPayoutAmount;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
