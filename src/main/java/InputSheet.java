import java.util.Date;

public class InputSheet {

    private String addReplace;
    private String fiscalYear;
    private String incentivePlan;
    private String employeeName;
    private String jobProfile;
    private Date timeInJob;
    private Date planEligibilityStartDate;
    private Date planEligibilityEndDate;
    private String businessUnitLabel;
    private double businessUnitMaximumGoal;
    private double businessUnitTargetGoal;
    private double businessUnitThresholdGoal;
    private double rqiMaximumGoal;
    private double rqiTargetGoal;
    private double rqiThresholdGoal;
    private double sbrMaximumGoal;
    private double sbrTarget;
    private double sbrThresholdGoal;
    private String surplusLabel;
    private double surplusMaximumGoal;
    private double surplusTargetGoal;
    private double surplusThresholdGoal;
    private double awrMaximumGoal;
    private double awrTargetGoal;
    private double awrThresholdGoal;
    private double majorGiftsMaximumGoal;
    private double majorGiftsTargetGoal;
    private double majorGiftsThresholdGoal;
    private String individualGoalLabel;
    private double individualGoalMaximumGoal;
    private double individualGoalTargetGoal;
    private double individualGoalThresholdGoal;
    private String teamworkLabel;
    private double teamworkMaximumGoal;
    private double teamworkTargetGoal;
    private double teamworkThresholdGoal;
    private String inItTogetherLabel;
    private double inItTogetherMaximumGoal;
    private double inItTogetherTargetGoal;
    private double inItTogetherThresholdGoal;
    private String employeeID;
    private String region;
    private String location;
    private String department;
    private String jobCode;
    private String businessTitle;
    private Date hireDate;

    public InputSheet () {
        this.addReplace = "";
        this.fiscalYear = "";
        this.incentivePlan = "";
        this.employeeName = "";
        this.jobProfile = "";
        this.businessUnitLabel = "";
        this.businessUnitMaximumGoal = 0d;
        this.businessUnitTargetGoal = 0d;
        this.businessUnitThresholdGoal = 0d;
        this.rqiMaximumGoal = 0d;
        this.rqiTargetGoal = 0d;
        this.rqiThresholdGoal = 0d;
        this.sbrMaximumGoal = 0d;
        this.sbrTarget = 0d;
        this.sbrThresholdGoal = 0d;
        this.surplusLabel = "";
        this.surplusMaximumGoal = 0d;
        this.surplusTargetGoal = 0d;
        this.surplusThresholdGoal = 0d;
        this.awrMaximumGoal = 0d;
        this.awrTargetGoal = 0d;
        this.awrThresholdGoal = 0d;
        this.majorGiftsMaximumGoal = 0d;
        this.majorGiftsTargetGoal = 0d;
        this.majorGiftsThresholdGoal = 0d;
        this.individualGoalLabel = "";
        this.individualGoalMaximumGoal = 0d;
        this.individualGoalTargetGoal = 0d;
        this.individualGoalThresholdGoal = 0d;
        this.teamworkLabel = "";
        this.teamworkMaximumGoal = 0d;
        this.teamworkTargetGoal = 0d;
        this.teamworkThresholdGoal = 0d;
        this.inItTogetherLabel = "";
        this.inItTogetherMaximumGoal = 0d;
        this.inItTogetherTargetGoal = 0d;
        this.inItTogetherThresholdGoal = 0d;
        this.employeeID = "";
        this.region = "";
        this.location = "";
        this.department = "";
        this.jobCode = "";
        this.businessTitle = "";
    }

