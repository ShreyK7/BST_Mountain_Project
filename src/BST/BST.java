package BST;
import java.util.*;
import java.util.Iterator;

/**
 * Binary Search Trees (BSTs) are a special type of tree whose nodes can only have up to two children.
 * Additionally, all the data elements stored in the left subtree of a BST node must be smaller than
 * the data of the given BST node, while all the elements in the right subtree must be bigger. This
 * BST class implements BST methods such as add, remove, get, etc and stores the size and root node of
 * the BST as instance variables, as well as has iterater subclasses and a node subclass.
 *
 * @param <E> the data type stored in the nodes of this BST
 *
 * @author Shrey Khater
 * @version 12/5/22
 */
public class BST<E extends Comparable<E>>
{
    /**
     * The Node class in this BST makes up the individual BST nodes of the tree, and store the left child
     * node, the right child node, and the data element stored in the node. Additionally, this BST node
     * also stores the height of the node in the tree, the size of the left subtree, and the size of both
     * subtrees as instance variables, in order to optimize the runtimes of methods in the broader BST class.
     *
     * @author Shrey Khater
     * @version 12/5/22
     */
    protected class Node implements Comparable<Node>
    {
        protected Node left;
        protected Node right;
        protected E data;
        protected int height;
        protected int leftTreeSize;
        protected int sizeSubTrees;

        /**
         * Constructor for objects of class Node. Sets all the instance variables to their base forms.
         *
         * @param data the data element of type E to be stored in this node
         */
        public Node(E data)
        {
            height = 1;
            left = null;
            right = null;
            this.data = data;
            leftTreeSize = 0;
            sizeSubTrees = 0;
        }

        /**
         * Multi-parameter constructor for objects of class Node.
         *
         * @param data the data element of type E to be stored in this node
         * @param left the left child node of this node
         * @param right the right child node of this node
         */
        public Node(E data, Node left, Node right)
        {
            height = 1;
            this.left = left;
            this.right = right;
            this.data = data;
            leftTreeSize = left.leftTreeSize + 1;
        }

        /**
         * Compares the element in this node to the element in another node.
         *
         * @param other the node to be compared
         *
         * @return 1 if the data in this node is greater than the data in the other node,
         *         -1 if the data in this node is smaller, and
         *         0 if the data are the same
         */
        public int compareTo(Node other)
        {
            return this.data.compareTo(other.data);
        }

        /**
         * Updates the height of this node by comparing it to the heights of its child nodes.
         */
        public void updateHeight()
        {
            if (left == null && right == null)
            {
                height = 1;
            }
            else if (left == null)
            {
                height = right.height + 1;
            }
            else if (right == null)
            {
                height = left.height + 1;
            }
            else
            {
                height = 1 + Math.max(left.height, right.height);
            }
        }

        /**
         * Updates the size of the left subtree of this node, as well as the total size of the 2
         * subtrees.
         */
        public void updateLeftTreeSize()
        {
            if (left == null && right == null)
            {
                leftTreeSize = 0;
                sizeSubTrees = 0;
            }
            else if (left == null)
            {
                leftTreeSize = 0;
                sizeSubTrees = right.sizeSubTrees+1;
            }
            else if (left.right == null)
            {
                leftTreeSize = left.leftTreeSize + 1;
                sizeSubTrees++;
            }
            else
            {
                leftTreeSize = left.sizeSubTrees + 1;
                sizeSubTrees++;
            }
        }
    }
    protected Node root;

    protected int size;

    /**
     * Constructor for BST objects.
     */
    public BST()
    {
        root = null;
    }

    /**
     * Contructor for BST objects using an already constructed array of elements of type E.
     * @param collection the array of elements to be added to this BST
     */
    public BST(E[] collection)
    {
        Arrays.sort(collection);
        balancedBST(collection, 0, collection.length - 1);
        size = collection.length;

    }

    /**
     * Recursive helper method for the E[] collection BST constructor.
     * @param collection  the array of elements to be added
     * @param start the start of the array in this recursive iteration
     * @param end the end of the array in this recursive iteration
     */
    public void balancedBST(E[] collection, int start, int end)
    {
        if (start > end)
        {
            return;
        }
        int middle = (start+end)/2;
        this.add(collection[middle]);
        balancedBST(collection, start,  middle-1);
        balancedBST(collection, middle+1, end);
    }


