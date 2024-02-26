package BST;
import java.util.ArrayList;

/**
 * The BSTMountain class extends the BST class's functionality, as well as implementing an
 * actual search algorithm to find the "safe paths" on the mountain.
 *
 * @author Shrey Khater
 * @version 12/5/22
 */
public class BSTMountain extends BST<RestStop>
{
    ArrayList<String> solutions;
    int treeHeight;

    /**
     * Default constructor for objets of class BSTMountain.
     */
    public BSTMountain()
    {
    }

    /**
     * Obtains the solutions a hiker can safely take down this BST mountain with
     * its supplies and obstacles.
     *
     * @param hiker the hiker traversing this mountain
     * @return the list of safe solutions, in string label format
     */
    public ArrayList<String> getSolutions(Hiker hiker)
    {
        solutions = new ArrayList<String>();
        treeHeight = this.height() - 1;
        solutionsRec(hiker, root, 0, "");
        return solutions;
    }

    /**
     * Recursive helper method for getSolutions which actually iterates through the mountain to find
     * safe solutions.
     *
     * @param hiker the hiker traversing this mountain
     * @param restStop the current RestStop node the hiker is at in this iteration of the recursion
     * @param steps the number of steps a hiker has taken down the mountain on the current path
     * @param curSolution the current solution of the path the hiker is traversing
     */
    public void solutionsRec(Hiker hiker, Node restStop, int steps, String curSolution)
    {
        if (restStop != null)
        {
            //Adds supplies from the current RestStop to the hiker
            for (String supply: restStop.data.getSupplies())
            {
                if (supply.equals("food")) {
                    hiker.increaseFood();
                } else if (supply.equals("raft")) {
                    hiker.increaseRafts();
                } else if (supply.equals("axe")) {
                    hiker.increaseAxe();
                }
            }

            //Checks if the hiker has the supplies to pass the obstacles at this RestStop, if any
            for (String obstacle: restStop.data.getObstacles())
            {
                if (obstacle.equals("fallen tree")) {
                    if (hiker.getNumAxes() == 0) {
                        return;
                    }
                    hiker.cutTree();
                }
                if (obstacle.equals("river")) {
                    if (hiker.getNumRafts() == 0) {
                        return;
                    }
                    hiker.crossRiver();
                }
            }

            //Checks if the hiker is at the bottom of the mountain, and if so, adds the current solution to the solution list.
            if (steps == treeHeight) {
                curSolution += restStop.data.getLabel();
                solutions.add(curSolution);
                return;
            }

            //Checks if the hiker has enough food to progress
            if (hiker.getNumFood() > 0)
            {
                hiker.useFood();
                //Stores the current amount of supplies the hiker has
                int curFood = hiker.getNumFood();
                int curAxes = hiker.getNumAxes();
                int curRafts = hiker.getNumRafts();
                steps++;
                //Performs the reccursion so that the hiker can check every path
                curSolution = curSolution + restStop.data.getLabel() + " ";
                solutionsRec(hiker, restStop.left, steps, curSolution);
                hiker.setSupplies(curFood, curAxes, curRafts);
                solutionsRec(hiker, restStop.right, steps, curSolution);
            }
        }
    }
}
