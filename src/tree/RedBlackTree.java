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

    public boolean remove(int data){
        initViolation();
        if(root == null) return false;
        return findAndRemove(data, root);
    }

    private boolean findAndRemove(int data, Node node) {
        if(data == node.data) {
            deleteNode(node);
            return true;
        } else if(data < node.data) {
            if(node.left == null) return false;
            return findAndRemove(data, node.left);
        } else {
            if(node.right == null) return false;
            return findAndRemove(data, node.right);
        }
    }

    private void deleteNode(Node node) {
        size--;
        if(node.left == null && node.right == null) {
            if(root == node) {
                root = null;
                return;
            }

            Node parent = node.parent;
            boolean isLeft = parent.data > node.data;
            if(isLeft)
                parent.left = null;
            else parent.right = null;

            checkRemoveViolation(node.color, null, parent, isLeft); // nil node
        } else if(node.left != null && node.right == null) {
            if(root == node) {
                root = node.left;
                root.parent = null;
                checkRemoveViolation(node.color, root, null, true); // 무조건 red and black
                return;
            }

            Node parent = node.parent;
            boolean isLeft = parent.data > node.data;
            if(isLeft){
                parent.left = node.left;
                node.left.parent = parent;
            }
            else{
                parent.right = node.left;
                node.left.parent = parent;
            }

            checkRemoveViolation(node.color, node.left, parent, isLeft);
        } else if(node.left == null && node.right != null) {
            if(root == node) {
                root = node.right;
                root.parent = null;
                checkRemoveViolation(node.color, root, null, true); // 무조건 red and black
                return;
            }

            Node parent = node.parent;
            boolean isLeft = parent.data > node.data;
            if(isLeft){
                parent.left = node.right;
                node.right.parent = parent;
            }
            else{
                parent.right = node.right;
                node.right.parent = parent;
            }

            checkRemoveViolation(node.color, node.right, parent, isLeft);
        } else
            replaceSuccessorAndUnlink(node);
    }

    private void replaceSuccessorAndUnlink(Node node) {
        Node successor = node.right;

        if(successor.left == null) {
            node.right = successor.right;
            successor.right.parent = node;
            checkRemoveViolation(node.color, successor.right, node, false);
            return;
        }

        while(successor.left != null) successor = successor.left;

        Node parent = successor.parent;
        if(successor.right != null) {
            parent.left = successor.right;
            successor.right.parent = parent;
        }
        node.data = successor.data;

        checkRemoveViolation(node.color, successor.right, parent, true);
    }

    private void checkRemoveViolation(Color removedColor, Node replacedNode, Node parent, boolean isLeft) {
        if(removedColor == Color.Red) return; // 아무 문제 없음

        if(replacedNode != null && replacedNode.color == Color.Red){ // Red and Black
            violations.add(Violation.RED_AND_BLACK);
            replacedNode.color = Color.Black;
            return;
        }

        Node silblingNode = isLeft? parent.right: parent.left;

        // TODO Refactoring ctrl+alt+m, silblingNode change
        if(isLeft && parent.right.color == Color.Black &&
                parent.right.right != null &&
                parent.right.right.color == Color.Red){ // doubly black 1
            violations.add(Violation.DOUBLY_BLACK1);
            swapColor(parent, silblingNode);
            silblingNode.right.color = Color.Black;
            rotateLeft(parent);
        }
        else if(isLeft && parent.right.color == Color.Black &&
                parent.right.left != null &&
                parent.right.left.color == Color.Red){ // doubly black 2
            violations.add(Violation.DOUBLY_BLACK2);
            swapColor(silblingNode, silblingNode.left);
            silblingNode = rotateRight(silblingNode);

            // doubly black 1
            swapColor(parent, silblingNode);
            silblingNode.right.color = Color.Black;
            rotateLeft(parent);
        }
        else if(isLeft && parent.right.color == Color.Black &&
                (parent.right.left == null ||
                        parent.right.left.color == Color.Black)){ // doubly black 3
            violations.add(Violation.DOUBLY_BLACK3);
            silblingNode.color = Color.Red;

            // parent -> rb or db
            if(parent == root) // doubly black but can eliminate
                return;
            else if(parent.color == Color.Red) parent.color = Color.Black;
            else
                checkRemoveViolation(Color.Black, parent, parent.parent,
                        parent.parent.data > parent.data);
        }
        else if(isLeft && parent.right.color == Color.Red){ // doubly black 4
            violations.add(Violation.DOUBLY_BLACK4);
            swapColor(parent, silblingNode);
            rotateLeft(parent);
            checkRemoveViolation(Color.Black, replacedNode, parent, isLeft);
        }
        else if(!isLeft && parent.left.color == Color.Black &&
                parent.left.left != null &&
                parent.left.left.color == Color.Red){ // doubly black 1 (right)
            violations.add(Violation.DOUBLY_BLACK1);
            swapColor(parent, silblingNode);
            silblingNode.left.color = Color.Black;
            rotateRight(parent);
        }
        else if(!isLeft && parent.left.color == Color.Black &&
                parent.left.right != null &&
                parent.left.right.color == Color.Red){ // doubly black 2 (right)
            violations.add(Violation.DOUBLY_BLACK2);
            swapColor(silblingNode, silblingNode.right);
            silblingNode = rotateLeft(silblingNode);

            // doubly black 1
            swapColor(parent, silblingNode);
            silblingNode.left.color = Color.Black;
            rotateRight(parent);
        }
        else if(!isLeft && parent.left.color == Color.Black &&
                (parent.left.right == null ||
                        parent.left.right.color == Color.Black)){ // doubly black 3 (right)
            violations.add(Violation.DOUBLY_BLACK3);
            silblingNode.color = Color.Red;

            // parent -> rb or db
            if(parent == root) // doubly black but can eliminate
                return;
            else if(parent.color == Color.Red) parent.color = Color.Black;
            else
                checkRemoveViolation(Color.Black, parent, parent.parent,
                        parent.parent.data > parent.data);
        }
        else if(!isLeft && parent.left.color == Color.Red){ // doubly black 4 (right)
            violations.add(Violation.DOUBLY_BLACK4);
            swapColor(parent, silblingNode);
            rotateRight(parent);
            checkRemoveViolation(Color.Black, replacedNode, parent, isLeft);
        }
        else throw new IllegalStateException("Invalid remove case");
    }

    private Node rotateRight(Node node) {
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

        return left;
    }

    private Node rotateLeft(Node node) {
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

        return right;
    }

    private static void swapColor(Node node1, Node node2) {
        Color color = node1.color;
        node1.color = node2.color;
        node2.color = color;
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