    public InputSheet (String addReplace,String fiscalYear,String incentivePlan,String employeeName,String jobProfile,
                       Date timeInJob,Date planEligibilityStartDate,Date planEligibilityEndDate,String businessUnitLabel,
                       double businessUnitMaximumGoal,double businessUnitTargetGoal,double businessUnitThresholdGoal,
                       double rqiMaximumGoal,double rqiTargetGoal,double rqiThresholdGoal,double sbrMaximumGoal,
                       double sbrTarget,double sbrThresholdGoal,String surplusLabel,double surplusMaximumGoal,
                       double surplusTargetGoal,double surplusThresholdGoal,double awrMaximumGoal,double awrTargetGoal,
                       double awrThresholdGoal,double majorGiftsMaximumGoal,double majorGiftsTargetGoal,
                       double majorGiftsThresholdGoal,String individualGoalLabel,double individualGoalMaximumGoal,
                       double individualGoalTargetGoal,double individualGoalThresholdGoal,String teamworkLabel,
                       double teamworkMaximumGoal,double teamworkTargetGoal,double teamworkThresholdGoal,
                       String inItTogetherLabel,double inItTogetherMaximumGoal,double inItTogetherTargetGoal,
                       double inItTogetherThresholdGoal,String employeeID,String region,String location,String department, String jobCode,
                       String businessTitle,Date hireDate) {

        this.addReplace = addReplace;
        this.fiscalYear = fiscalYear;
        this.incentivePlan = incentivePlan;
        this.employeeName = employeeName;
        this.jobProfile = jobProfile;
        this.timeInJob = timeInJob;
        this.planEligibilityStartDate = planEligibilityStartDate;
        this.planEligibilityEndDate = planEligibilityEndDate;
        this.businessUnitLabel = businessUnitLabel;
        this.businessUnitMaximumGoal = businessUnitMaximumGoal;
        this.businessUnitTargetGoal = businessUnitTargetGoal;
        this.businessUnitThresholdGoal = businessUnitThresholdGoal;
        this.rqiMaximumGoal = rqiMaximumGoal;
        this.rqiTargetGoal = rqiTargetGoal;
        this.rqiThresholdGoal = rqiThresholdGoal;
        this.sbrMaximumGoal = sbrMaximumGoal;
        this.sbrTarget = sbrTarget;
        this.sbrThresholdGoal = sbrThresholdGoal;
        this.surplusLabel = surplusLabel;
        this.surplusMaximumGoal = surplusMaximumGoal;
        this.surplusTargetGoal = surplusTargetGoal;
        this.surplusThresholdGoal = surplusThresholdGoal;
        this.awrMaximumGoal = awrMaximumGoal;
        this.awrTargetGoal = awrTargetGoal;
        this.awrThresholdGoal = awrThresholdGoal;
        this.majorGiftsMaximumGoal = majorGiftsMaximumGoal;
        this.majorGiftsTargetGoal = majorGiftsTargetGoal;
        this.majorGiftsThresholdGoal = majorGiftsThresholdGoal;
        this.individualGoalLabel = individualGoalLabel;
        this.individualGoalMaximumGoal = individualGoalMaximumGoal;
        this.individualGoalTargetGoal = individualGoalTargetGoal;
        this.individualGoalThresholdGoal = individualGoalThresholdGoal;
        this.teamworkLabel = teamworkLabel;
        this.teamworkMaximumGoal = teamworkMaximumGoal;
        this.teamworkTargetGoal = teamworkTargetGoal;
        this.teamworkThresholdGoal = teamworkThresholdGoal;
        this.inItTogetherLabel = inItTogetherLabel;
        this.inItTogetherMaximumGoal = inItTogetherMaximumGoal;
        this.inItTogetherTargetGoal = inItTogetherTargetGoal;
        this.inItTogetherThresholdGoal = inItTogetherThresholdGoal;
        this.employeeID = employeeID;
        this.region = region;
        this.location = location;
        this.department = department;
        this.jobCode = jobCode;
        this.businessTitle = businessTitle;
        this.hireDate = hireDate;
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

    public String getIncentivePlan() {
        return incentivePlan;
    }

    public void setIncentivePlan(String incentivePlan) {
        this.incentivePlan = incentivePlan;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }

    public Date getTimeInJob() {
        return timeInJob;
    }

    public void setTimeInJob(Date timeInJob) {
        this.timeInJob = timeInJob;
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

    public String getBusinessUnitLabel() {
        return businessUnitLabel;
    }

    public void setBusinessUnitLabel(String businessUnitLabel) {
        this.businessUnitLabel = businessUnitLabel;
    }

    public double getBusinessUnitMaximumGoal() {
        return businessUnitMaximumGoal;
    }

    public void setBusinessUnitMaximumGoal(double businessUnitMaximumGoal) {
        this.businessUnitMaximumGoal = businessUnitMaximumGoal;
    }

    public double getBusinessUnitTargetGoal() {
        return businessUnitTargetGoal;
    }

    public void setBusinessUnitTargetGoal(double businessUnitTargetGoal) {
        this.businessUnitTargetGoal = businessUnitTargetGoal;
    }

    public double getBusinessUnitThresholdGoal() {
        return businessUnitThresholdGoal;
    }

    public void setBusinessUnitThresholdGoal(double businessUnitThresholdGoal) {
        this.businessUnitThresholdGoal = businessUnitThresholdGoal;
    }

    public double getRqiMaximumGoal() {
        return rqiMaximumGoal;
    }

    public void setRqiMaximumGoal(double rqiMaximumGoal) {
        this.rqiMaximumGoal = rqiMaximumGoal;
    }

    public double getRqiTargetGoal() {
        return rqiTargetGoal;
    }

    public void setRqiTargetGoal(double rqiTargetGoal) {
        this.rqiTargetGoal = rqiTargetGoal;
    }

    public double getRqiThresholdGoal() {
        return rqiThresholdGoal;
    }

    public void setRqiThresholdGoal(double rqiThresholdGoal) {
        this.rqiThresholdGoal = rqiThresholdGoal;
    }

    public double getSbrMaximumGoal() {
        return sbrMaximumGoal;
    }

    public void setSbrMaximumGoal(double sbrMaximumGoal) {
        this.sbrMaximumGoal = sbrMaximumGoal;
    }

    public double getSbrTarget() {
        return sbrTarget;
    }

    public void setSbrTarget(double sbrTarget) {
        this.sbrTarget = sbrTarget;
    }

    public double getSbrThresholdGoal() {
        return sbrThresholdGoal;
    }

    public void setSbrThresholdGoal(double sbrThresholdGoal) {
        this.sbrThresholdGoal = sbrThresholdGoal;
    }

    public String getSurplusLabel() {
        return surplusLabel;
    }

    public void setSurplusLabel(String surplusLabel) {
        this.surplusLabel = surplusLabel;
    }

    public double getSurplusMaximumGoal() {
        return surplusMaximumGoal;
    }

    public void setSurplusMaximumGoal(double surplusMaximumGoal) {
        this.surplusMaximumGoal = surplusMaximumGoal;
    }

    public double getSurplusTargetGoal() {
        return surplusTargetGoal;
    }

    public void setSurplusTargetGoal(double surplusTargetGoal) {
        this.surplusTargetGoal = surplusTargetGoal;
    }

    public double getSurplusThresholdGoal() {
        return surplusThresholdGoal;
    }

    public void setSurplusThresholdGoal(double surplusThresholdGoal) {
        this.surplusThresholdGoal = surplusThresholdGoal;
    }

    public double getAwrMaximumGoal() {
        return awrMaximumGoal;
    }

    public void setAwrMaximumGoal(double awrMaximumGoal) {
        this.awrMaximumGoal = awrMaximumGoal;
    }

    public double getAwrTargetGoal() {
        return awrTargetGoal;
    }

    public void setAwrTargetGoal(double awrTargetGoal) {
        this.awrTargetGoal = awrTargetGoal;
    }

    public double getAwrThresholdGoal() {
        return awrThresholdGoal;
    }

    public void setAwrThresholdGoal(double awrThresholdGoal) {
        this.awrThresholdGoal = awrThresholdGoal;
    }

    public double getMajorGiftsMaximumGoal() {
        return majorGiftsMaximumGoal;
    }

    public void setMajorGiftsMaximumGoal(double majorGiftsMaximumGoal) {
        this.majorGiftsMaximumGoal = majorGiftsMaximumGoal;
    }

    public double getMajorGiftsTargetGoal() {
        return majorGiftsTargetGoal;
    }

    public void setMajorGiftsTargetGoal(double majorGiftsTargetGoal) {
        this.majorGiftsTargetGoal = majorGiftsTargetGoal;
    }

    public double getMajorGiftsThresholdGoal() {
        return majorGiftsThresholdGoal;
    }

    public void setMajorGiftsThresholdGoal(double majorGiftsThresholdGoal) {
        this.majorGiftsThresholdGoal = majorGiftsThresholdGoal;
    }

    public String getIndividualGoalLabel() {
        return individualGoalLabel;
    }

    public void setIndividualGoalLabel(String individualGoalLabel) {
        this.individualGoalLabel = individualGoalLabel;
    }

    public double getIndividualGoalMaximumGoal() {
        return individualGoalMaximumGoal;
    }

    public void setIndividualGoalMaximumGoal(double individualGoalMaximumGoal) {
        this.individualGoalMaximumGoal = individualGoalMaximumGoal;
    }

    public double getIndividualGoalTargetGoal() {
        return individualGoalTargetGoal;
    }

    public void setIndividualGoalTargetGoal(double individualGoalTargetGoal) {
        this.individualGoalTargetGoal = individualGoalTargetGoal;
    }

    public double getIndividualGoalThresholdGoal() {
        return individualGoalThresholdGoal;
    }

    public void setIndividualGoalThresholdGoal(double individualGoalThresholdGoal) {
        this.individualGoalThresholdGoal = individualGoalThresholdGoal;
    }

    public String getTeamworkLabel() {
        return teamworkLabel;
    }

    public void setTeamworkLabel(String teamworkLabel) {
        this.teamworkLabel = teamworkLabel;
    }

    public double getTeamworkMaximumGoal() {
        return teamworkMaximumGoal;
    }

    public void setTeamworkMaximumGoal(double teamworkMaximumGoal) {
        this.teamworkMaximumGoal = teamworkMaximumGoal;
    }

    public double getTeamworkTargetGoal() {
        return teamworkTargetGoal;
    }

    public void setTeamworkTargetGoal(double teamworkTargetGoal) {
        this.teamworkTargetGoal = teamworkTargetGoal;
    }

    public double getTeamworkThresholdGoal() {
        return teamworkThresholdGoal;
    }

    public void setTeamworkThresholdGoal(double teamworkThresholdGoal) {
        this.teamworkThresholdGoal = teamworkThresholdGoal;
    }

    public String getInItTogetherLabel() {
        return inItTogetherLabel;
    }

    public void setInItTogetherLabel(String inItTogetherLabel) {
        this.inItTogetherLabel = inItTogetherLabel;
    }

    public double getInItTogetherMaximumGoal() {
        return inItTogetherMaximumGoal;
    }

    public void setInItTogetherMaximumGoal(double inItTogetherMaximumGoal) {
        this.inItTogetherMaximumGoal = inItTogetherMaximumGoal;
    }

    public double getInItTogetherTargetGoal() {
        return inItTogetherTargetGoal;
    }

    public void setInItTogetherTargetGoal(double inItTogetherTargetGoal) {
        this.inItTogetherTargetGoal = inItTogetherTargetGoal;
    }

    public double getInItTogetherThresholdGoal() {
        return inItTogetherThresholdGoal;
    }

    public void setInItTogetherThresholdGoal(double inItTogetherThresholdGoal) {
        this.inItTogetherThresholdGoal = inItTogetherThresholdGoal;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
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

    public String getBusinessTitle() {
        return businessTitle;
    }

    public void setBusinessTitle(String businessTitle) {
        this.businessTitle = businessTitle;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }
}
