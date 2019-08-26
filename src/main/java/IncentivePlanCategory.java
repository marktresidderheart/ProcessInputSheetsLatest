public class IncentivePlanCategory {

    private String employeeID;
    private String jobCode;
    private String jobProfile;
    private String incentivePlan;
    private String incentivePlanCategory;

    public IncentivePlanCategory() {
        this.employeeID = "";
        this.jobCode = "";
        this.jobProfile = "";
        this.incentivePlan = "";
        this.incentivePlanCategory = "";
    }

    public IncentivePlanCategory(String employeeID, String jobCode,String jobProfile, String incentivePlan, String incentivePlanCategory) {
        this.employeeID = employeeID;
        this.jobCode = jobCode;
        this.jobProfile = jobProfile;
        this.incentivePlan = incentivePlan;
        this.incentivePlanCategory = incentivePlanCategory;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
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

    public String getIncentivePlan() {
        return incentivePlan;
    }

    public void setIncentivePlan(String incentivePlan) {
        this.incentivePlan = incentivePlan;
    }

    public String getIncentivePlanCategory() {
        return incentivePlanCategory;
    }

    public void setIncentivePlanCategory(String incentivePlanCategory) {
        this.incentivePlanCategory = incentivePlanCategory;
    }
}
