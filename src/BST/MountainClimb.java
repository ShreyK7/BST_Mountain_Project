package BST;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * The MountainClimb class performs the actual running of this BSTMountain program given an input file. It
 * reads in the input file, constructs a BSTMountain based on the data, and then outputs the list of safe
 * solutions to the mountain.
 *
 * @author Shrey Khater
 * @version 12/5/22
 */
public class MountainClimb {

    /**
     * The main() method of this program.
     *
     * @param args the file argument from the command line to be read in
     */
    public static void main(String[] args)
    {
        //verify that the command line argument exists
        if (args.length == 0) {
            System.err.println("Program requires file name as input");
            System.exit(1);
        }

        //verify that the command line argument exists
        File file = new File(args[0]);
        if (!file.exists()) {
            System.err.println("The file " + file.getAbsolutePath() + " does not exist");
            System.exit(1);
        }
        if (!file.canRead()) {
            System.err.println("the file " + file.getAbsolutePath() + " can't be read");
            System.exit(1);
        }

        //open the file for reading
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("the file " + file.getAbsolutePath() + " can't be read");
            System.exit(1);
        }

        //read the content of the file and construct the appropriate BSTMountain
        ArrayList<RestStop> stops = new ArrayList<RestStop>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] elements = line.split(" ");
            String label = elements[0];
            //Adds the obstacles and supplies of a given RestStop to new lists and ensures only lines in the
            //correct format are read in (ie no supplies after obstacles)
            ArrayList<String> obstacles = new ArrayList<String>();
            ArrayList<String> supplies = new ArrayList<String>();
            int supplyIndex = 1;
            for (int i = 1; i < elements.length; i++)
            {
                if (isSupply(elements[i]))
                {
                    supplies.add(elements[i]);
                    supplyIndex++;
                }
                else if (isObstacle(elements[i]))
                {
                    break;
                }
            }
            for (int i = supplyIndex; i < elements.length; i++)
            {
                if (isObstacle(elements[i]))
                {
                    obstacles.add(elements[i]);
                }
            }
            for (int i = supplyIndex; i < elements.length - 1; i++)
            {
                if (elements[i].equals("fallen"))
                {
                    if (elements[i + 1].equals("tree"))
                    {
                        obstacles.add("fallen tree");
                    }
                }
            }
            RestStop stop = new RestStop(supplies, obstacles, label);
            stops.add(stop);
        }
        scanner.close();

        BSTMountain mountain = new BSTMountain();
        for (int i = 0; i < stops.size(); i++)
        {
            mountain.add(stops.get(i));
        }

        //Creates the list of safe solutions
        Hiker hiker = new Hiker();
        ArrayList<String> solutions = mountain.getSolutions(hiker);
        for (int i = 0; i < solutions.size(); i++)
        {
            System.out.println(solutions.get(i));
        }
        //Program terminates
    }

    /**
     * Checks if an inputted string is a valid obstacle in the BSTMountain (either "river"
     * or "fallen tree").
     *
     * @param string the inputted string to be checked
     * @return true if valid; otherwise,
     *         false
     */
    public static boolean isObstacle(String string)
    {
        return string.equalsIgnoreCase("river");
    }

    /**
     * Checks if an inputted string is a valid obstacle in the BSTMountain (either "raft",
     * "axe", or "food").
     *
     * @param string the inputted string to be checked
     * @return true if valid; otherwise,
     *         false
     */
    public static boolean isSupply(String string)
    {
        return string.equalsIgnoreCase("raft") || string.equalsIgnoreCase("axe")
                || string.equalsIgnoreCase("food");
    }
}
