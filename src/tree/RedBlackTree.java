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
            if(leftOf(node) == null) {
                Node newNode = new Node(node, null, data, null);
                node.left = newNode;
                checkAddViolation(newNode);
            }
            else add(data, leftOf(node));
        } else {
            if(rightOf(node) == null) {
                Node newNode = new Node(node, null, data, null);
                node.right = newNode;
                checkAddViolation(newNode);
            }
            else add(data, rightOf(node));
        }
    }

    private void checkAddViolation(Node node) {
        Node parent = parentOf(node);
        if(parent == null) { // node is root and red
            violations.add(Violation.ADD_VIOLATION2);
            node.color = Color.Black;
            return;
        }
        if(parent.color == Color.Black) return;

        // not null(parent is not root because of color)
        Node grandParent = parentOf(parent);
        Node aunt;
        if(grandParent.data > parent.data)
            aunt = rightOf(grandParent);
        else aunt = leftOf(grandParent);

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
        if(leftOf(grandParent) == parent && leftOf(parent) == node){ // #4_1 LL
            violations.add(Violation.ADD_VIOLATION4_1);
            grandParent.color = Color.Red;
            parent.color = Color.Black;
            rotateRight(grandParent);
        } else if(rightOf(grandParent) == parent && rightOf(parent) == node){ // #4_1 RR
            violations.add(Violation.ADD_VIOLATION4_1);
            grandParent.color = Color.Red;
            parent.color = Color.Black;
            rotateLeft(grandParent);
        } else if(leftOf(grandParent) == parent && rightOf(parent) == node){ // #4_2 LR
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
            if(leftOf(node) == null) return false;
            return findAndRemove(data, leftOf(node));
        } else {
            if(rightOf(node) == null) return false;
            return findAndRemove(data, rightOf(node));
        }
    }

    private void deleteNode(Node node) {
        size--;
        if(leftOf(node) == null && rightOf(node) == null) {
            if(root == node) {
                root = null;
                return;
            }

            Node parent = parentOf(node);
            boolean isLeft = parent.data > node.data;
            if(isLeft)
                parent.left = null;
            else parent.right = null;

            checkRemoveViolation(colorOf(node), null, parent, isLeft); // nil node
        } else if(leftOf(node) != null && rightOf(node) == null) {
            if(root == node) {
                root = leftOf(node);
                root.parent = null;
                checkRemoveViolation(colorOf(node), root, null, true); // 무조건 red and black
                return;
            }

            Node parent = parentOf(node);
            boolean isLeft = parent.data > node.data;
            if(isLeft){
                parent.left = leftOf(node);
                node.left.parent = parent;
            }
            else{
                parent.right = leftOf(node);
                node.left.parent = parent;
            }

            checkRemoveViolation(colorOf(node), leftOf(node), parent, isLeft);
        } else if(leftOf(node) == null && rightOf(node) != null) {
            if(root == node) {
                root = rightOf(node);
                root.parent = null;
                checkRemoveViolation(colorOf(node), root, null, true); // 무조건 red and black
                return;
            }

            Node parent = parentOf(node);
            boolean isLeft = parent.data > node.data;
            if(isLeft){
                parent.left = rightOf(node);
                node.right.parent = parent;
            }
            else{
                parent.right = rightOf(node);
                node.right.parent = parent;
            }

            checkRemoveViolation(colorOf(node), rightOf(node), parent, isLeft);
        } else
            replaceSuccessorAndUnlink(node);
    }

    private void replaceSuccessorAndUnlink(Node node) {
        Node successor = rightOf(node);

        if(leftOf(successor) == null) {
            node.right = rightOf(successor);

            if(rightOf(successor) != null)
                successor.right.parent = node;
            checkRemoveViolation(colorOf(node), rightOf(successor), node, false);
            return;
        }

        while(leftOf(successor) != null) successor = leftOf(successor);

        Node parent = parentOf(successor);
        parent.left = rightOf(successor);
        if(rightOf(successor) != null)
            successor.right.parent = parent;
        node.data = successor.data;

        checkRemoveViolation(colorOf(rightOf(successor)), rightOf(successor), parent, true);
    }

    private void checkRemoveViolation(Color removedColor, Node replacedNode, Node parent, boolean isLeft) {
        if(removedColor == Color.Red) return; // 아무 문제 없음

        if(replacedNode != null && colorOf(replacedNode) == Color.Red){ // Red and Black
            violations.add(Violation.RED_AND_BLACK);
            replacedNode.color = Color.Black;
            return;
        }

        Node silblingNode = isLeft? rightOf(parent) : leftOf(parent);
        if(isDoublyBlackLeftCase1(isLeft, silblingNode)){ // doubly black 1
            violations.add(Violation.DOUBLY_BLACK1);
            swapColor(parent, silblingNode);
            rightOf(silblingNode).color = Color.Black;
            rotateLeft(parent);
        }
        else if(isDoublyBlackLeftCase2(isLeft, silblingNode)){ // doubly black 2
            violations.add(Violation.DOUBLY_BLACK2);
            swapColor(silblingNode, leftOf(silblingNode));
            silblingNode = rotateRight(silblingNode);

            // doubly black 1
            swapColor(parent, silblingNode);
            rightOf(silblingNode).color = Color.Black;
            rotateLeft(parent);
        }
        else if(isDoublyBlackLeftCase3(isLeft, silblingNode)){ // doubly black 3
            violations.add(Violation.DOUBLY_BLACK3);
            silblingNode.color = Color.Red;

            // parent -> rb or db
            if(parent == root) // doubly black but can eliminate
                return;
            else if(parent.color == Color.Red) parent.color = Color.Black;
            else
                checkRemoveViolation(Color.Black, parent, parentOf(parent),
                        parentOf(parent).data > parent.data);
        }
        else if(isDoublyBlackLeftCase4(isLeft, silblingNode)){ // doubly black 4
            violations.add(Violation.DOUBLY_BLACK4);
            swapColor(parent, silblingNode);
            rotateLeft(parent);
            checkRemoveViolation(Color.Black, replacedNode, parent, isLeft);
        }
        else if(isDoublyBlackRightCase1(isLeft, silblingNode)){ // doubly black 1 (right)
            violations.add(Violation.DOUBLY_BLACK1);
            swapColor(parent, silblingNode);
            leftOf(silblingNode).color = Color.Black;
            rotateRight(parent);
        }
        else if(isDoublyBlackRightCase2(isLeft, silblingNode)){ // doubly black 2 (right)
            violations.add(Violation.DOUBLY_BLACK2);
            swapColor(silblingNode, rightOf(silblingNode));
            silblingNode = rotateLeft(silblingNode);

            // doubly black 1
            swapColor(parent, silblingNode);
            leftOf(silblingNode).color = Color.Black;
            rotateRight(parent);
        }
        else if(isDoublyBlackRightCase3(isLeft, silblingNode)){ // doubly black 3 (right)
            violations.add(Violation.DOUBLY_BLACK3);
            silblingNode.color = Color.Red;

            // parent -> rb or db
            if(parent == root) // doubly black but can eliminate
                return;
            else if(colorOf(parent) == Color.Red) parent.color = Color.Black;
            else
                checkRemoveViolation(Color.Black, parent, parentOf(parent),
                        parentOf(parent).data > parent.data);
        }
        else if(isDoublyBlackRightCase4(isLeft, silblingNode)){ // doubly black 4 (right)
            violations.add(Violation.DOUBLY_BLACK4);
            swapColor(parent, silblingNode);
            rotateRight(parent);
            checkRemoveViolation(Color.Black, replacedNode, parent, isLeft);
        }
        else throw new IllegalStateException("Invalid remove case");
    }

    private static boolean isDoublyBlackLeftCase1(boolean isLeft, Node silblingNode) {
        return isLeft && colorOf(silblingNode) == Color.Black &&
                rightOf(silblingNode) != null &&
                colorOf(rightOf(silblingNode)) == Color.Red;
    }

    private static boolean isDoublyBlackLeftCase2(boolean isLeft, Node silblingNode) {
        return isLeft && colorOf(silblingNode) == Color.Black &&
                leftOf(silblingNode) != null &&
                colorOf(leftOf(silblingNode)) == Color.Red;
    }

    private static boolean isDoublyBlackLeftCase3(boolean isLeft, Node silblingNode) {
        return isLeft && colorOf(silblingNode) == Color.Black &&
                (leftOf(silblingNode) == null ||
                        colorOf(leftOf(silblingNode)) == Color.Black);
    }

    private static boolean isDoublyBlackLeftCase4(boolean isLeft, Node silblingNode) {
        return isLeft && colorOf(silblingNode) == Color.Red;
    }

    private static boolean isDoublyBlackRightCase1(boolean isLeft, Node silblingNode) {
        return !isLeft && colorOf(silblingNode) == Color.Black &&
                leftOf(silblingNode) != null &&
                colorOf(leftOf(silblingNode)) == Color.Red;
    }

    private static boolean isDoublyBlackRightCase2(boolean isLeft, Node silblingNode) {
        return !isLeft && colorOf(silblingNode) == Color.Black &&
                rightOf(silblingNode) != null &&
                colorOf(rightOf(silblingNode)) == Color.Red;
    }

    private static boolean isDoublyBlackRightCase3(boolean isLeft, Node silblingNode) {
        return !isLeft && colorOf(silblingNode) == Color.Black &&
                (rightOf(silblingNode) == null ||
                        colorOf(rightOf(silblingNode)) == Color.Black);
    }

    private static boolean isDoublyBlackRightCase4(boolean isLeft, Node silblingNode) {
        return !isLeft && colorOf(silblingNode) == Color.Red;
    }

    private Node rotateRight(Node node) {
        Node parent = parentOf(node);
        Node left = leftOf(node);

        node.left = rightOf(left);
        if(leftOf(node) != null)
            node.left.parent = node;
        left.right = node;
        node.parent = left;
        left.parent = parent;
        if(parent != null){
            if(node.data < parent.data)
                parent.left = left;
            else parent.right = left;
        }

        if(root == node) root = left;

        return left;
    }

    private Node rotateLeft(Node node) {
        Node right = rightOf(node);
        Node parent = parentOf(node);

        node.right = leftOf(right);
        if(rightOf(node) != null)
            node.right.parent = node;
        right.left = node;
        node.parent = right;
        right.parent = parent;
        if(parent != null) {
            if(node.data < parent.data)
                parent.left = right;
            else parent.right = right;
        }

        if(root == node) root = right;

        return right;
    }

    private static Node parentOf(Node node) {
        return (node == null)? null: node.parent;
    }

    private static Node leftOf(Node node) {
        return (node == null)? null: node.left;
    }

    private static Node rightOf(Node node) {
        return (node == null)? null: node.right;
    }

    private static Color colorOf(Node node) {
        return (node == null)? null: node.color;
    }

    private static void swapColor(Node node1, Node node2) {
        if(node1 == null) {
            node2.color = Color.Black;
            return;
        }
        if(node2 == null) {
            node1.color = Color.Black;
            return;
        }

        Color color = node1.color;
        node1.color = node2.color;
        node2.color = color;
    }

    private void initViolation() {
        violations.clear();
    }

    public int checkBlackHeight(Node node) {
        if(node == null) return 1;

        // color check
        if(node == root && node.color == Color.Red) return -1;

        if(node.color == Color.Red &&
                (colorOf(node.left) == Color.Red || colorOf(node.right) == Color.Red))
            return -1;

        int leftBH = checkBlackHeight(node.left);
        int rightBH = checkBlackHeight(node.right);

        if(leftBH == -1) return -1; // left subTree has violation
        if(rightBH == -1) return -1; // right subTree has violation
        if(leftBH != rightBH) return -1;

        if(node.color == Color.Black)
            return 1 + leftBH;
        else
            return leftBH;
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
