package tree;

import java.util.TreeMap;

import static java.lang.Math.exp;
import static java.lang.Math.max;

public class AvlTree {
    public int size;
    public Node root;

    public void add(int d) {
        Node newNode;
        if(root == null){
            size++;
            root = new Node(null, d, null);
            return;
        }
        else add(d, root);
    }

    private void add(int d, Node node) {
        if(node.data > d) {
            if(node.left == null)
                addNode(d, node, true);
            else add(d, node.left);
        }
        else {
            if(node.right == null)
                addNode(d, node, false);
            else add(d, node.right);
        }
    }

    private void addNode(int d, Node parent, boolean addToLeft) {
        Node newNode = new Node(parent, null, d, null);
        if(addToLeft)
            parent.left = newNode;
        else
            parent.right = newNode;

        fixAfterChangingTree(parent);

        size++;
    }

    public boolean remove(int data) {
        // root is null
        if(root == null) return false;

        return findRemoveTargetNode(data, root);
    }

    private boolean findRemoveTargetNode(int data, Node node) {
        if(node.data == data) {
            deleteNode(node);
            return true;
        }
        else if(node.data > data){
            if(node.left == null) return false;
            return findRemoveTargetNode(data, node.left);
        }
        else{
            if(node.right == null) return false;
            return findRemoveTargetNode(data, node.right);
        }
    }

    private void deleteNode(Node target) {
        if(target.left == null && target.right == null) {
            if(root == target) {
                root = null;
                return;
            }
            Node parent = target.parent;
            if(parent.data > target.data)
                parent.left = null;
            else parent.right = null;
            fixAfterChangingTree(parent);
        }
        else if(target.left == null && target.right != null) {
            if(target == root){
                root = target.right;
                target.right.parent = null;
                return;
            }

            Node parent = target.parent;
            if(parent.data > target.data){
                parent.left = target.right;
                target.right.parent = parent;
            }
            else{
                parent.right = target.right;
                target.right.parent = parent;
            }
            fixAfterChangingTree(parent);
        }
        else if(target.left != null && target.right == null) {
            if(target == root) {
                root = target.left;
                target.left.parent = null;
                return;
            }

            Node parent = target.parent;
            if(parent.data > target.data){
                parent.left = target.left;
                target.left.parent = parent;
            }
            else{
                parent.right = target.left;
                target.left.parent = parent;
            }
            fixAfterChangingTree(parent);
        }
        else
            replaceSuccessorAndUnlink(target);

        size--;
    }

    private void replaceSuccessorAndUnlink(Node target) {
        Node successor = target.right;
        if(successor.left == null) {
            target.right = successor.right;

            if(successor.right != null)
                successor.right.parent = target;
            target.data = successor.data;

            fixAfterChangingTree(target);
            return;
        }

        while(successor.left != null)
            successor = successor.left;

        Node parent = successor.parent;
        parent.left = successor.right;
        if(successor.right != null)
            successor.right.parent = parent;
        target.data = successor.data;

        fixAfterChangingTree(parent);
    }

    private void fixAfterChangingTree(Node node) {
        // check all parent nodes maintain balance factor
        node.updateHeight();
        int tempBf = node.getBf();

        //assert tempBf > 2 || tempBf < -2;

        // guarantee sub node's bf is in {-1,0,1}
        if(tempBf == 2) {
            // check LL or LR
            int state = checkLLorLR(node);

            if(state == LR)
                rotateLeft(node.left);
            node = rotateRight(node);
        }
        else if(tempBf == -2) {
            // check RR or RL
            int state = checkRRorRL(node);

            if(state == RL)
                rotateRight(node.right);
            node = rotateLeft(node);
        }

        if(node.parent != null) fixAfterChangingTree(node.parent);
    }

    private Node rotateLeft(Node node) {
        Node parent = node.parent;
        Node right = node.right;

        node.right = right.left;
        if(node.right != null)
            node.right.parent = node;

        right.left = node;
        node.parent = right;

        right.parent = parent;
        if(right.parent != null){
            if(parent.data > node.data)
                parent.left = right;
            else parent.right = right;
        }
        else root = right;

        // update BF
        node.updateHeight();
        right.updateHeight();

        return right;
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
        if(left.parent != null){
            if(parent.data > node.data)
                parent.left = left;
            else parent.right = left;
        }

        // update BF
        node.updateHeight();
        left.updateHeight();

        if(root == node) root = left;

        return left;
    }

    // parameter node's bf is 2
    private int checkLLorLR(Node node) {
        Node left = node.left;
        int leftBf = left.getBf();

        //assert leftBf == -1 || leftBf == 1;

        // guarantee left node's bf is -1 or 1
        if(leftBf == -1)
            return LR;
        else
            return LL;
    }

    // parameter node's bf is -2
    private int checkRRorRL(Node node) {
        Node right = node.right;
        int rightBf = right.getBf();

        //assert rightBf == -1 || rightBf == 1;

        // guarantee right node's bf is -1 or 1
        if(rightBf == 1)
            return RL;
        else
            return RR;
    }


    public class Node {
        public int data;
        public Node parent;
        public Node left;
        public Node right;

        public int height;

        public Node(Node parent, Node left, int data, Node right) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
            height = 0;
        }

        public Node(Node left, int data, Node right) {
            this(null, left, data, right);
        }

        public void updateHeight() {
            int leftHeight = (left == null) ? 0 : left.height + 1;
            int rightHeight = (right == null) ? 0 : right.height + 1;
            height = max(leftHeight, rightHeight);
        }

        public int getBf(){
            // 서브트리의 노드가 없다면 높이는 -1
            int leftH = (left == null) ? -1 : left.height;
            int rightH = (right == null) ? -1 : right.height;
            return leftH - rightH;
        }
    }

    private static final int LL = 0;
    private static final int RR = 1;
    private static final int LR = 2;
    private static final int RL = 3;
}
