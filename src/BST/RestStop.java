package BST;
import java.util.*;

/**
 * RestStops act as nodes in the BSTMountain class, and store arrays of supplies and obstacles that
 * hiker objects can pick up, as well as a label which serves as the rest stop's name
 *
 * @author Shrey Khater
 * @version 12/6/22
 */
public class RestStop implements Comparable<RestStop>
{
    private ArrayList<String> supplies;
    private ArrayList<String> obstacles;
    private String label;

    /**
     * Constructor for objects of class RestStop. Initializes instance variables.
     */
    public RestStop()
    {
        supplies = null;
        obstacles = null;
        label = null;
    }

    /**
     * Multiple-parameter Constructor for objects of class RestStop. Initializes instance variables to preset values.
     *
     * @param supplies the list of supplies
     * @param obstacles the list of obstacles
     * @param label the name of the rest stop
     */
    public RestStop(ArrayList<String> supplies, ArrayList<String> obstacles, String label)
    {
        this.supplies = supplies;
        this.obstacles = obstacles;
        this.label = label;
    }

    /**
     * Sets the list of supplies to a new list.
     *
     * @param supplies the new list of supplies
     */
    public void setSupplies(ArrayList<String> supplies)
    {
        this.supplies = supplies;
    }

    /**
     * Sets the list of obstacles to a new list.
     *
     * @param supplies the new list of obstacles
     */
    public void setObstacles(ArrayList<String> supplies)
    {
        this.obstacles = obstacles;
    }

    /**
     * Sets the label of this RestStop to a new name.
     *
     * @param label the new name
     */
    public void setLabel(String label)
    {
        this.label = label;
    }

    /**
     * Obtains the name of this RestStop.
     *
     * @return the name
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * Obtains the list of supplies of this RestStop.
     *
     * @return the list of supplies
     */
    public ArrayList<String> getSupplies()
    {
        return supplies;
    }

    /**
     * Obtains the list of obstacles of this RestStop.
     *
     * @return the list of obstacles
     */
    public ArrayList<String> getObstacles()
    {
        return obstacles;
    }

    /**
     * Compares this RestStop to another RestStop using their labels.
     *
     * @param other the RestStop to be compared
     * @return 1 if this RestStop is bigger, -1 if this RestStop is smaller, and 0 if the two RestStops are equal
     */
    @Override
    public int compareTo(RestStop other)
    {
        if (this.label == null || other == null || other.label == null)
        {
            throw new NullPointerException("cannot compare with null rest stops");
        }
        return this.label.compareTo(other.label);
    }

    /**
     * ToString representation of a RestStop, (its label).
     *
     * @return the label
     */
    @Override
    public String toString()
    {
        return label;
    }
}
