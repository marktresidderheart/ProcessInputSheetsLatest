public class GoalDefinitionColumnOrderComparator {

    public int compare(GoalDefinition goalColumnDef1, GoalDefinition goalColumnDef2) {
        return goalColumnDef1.getColumnOrder() - goalColumnDef2.getColumnOrder();
    }

}