    private Boolean added;
    /**
     * The add method for adding an element to this BST (doesn't add duplicates).
     *
     * @param data the element to be added
     * @return true if the element was able ot be added; otherwise,
     *         false
     */
    public boolean add(E data)
    {
        added = false;
        if (data == null)
        {
            throw new NullPointerException ("null value found");
        }
        root = add(data, root);
        if (added)
        {
            size++;
            root.updateHeight();
            root.updateLeftTreeSize();
        }
        return added;
    }

    /**
     * Recursive helper method for add.
     *
     * @param data the element to be added
     * @param node the current node in the recursive iteration
     * @return a node with the added node in its subtree, if able to be added
     */
    private Node add(E data, Node node)
    {
        if (node == null)
        {
            added = true;
            return new Node(data);
        }
        if (data.compareTo(node.data) < 0 )
        {
            node.left = add(data, node.left);
            node.updateHeight();
            node.updateLeftTreeSize();
        }
        else if (data.compareTo(node.data) > 0)
        {
            node.right = add(data, node.right);
            node.updateHeight();
            node.updateLeftTreeSize();
        }
        else
        {
            added = false;
        }
        return node;
    }

    private Boolean found;
    /**
     * Removes a certain element from the BST, if already contained in the tree.
     *
     * @param o the element to be removed
     * @return true if the element was able to be removed; otherwise,
     *         false.
     */
    public boolean remove (Object o)
    {
        E target = null;
        try
        {
            target = (E) o;
        }
        catch(ClassCastException e)
        {
            System.out.println("removed element must be of proper type");
        }
        root = remove(target, root);
        if (found)
        {
            size--;
            root.updateHeight();
            root.updateLeftTreeSize();
        }
        return found;
    }

    /**
     * Recursive helper method for remove method.
     *
     * @param target the element to be removed
     * @param node the current node in the recursive iteration
     * @return a node with either the required node removed in its subtrees or unchanged
     */
    private Node remove(E target, Node node)
    {
        if (node == null)
        {
            found = false;
            return null;

        }
        if (target.compareTo(node.data) > 0)
        {
            node.right = remove(target, node.right);
            node.updateHeight();
            node.updateLeftTreeSize();
        }
        else if (target.compareTo(node.data) < 0)
        {
            node.left = remove(target, node.left);
            node.updateHeight();
            node.updateLeftTreeSize();
        }
        else
        {
            node = removeNode(node);
            found = true;
        }
        return node;
    }

    /**
     * Helper method for remove method that actually removes a node from the BST with 3 cases; a leaf,
     * a node with only one child, or a node with two children (in which case the predecessor must be found).
     *
     * @param node the node in the BST to be removed
     * @return a node with either the required node removed in its subtrees or unchanged
     */
    private Node removeNode(Node node) {
        E data;
        if (node.left == null)
        {
            return node.right;
        }
        else if (node.right == null)
        {
            return node.left;
        }
        else
        {
            data = getPredecessor(node.left);
            node.data = data;
            node.left = remove(data, node.left);
            return node;
        }
    }

    /**
     * Helper method for remove method that obtains the predecessor of the BST
     * in the case that the removed node has two children.
     *
     * @param left the node from which to start searching for the predecessor (the
     *             node being removed)
     * @return the predecessor element
     */
    private E getPredecessor(Node left)
    {
        if (left == null)
            throw new NullPointerException("getPredecessor called with an empty subtree");
        Node temp = left;
        while (temp.right != null)
            temp = temp.right;
        return temp.data;
    }

    /**
     * Returns the smallest element in this tree greater than or equal to the given element or null if not possible.
     *
     * @param e the "ceiling" element being compared to
     * @return the smallest element greater than or equal to the ceiling, or null
     */
    public E ceiling(E e)
    {
        if (e == null)
        {
            throw new NullPointerException("Ceiling cannot be called with an empty value");
        }
        if (root == null)
        {
            return null;
        }
        if (e.getClass() == this.getClass())
        {
            throw new ClassCastException();
        }
        if (e.compareTo(this.last()) > 0)
        {
            return null;
        }
        Node cur = root;
        Node ceiling = null;
        while (cur != null)
        {
            if (e.compareTo(cur.data) < 0)
            {
                ceiling = cur;
                cur = cur.left;
            }
            else if (e.compareTo(cur.data) > 0)
            {
                cur = cur.right;
            }
            else
            {
                ceiling = cur;
                break;
            }
        }
        return ceiling.data;
    }

