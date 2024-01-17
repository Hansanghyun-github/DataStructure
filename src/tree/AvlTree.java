package tree;

import java.util.TreeMap;

import static java.lang.Math.exp;
import static java.lang.Math.max;

public class AvlTree {
    public Node root;

    public void add(int d) {
        Node newNode;
        if(root == null){
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

        fixAfterChangingTree(newNode.parent);
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
                target.right.parent = root;
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
        }
        else if(target.left != null && target.right == null) {
            if(target == root) {
                root = target.left;
                target.left.parent = root;
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
        }
        else
            replaceSuccessorAndUnlink(target);
    }

    private void replaceSuccessorAndUnlink(Node target) {
        Node successor = target.right;
        if(successor.left == null) {
            target.right = successor.right;
            successor.right.parent = target;
            target.data = successor.data;

            fixAfterChangingTree(target);
            return;
        }

        while(successor.left != null)
            successor = successor.left;

        Node parent = successor.parent;
        if(successor.right != null) {
            parent.left = successor.right;
            successor.right.parent = parent;
        }
        target.data = successor.data;

        fixAfterChangingTree(parent);
    }

    private void fixAfterChangingTree(Node parent) {
        // check all parent nodes maintain balance factor
        Node temp = parent;
        while (temp != null) {
            temp.updateHeight();
            int tempBf = temp.getBf();
            // guarantee sub node's bf is in {-1,0,1}
            if(tempBf == 2) {
                // check LL or LR
                int state = checkLLorLR(temp);

                if(state == LR)
                    rotateLeft(temp.left);
                temp = rotateRight(temp);
            }
            else if(tempBf == -2) {
                // check RR or RL
                int state = checkRRorRL(temp);

                if(state == RL)
                    rotateRight(temp.right);
                temp = rotateLeft(temp);
            }
            temp = temp.parent;
        }
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
            if(parent.data > right.data)
                parent.left = right;
            else parent.right = right;
        }

        // update BF
        node.updateHeight();
        right.updateHeight();


        if(root == node) root = right;

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
            if(parent.data > left.data)
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

        // guarantee left node's bf is -1 or 1
        if(leftBf == 1)
            return LL;
        else
            return LR;
    }

    // parameter node's bf is -2
    private int checkRRorRL(Node node) {
        Node right = node.right;
        int rightBf = right.getBf();

        // guarantee right node's bf is -1 or 1
        if(rightBf == -1)
            return RR;
        else
            return RL;
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
