package tree;

import java.util.ArrayList;
import java.util.List;

public class BST {
    Node root;

    public Node getRoot() {
        return root;
    }

    public void add(int data) {
        if(root == null){
            root = new Node(null, data, null, null);
            root.parent = root;
            return;
        }

        add(data, root);
    }

    private void add(int data, Node node){
        if(node.data > data){
            if(node.left == null){
                node.left = new Node(null, data, null, node);
                node.left.parent = node;
            }
            else add(data, node.left);
        }
        else {
            if(node.right == null){
                node.right = new Node(null, data, null, node);
                node.right.parent = node;
            }
            else add(data, node.right);
        }
    }

    public boolean contains(int data) {
        if(root == null) return false;

        return contains(data, root);
    }

    private boolean contains(int data, Node node){
        if(node.data == data)
            return true;

        if(node.data > data){
            if(node.left == null)
                return false;
            else
                return contains(data, node.left);
        }
        else {
            if(node.right == null)
                return false;
            else
                return contains(data, node.right);
        }
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

        // next
        if(node.data > data){
            if(node.left == null) return false;
            else return findRemoveTargetNode(data, node.left);
        }
        else{
            if(node.right == null) return false;
            else return findRemoveTargetNode(data, node.right);
        }
    }

    private void deleteNode(Node target) {
        if(target.left == null & target.right == null){
            if(target == root) {
                root = null;
                return;
            }

            Node parent = target.parent;
            if(parent.data > target.data)
                parent.left = null;
            else parent.right = null;
        }
        else if(target.left != null & target.right == null){
            Node parent = target.parent;
            if(parent.data > target.data){
                target.left.parent = parent;
                parent.left = target.left;
            }
            else{
                target.left.parent = parent;
                parent.right = target.left;
            }
        }
        else if(target.left == null & target.right != null){
            Node parent = target.parent;
            if(parent.data > target.data){
                target.right.parent = parent;
                parent.left = target.right;
            }
            else{
                target.right.parent = parent;
                parent.right = target.right;
            }
        }
        else {
            Node replacement = getSuccessorAndUnlink(target);
            target.data = replacement.data;
        }
    }

    private Node getSuccessorAndUnlink(Node target) {
        Node curParent = target;
        Node current = target.right;

        if(current.left == null){
            curParent.right = current.right;
            if(curParent.right != null)
                current.right.parent = curParent;
            current.right = null;
            return current;
        }

        while(current.left != null){
            curParent = current;
            current = current.left;
        }

        curParent.left = current.right;
        if(current.right != null)
            curParent.left.parent = curParent;

        current.right = null;
        return current;
    }

    public void printPreorder(){
        if(root != null) preorderTraversal(root);
        System.out.println();
    }

    public void printInorder(){
        if(root != null) inorderTraversal(root);
        System.out.println();
    }

    public void printPostorder(){
        if(root != null) postorderTraversal(root);
        System.out.println();
    }

    private void preorderTraversal(Node node) {
        System.out.print(node.data + " ");
        if(node.left != null) preorderTraversal(node.left);
        if(node.right != null) preorderTraversal(node.right);
    }

    private void inorderTraversal(Node node) {
        if(node.left != null) inorderTraversal(node.left);
        System.out.print(node.data + " ");
        if(node.right != null) inorderTraversal(node.right);
    }

    private void postorderTraversal(Node node) {
        if(node.left != null) postorderTraversal(node.left);
        if(node.right != null) postorderTraversal(node.right);
        System.out.print(node.data + " ");
    }

    public List<Integer> getAllNodeData() {
        List<Integer> list = new ArrayList<>();
        if(root != null) getNodeDataByInorder(root, list);
        return list;
    }

    private void getNodeDataByInorder(Node node, List<Integer> list) {
        if(node.left != null) getNodeDataByInorder(node.left, list);
        list.add(node.data);
        if(node.right != null) getNodeDataByInorder(node.right, list);
    }

    public class Node {
        public int data;

        public Node left;
        public Node right;

        Node parent;

        public Node(Node left, int data, Node right, Node parent) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public int countChild() {
            int cnt = 0;
            if(left != null) cnt++;
            if(right != null) cnt++;
            return cnt;
        }
    }
}
