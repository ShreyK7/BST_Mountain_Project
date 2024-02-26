package BST;
import java.util.*;
public class Tester {
    public static void main(String[] args)
    {
        Integer[] elements = new Integer[10];
        for (int i = 0; i < elements.length; i++)
        {
            elements[i] = i;
        }
        BST<Integer> test = new BST<Integer>(elements);

        System.out.println(test.toStringTreeFormat());
        System.out.println(test);
    }
}
