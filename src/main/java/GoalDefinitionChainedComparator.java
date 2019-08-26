import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * This is a chained comparator that is used to sort a list by multiple
 * attributes by chaining a sequence of comparators of individual fields
 * together.  This comparator is specifically for the GoalDefinition Collection.
 */

public class GoalDefinitionChainedComparator implements Comparator<GoalDefinition> {

    private List<Comparator<GoalDefinition>> listComparators;

    @SafeVarargs
    public GoalDefinitionChainedComparator(Comparator<GoalDefinition>... comparators) {
        this.listComparators = Arrays.asList(comparators);
    }

    @Override
    public int compare(GoalDefinition goalColumnDefinition1, GoalDefinition goalColumnDefinition2) {
        for (Comparator<GoalDefinition> comparator : listComparators) {
            int result = comparator.compare(goalColumnDefinition1, goalColumnDefinition2);
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }


}
