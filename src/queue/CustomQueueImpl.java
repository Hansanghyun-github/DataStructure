package queue;

public class CustomQueueImpl<E> implements CustomQueue<E> {
    Node<E> head;
    Node<E> tail;

    @Override
    public boolean add(E e) {
        Node<E> newNode = new Node<>(e, null);

        if(head == null){
            head = newNode;
            tail = newNode;
            return true;
        }

        tail.next = newNode;
        tail = tail.next;

        return true;
    }

    @Override
    public E remove() {
        if(head == null) return null;

        E item = head.item;
        if(head.next == null){
            head = null;
            tail = null;

            return item;
        }

        Node<E> next = head.next;
        head.next = null;
        head.item = null;
        head = next;

        return item;
    }

    @Override
    public E element() {
        return head.item;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void clear() {
        for(Node<E> x = head; x != null; x = x.next){
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x = next;
        }
        head = null;
        tail = null;
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }
}