    /**
     * Returns the smallest element in this tree greater than the given element or null if not possible.
     *
     * @param e the "higher" element being compared to
     * @return the smallest element greater than the ceiling, or null
     */
    public E higher(E e)
    {
        if (e == null)
        {
            throw new NullPointerException("Higher cannot be called with an empty value");
        }
        if (root == null)
        {
            return null;
        }
        if (e.getClass() == this.getClass())
        {
            throw new ClassCastException();
        }
        if (e.compareTo(this.last()) >= 0)
        {
            return null;
        }
        Node cur = root;
        Node higher = null;
        while (cur != null)
        {
            if (e.compareTo(cur.data) < 0)
            {
                higher = cur;
                cur = cur.left;
            }
            else if (e.compareTo(cur.data) >= 0)
            {
                cur = cur.right;
            }
            else
            {
                break;
            }
        }
        return higher.data;
    }

    /**
     * Returns the biggest element in this tree less than or equal to than the given element or null if not possible.
     *
     * @param e the "floor" element being compared to
     * @return the biggest element less than or equal to the floor, or null
     */
    public E floor(E e)
    {
        if (e == null)
        {
            throw new NullPointerException("Higher cannot be called with an empty value");
        }
        if (root == null)
        {
            return null;
        }
        if (e.getClass() == this.getClass())
        {
            throw new ClassCastException();
        }
        if (e.compareTo(this.first()) < 0)
        {
            return null;
        }
        Node cur = root;
        Node floor = null;
        while (cur != null)
        {
            if (e.compareTo(cur.data) > 0)
            {
                floor = cur;
                cur = cur.right;
            }
            else if (e.compareTo(cur.data) < 0)
            {
                cur = cur.left;
            }
            else
            {
                floor = cur;
                break;
            }
        }
        return floor.data;
    }

    /**
     * Returns the biggest element in this tree less than the given element or null if not possible.
     *
     * @param e the "lower" element being compared to
     * @return the biggest element less than the lower, or null
     */
    public E lower(E e)
    {
        if (e == null)
        {
            throw new NullPointerException("Higher cannot be called with an empty value");
        }
        if (root == null)
        {
            return null;
        }
        if (e.getClass() == this.getClass())
        {
            throw new ClassCastException();
        }
        if (e.compareTo(this.first()) <= 0)
        {
            return null;
        }
        Node cur = root;
        Node lower = null;
        while (cur != null)
        {
            if (e.compareTo(cur.data) > 0)
            {
                lower = cur;
                cur = cur.right;
            }
            else if (e.compareTo(cur.data) <= 0)
            {
                cur = cur.left;
            }
            else
            {
                break;
            }
        }
        return lower.data;
    }

    /**
     * Makes this BST empty, also resetting the size.
     */
    public void clear()
    {
        root = null;
        size = 0;
    }

    /**
     * Checks if the BST contains a certain element.
     *
     * @param o the element being searched for
     * @return true if the BST contains 0; otherwise,
     *         false
     */
    public boolean contains(Object o)
    {
        if (o == null)
        {
            throw new NullPointerException("object cannot be null");
        }
        try
        {
            E other = (E) o;
            return containsRec(other, root);
        }
        catch(ClassCastException e)
        {
            System.out.println("object in contains method is not of proper type");
        }
        return false;
    }

    /**
     * Recursive helper method for contains.
     *
     * @param e the element being searched for
     * @param node the current node in the recursive iteration
     * @return true if the BST contains 0; otherwise,
     *         false
     */
    private boolean containsRec(E e, Node node)
    {
        if (node == null)
        {
            return false;
        }
        else if (node.data.compareTo(e) == 0)
        {
            return true;
        }
        if (node.data.compareTo(e) > 0)
        {
            return containsRec(e, node.left);
        }
        return containsRec(e, node.right);
    }

