package BST;

/**
 * Hikers store a number of supplies as instance variables: food, axes, and rafts (number of each).
 *
 * @author Shrey Khater
 * @version 12/5/22
 */
public class Hiker {
    private int numFood;
    private int numAxe;
    private int numRaft;

    /**
     * Constructor for objects of class Hiker, sets all supplies to 0.
     */
    public Hiker() {
        numFood = 0;
        numAxe = 0;
        numRaft = 0;
    }

    /**
     * Decreases food by one.
     */
    public void useFood() {
        numFood--;
    }

    /**
     * Decreases rafts by one.
     */
    public void crossRiver() {
        numRaft--;
    }

    /**
     * Decreases axes by one.
     */
    public void cutTree() {
        numAxe--;
    }

    /**
     * Increases food by one.
     */
    public void increaseFood() {
        numFood++;
    }

    /**
     * Increases rafts by one.
     */
    public void increaseRafts() {
        numRaft++;
    }

    /**
     * Increases axes by one.
     */
    public void increaseAxe() {
        numAxe++;
    }

    /**
     * Obtains the amount of food with this hiker.
     *
     * @return the amount of food
     */
    public int getNumFood() {
        return numFood;
    }

    /**
     * Obtains the amount of axes with this hiker.
     *
     * @return the amount of axes
     */
    public int getNumAxes() {
        return numAxe;
    }

    /**
     * Obtains the amount of rafts with this hiker.
     *
     * @return the amount of rafts
     */
    public int getNumRafts() {
        return numRaft;
    }

    /**
     * Sets the amount of supplies with the hiker to the inputted values.
     *
     * @param food the new amount of food
     * @param axes the new amount of axes
     * @param rafts the new amount of rafts
     */
    public void setSupplies(int food, int axes, int rafts)
    {
        numFood = food;
        numAxe = axes;
        numRaft = rafts;
    }
}