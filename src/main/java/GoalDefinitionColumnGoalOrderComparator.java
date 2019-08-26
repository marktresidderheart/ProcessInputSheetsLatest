import java.util.Comparator;

public class GoalDefinitionColumnGoalOrderComparator implements Comparator<GoalDefinition>  {

    public int compare(GoalDefinition goalColumnDef1, GoalDefinition goalColumnDef2) {
        return goalColumnDef1.getColumnGoalOrder() - goalColumnDef2.getColumnGoalOrder();
    }

}
