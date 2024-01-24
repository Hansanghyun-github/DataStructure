package tree;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {
    public int size;
    public Node root;
    public List<Violation> violations; // for debug

    public RedBlackTree() {
        size = 0;
        root = null;
        violations = new ArrayList<>();
    }

    public void add(int data){
        initViolation();
        if(root == null){
            root = new Node(null, null, data, null);
            violations.add(Violation.ADD_VIOLATION2); // #2
            root.color = Color.Black;
            size++;
            return;
        }
        add(data, root);

        size++;
    }

    private void add(int data, Node node) {
        if(data < node.data){
            if(node.left == null) {
                Node newNode = new Node(node, null, data, null);
                node.left = newNode;
                checkAddViolation(newNode);
            }
            else add(data, node.left);
        } else {
            if(node.right == null) {
                Node newNode = new Node(node, null, data, null);
                node.right = newNode;
                checkAddViolation(newNode);
            }
            else add(data, node.right);
        }
    }

    private void checkAddViolation(Node node) {
        Node parent = node.parent;
        if(parent == null) { // node is root and red
            violations.add(Violation.ADD_VIOLATION2);
            node.color = Color.Black;
            return;
        }
        if(parent.color == Color.Black) return;

        // not null(parent is not root because of color)
        Node grandParent = parent.parent;
        Node aunt;
        if(grandParent.data > parent.data)
            aunt = grandParent.right;
        else aunt = grandParent.left;

        if(aunt != null && aunt.color == Color.Red){ // #4_3
            violations.add(Violation.ADD_VIOLATION4_3);
            parent.color = Color.Black;
            aunt.color = Color.Black;
            grandParent.color = Color.Red;
            checkAddViolation(grandParent);
            return;
        }

        // grandparent is Black, parent is Red, aunt is Black
        // #4_1 or #4_2
        if(grandParent.left == parent && parent.left == node){ // #4_1 LL
            violations.add(Violation.ADD_VIOLATION4_1);
            grandParent.color = Color.Red;
            parent.color = Color.Black;
            rotateRight(grandParent);
        } else if(grandParent.right == parent && parent.right == node){ // #4_1 RR
            violations.add(Violation.ADD_VIOLATION4_1);
            grandParent.color = Color.Red;
            parent.color = Color.Black;
            rotateLeft(grandParent);
        } else if(grandParent.left == parent && parent.right == node){ // #4_2 LR
            violations.add(Violation.ADD_VIOLATION4_2);
            grandParent.color = Color.Red;
            node.color = Color.Black;
            rotateLeft(parent);
            rotateRight(grandParent);
        } else { // #4_2 RL
            violations.add(Violation.ADD_VIOLATION4_2);
            grandParent.color = Color.Red;
            node.color = Color.Black;
            rotateRight(parent);
            rotateLeft(grandParent);
        }
    }

    private void rotateRight(Node node) {
        Node parent = node.parent;
        Node left = node.left;

        node.left = left.right;
        if(node.left != null)
            node.left.parent = node;
        left.right = node;
        node.parent = left;
        left.parent = parent;
        if(parent != null){
            if(left.data < parent.data)
                parent.left = left;
            else parent.right = left;
        }

        if(root == node) root = left;
    }

    private void rotateLeft(Node node) {
        Node right = node.right;
        Node parent = node.parent;

        node.right = right.left;
        if(node.right != null)
            node.right.parent = node;
        right.left = node;
        node.parent = right;
        right.parent = parent;
        if(parent != null) {
            if(right.data < parent.data)
                parent.left = right;
            else parent.right = right;
        }

        if(root == node) root = right;
    }

    public boolean remove(int data){
        initViolation();
        return false;
    }

    private void initViolation() {
        violations.clear();
    }

    public class Node {
        public int data;
        public Node parent;
        public Node left;
        public Node right;
        public Color color;
        public Node(Node parent, Node left, int data, Node right) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = Color.Red;
        }

        public Node(Node left, int data, Node right) {
            this(null, left, data, right);
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
