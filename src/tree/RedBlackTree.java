package tree;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {
    public int size;
    public Node root;
    public List<Violation> violations;

    public RedBlackTree() {
        size = 0;
        root = null;
        violations = new ArrayList<>();
    }

    public void add(int data){
        initViolation();
    }

    public boolean remove(int data){
        initViolation();
        return false;
    }

    private void initViolation() {
        violations.clear();
        violations.clear();
    }

    public class Node {


        public int data;
        public Node parent;
        public Node left;
        public Node right;
        public Color color;
        public Node(Node parent, Node left, int data, Node right, Color color) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
        }

        public Node(Node left, int data, Node right, Color color) {
            this(null, left, data, right, color);
        }

    }


    public enum Color{
        Red, Black;
    }

    public enum Violation {
        ADD_VIOLATION2, ADD_VIOLATION4_1, ADD_VIOLATION4_2, ADD_VIOLATION4_3,
        RED_AND_BLACK, DOUBLY_BLACK1, DOUBLY_BLACK2, DOUBLY_BLACK3, DOUBLY_BLACK4
    }
}