    /**
     * Checks if this BST's elements are the same as another BST's elements.
     *
     * @param obj the other BST being compared to
     * @return true if the elements are the same; otherwise,
     *         false
     */
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || !(obj instanceof BST))
        {
            return false;
        }
        BST<E> compare = (BST<E>) obj;
        InOrderIterator<E> current = new InOrderIterator<E>(root);
        InOrderIterator<E> other = new InOrderIterator<E>(compare.root);
        while (current.hasNext())
        {
            if (!other.hasNext() || current.next().equals(other.next()) == false)
            {
                return false;
            }
        }
        if (other.hasNext())
        {
            return false;
        }
        return true;
    }

    /**
     * Obtains the size of this BST.
     * @return the size
     */
    public int size()
    {
        return size;
    }

    /**
     * Checks if this BST is empty (if the root is null).
     *
     * @return true if empty; otherwise,
     *         false
     */
    public boolean isEmpty()
    {
        return root == null;
    }

    /**
     * Obtains the height of this BST (also the height of the node).
     *
     * @return the height
     */
    public int height()
    {
        return root.height;
    }

    /**
     * Obtain the first, or lowest, element in this BST.
     *
     * @return the least element
     */
    public E first()
    {
        if (root == null)
        {
            throw new NoSuchElementException("first method cannot be called on an empty set");
        }
        Node curr = root;
        while(curr.left != null)
        {
            curr = curr.left;
        }
        return curr.data;
    }

    /**
     * Obtain the last, or highest, element in this BST.
     *
     * @return the greatest element
     */
    public E last()
    {
        if (root == null)
        {
            throw new NoSuchElementException("last method cannot be called on an empty set");
        }
        Node curr = root;
        while(curr.right != null)
        {
            curr = curr.right;
        }
        return curr.data;
    }

    /**
     * Obtains the element in this BST at a certain index in the inorder traversal of the tree.
     *
     * @param index the index in the inorder traversal of the tree being obtained from
     * @return the element at index
     */
    public E get(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new IndexOutOfBoundsException("index in get method must be between 0 and size");
        }
        return getRec(root, index);
    }

    /**
     * Recursive helper method for get method that utilizes the left subtree size data field of the BST nodes
     *
     * @param node the current node in the recursive iteration
     * @param index the index in the inorder traversal of the tree being obtained from
     * @return the element at index
     */
    public E getRec(Node node, int index)
    {
        if (index == node.leftTreeSize)
        {
            return node.data;
        }
        if (index < node.leftTreeSize)
        {
            return getRec(node.left, index);
        }
        return getRec(node.right, index - node.leftTreeSize - 1);
    }

    /**
     * Creates an iterator object for this BST which accesses the trees elements in an inorder traversal.
     *
     * @return the inorder iterator
     */
    public Iterator<E> iterator()
    {
        return new InOrderIterator<E>(root);
    }

    /**
     * Creates an iterator object for this BST which accesses the trees elements in a preorder traversal.
     *
     * @return the preorder iterator
     */
    public Iterator<E> preorderIterator()
    {
        return new PreOrderIterator<E>(root);
    }

    /**
     * Creates an iterator object for this BST which accesses the trees elements in a postorder traversal.
     *
     * @return the postorder iterator
     */
    public Iterator<E> postorderIterator()
    {
        return new PostOrderIterator<E>(root);
    }

    /**
     * Creates a string representation of this BST on a single line.
     *
     * @return the toString
     */
    @Override
    public String toString()
    {
        String s = "";
        InOrderIterator<E> iterator = new InOrderIterator<E>(root);
        for (int i = 0; i < size; i++)
        {
            s += String.valueOf(iterator.next()) + ", ";
        }
        return "[" + s + "]";
    }

    /**
     * Creates a string representation of this BST on more than one line in a shape that
     * more accurately models the BST.
     *
     * @return the advanced toString
     */
    public String toStringTreeFormat()
    {
        StringBuffer sb = new StringBuffer();
        toStringTreeFormatHelper(sb, root, 0);
        return sb.toString();
    }

    /**
     * Recursive helper method for toStringTreeFormat.
     *
     * @param sb a StringBuffer object used to create the toString
     * @param node the node in the current recursive iteration
     * @param level the level of the tree
     */
    private void toStringTreeFormatHelper(StringBuffer sb, Node node, int level) {
        //display the node
        if (level > 0 ) {
            for (int i = 0; i < level-1; i++) {
                sb.append("   ");
            }
            sb.append("|--");
        }
        if (node == null) {
            sb.append( "->\n");
            return;
        }
        else {
            sb.append( node.data + "\n");
        }

        //display the left subtree
        toStringTreeFormatHelper(sb, node.left, level+1);
        //display the right subtree
        toStringTreeFormatHelper(sb, node.right, level+1);
    }

    /**
     * An inorder iterator for this BST class, represented by a list.
     *
     * @param <E> the type of the iterator (same as the BST)
     *
     * @author Shrey Khater
     * @version 12/5/22
     */
    public class InOrderIterator<E> implements Iterator<E> {
        ArrayList<E> list = new ArrayList<E>();
        int current = 0;

        /**
         * Constructor for the inorder iterator. Constructs the list from the node.
         *
         * @param root the root of this BST from which the iterator's list will be created
         */
        public InOrderIterator(Node root)
        {
            makeList(root);
        }

        /**
         * Creates the iterators inorder list.
         *
         * @param node the node from which the list will be created
         */
        public void makeList(Node node)
        {
            if (node == null)
            {
                return;
            }
            makeList(node.left);
            list.add((E) node.data);
            makeList(node.right);
        }

        /**
         * Checks if the iterator has a next element.
         *
         * @return true if a next element exists; otherwise,
         *         false
         */
        @Override
        public boolean hasNext()
        {
            return (current < list.size());
        }

        /**
         * Iterates to the next element in this iterator.
         *
         * @return the current element stored in the iterator's inorder traversal
         */
        @Override
        public E next()
        {
            if (current > list.size())
            {
                throw new IndexOutOfBoundsException("end of iterator reached");
            }
            E temp = list.get(current);
            current++;
            return temp;
        }
    }

    /**
     * A preorder iterator for this BST class, represented by a list.
     *
     * @param <E> the type of the iterator (same as the BST)
     *
     * @author Shrey Khater
     * @version 12/5/22
     */
    public class PreOrderIterator<E> implements Iterator<E>{
        ArrayList<E> list = new ArrayList<E>();
        int current = 0;

        /**
         * Constructor for the preorder iterator. Constructs the list from the node.
         *
         * @param root the root of this BST from which the iterator's list will be created
         */
        public PreOrderIterator(Node root)
        {
            makeList(root);
        }


        /**
         * Creates the iterators preorder list.
         *
         * @param node the node from which the list will be created
         */
        public void makeList(Node node)
        {
            if (node == null)
            {
                return;
            }
            list.add((E) node.data);
            makeList(node.left);
            makeList(node.right);
        }

        /**
         * Checks if the iterator has a next element.
         *
         * @return true if a next element exists; otherwise,
         *         false
         */
        @Override
        public boolean hasNext()
        {
            return (current < list.size());
        }

        /**
         * Iterates to the next element in this iterator.
         *
         * @return the current element stored in the iterator's preorder traversal
         */
        @Override
        public E next()
        {
            if (current > list.size())
            {
                throw new IndexOutOfBoundsException("end of iterator reached");
            }
            E temp = list.get(current);
            current++;
            return temp;
        }
    }

    /**
     * A postorder iterator for this BST class, represented by a list.
     *
     * @param <E> the type of the iterator (same as the BST)
     *
     * @author Shrey Khater
     * @version 12/5/22
     */
    public class PostOrderIterator<E> implements Iterator<E>{
        ArrayList<E> list = new ArrayList<E>();
        int current = 0;

        /**
         * Constructor for the postorder iterator. Constructs the list from the node.
         *
         * @param root the root of this BST from which the iterator's list will be created
         */
        public PostOrderIterator(Node root)
        {
            makeList(root);
        }

        /**
         * Creates the iterators postorder list.
         *
         * @param node the node from which the list will be created
         */
        public void makeList(Node node)
        {
            if (node == null)
            {
                return;
            }
            makeList(node.left);
            makeList(node.right);
            list.add((E) node.data);
        }

        /**
         * Checks if the iterator has a next element.
         *
         * @return true if a next element exists; otherwise,
         *         false
         */
        @Override
        public boolean hasNext()
        {
            return (current < list.size());
        }

        /**
         * Iterates to the next element in this iterator.
         *
         * @return the current element stored in the iterator's postorder traversal
         */
        @Override
        public E next()
        {
            if (current > list.size())
            {
                throw new IndexOutOfBoundsException("end of iterator reached");
            }
            E temp = list.get(current);
            current++;
            return temp;
        }
    }
}