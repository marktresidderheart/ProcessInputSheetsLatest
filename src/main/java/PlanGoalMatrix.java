public class PlanGoalMatrix {

    private Integer fiscalYear;
    private String incentivePlanName;
    private String planCategory;
    private double max;
    private double target;
    private double min;
    private String goalName;
    private double goalWeight;
    private double flatAmount;

    public PlanGoalMatrix() {
        this.fiscalYear = 0;
        this.incentivePlanName = "";
        this.planCategory = "";
        this.max = 0f;
        this.target = 0f;
        this.min = 0f;
        this.goalName = "";
        this.goalWeight = 0f;
        this.flatAmount = 0f;
    }

    public PlanGoalMatrix(Integer fiscalYear, String incentivePlanName, String planCategory, double max, double target, double min, String goalName, double goalWeight, double flatAmount) {
        this.fiscalYear = fiscalYear;
        this.incentivePlanName = incentivePlanName;
        this.planCategory = planCategory;
        this.max = max;
        this.target = target;
        this.min = min;
        this.goalName = goalName;
        this.goalWeight = goalWeight;
        this.flatAmount = flatAmount;
    }

    public Integer getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(Integer fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getIncentivePlanName() {
        return incentivePlanName;
    }

    public void setIncentivePlanName(String incentivePlanName) {
        this.incentivePlanName = incentivePlanName;
    }

    public String getPlanCategory() {
        return planCategory;
    }

    public void setPlanCategory(String planCategory) {
        this.planCategory = planCategory;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public double getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(double goalWeight) {
        this.goalWeight = goalWeight;
    }

    public double getFlatAmount() {
        return flatAmount;
    }

    public void setFlatAmount(double flatAmount) {
        this.flatAmount = flatAmount;
    }
}

