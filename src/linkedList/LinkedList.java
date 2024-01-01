package linkedList;

public class LinkedList<T> implements List<T>{
    private int size;
    private Node<T> head;

    @Override
    public void add(T data){ // 마지막 위치에 노드 추가
        if(head == null){
            head = new Node<>(data);
            size++;
            return;
        }

        Node<T> newNode = new Node<>(data);
        Node<T> temp = head;

        while(temp.next != null) { temp = temp.next; }

        temp.next = newNode;

        size++;
    }

    @Override
    public boolean remove(T data){ // data에 해당하는 노드 찾아서 해당 노드 삭제
        if(data.equals(head.data)){
            head = head.next;
            size--;
            return true;
        }

        Node<T> temp = head;
        Node<T> prevTemp = temp;

        while(temp.next != null){
            if(data.equals(temp.data)) {
                prevTemp.next = temp.next;
                temp.data = null;
                temp.next = null;
                size--;
                return true;
            }
            prevTemp = temp;
            temp = temp.next;
        }

        return false;
    }

    @Override
    public int size(){ return size; }

    @Override
    public void clear() {
        for(Node<T> x = head; x != null;) {
            Node<T> next = x.next;
            x.data = null;
            x.next = null;
            x = next;
        }

        head = null;
        size = 0;
    }

    @Override
    public T get(int index){
        if(index < 0) throw new IndexOutOfBoundsException("Invalid index");

        Node<T> temp = head;
        for(;index != 0; index--){
            if(temp.next == null) throw new IndexOutOfBoundsException("Invalid index");
            temp = temp.next;
        }
        return temp.data;
    }

    @Override
    public boolean isEmpty(){
        return (size == 0);
    }

    private static class Node<T>{
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }
}
