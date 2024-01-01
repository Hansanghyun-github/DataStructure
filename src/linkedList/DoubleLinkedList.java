package linkedList;

public class DoubleLinkedList<T> implements List<T>{
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if(head == null){
            head = newNode;
            tail = newNode;

            size++;
            return;
        }

        tail.next = newNode;
        newNode.prev = tail;
        tail = tail.next;

        size++;
    }

    @Override
    public boolean remove(T data) {
        if(data.equals(head.data)){
            head.data = null;
            Node<T> temp = head;
            head = head.next;
            head.prev = null;
            temp.next = null;

            size--;
            return true;
        }
        if(data.equals(tail.data)){
            tail.data = null;
            Node<T> temp = tail;
            tail = tail.prev;
            temp.prev = null;
            tail.next = null;

            size--;
            return true;
        }
        for(Node<T> x = head.next; x != null; x = x.next){
            if(data.equals(x.data)){
                Node<T> nextNode = x.next;
                Node<T> prevNode = x.prev;

                prevNode.next = nextNode;
                nextNode.prev = prevNode;

                x.next = null;
                x.prev = null;
                x.data = null;

                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for(Node<T> x = head; x != null;){
            Node<T> temp = x.next;
            x.prev = null;
            x.next = null;
            x.data = null;

            x = temp;
        }

        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public T get(int index) {
        Node<T> x = head;
        for(;index > 0; index--)
            x = x.next;
        return x.data;
    }

    private static class Node<T>{
        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data) {
            this.data = data;
        }
    }
}
