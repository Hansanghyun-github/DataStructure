package linkedList;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class CustomLinkedList<E> implements CustomList<E>{
    // TODO index의 위치를 중심을 기준으로 first/last 시작 위치 세팅 -> 최적화
    // TODO OutOfBound 체크 메서드 생성 & 리팩토링
    private int modCount = 0;
    private int size = 0;

    private Node<E> first;
    private Node<E> last;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        for(Node<E> x = first; x != null; x = x.next){
            if(x.item == o)
                return true;
        }

        // return indexOf(o) >= 0

        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[size];
        int i = 0;
        for(Node<E> x = first; x != null; x = x.next)
            objects[i++] = x.item;
        return objects;
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    private void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = (Node<E>) new Node<>(l, e, null);

        last = newNode;
        if(l == null)
            first = newNode;
        else
            l.next = newNode; // 멤버 변수를 가리키는 것이라서 가능

        size++;
        modCount++;
    }

    // succ is non-null
    private void linkBefore(E e, Node<E> succ) {
        Node<E> prev = succ.prev;
        Node<E> newNode = new Node<>(prev, e, succ);
        succ.prev = newNode;
        if(prev == null)
            first = newNode;
        else
            prev.next = newNode;

        size++;
        modCount++;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if(index == -1)
            return false;
        unlink(node(index));
        return true;
    }

    @Override
    public void clear() {
        for(Node<E> x = first; x != null; x = x.next){
            Node<E> next = x.next;
            x.prev = null;
            x.item = null;
            x.next = null;
            x = next;
        }

        first = null;
        last = null;
        size = 0;
        modCount++;
    }

    @Override
    public E remove(int index) {
        return unlink(node(index));
    }

    @Override
    public E get(int index) {
        return node(index).item;
    }

    @Override
    public E set(int index, E element) {
        Node<E> target = node(index);

        E oldVal = target.item;
        target.item = element;

        return oldVal;
    }

    // target is not null
    private E unlink(Node<E> target) {
        final Node<E> prev = target.prev;
        final Node<E> next = target.next;
        final E item = target.item;

        if(first == target && last == target){
            first = null;
            last = null;
        } else if(first == target) {
            first = next;
            next.prev = null;
        } else if(last == target) {
            last = prev;
            prev.next = null;
        } else {
            prev.next = next;
            next.prev = prev;
        }

        target.prev = null;
        target.next = null;
        target.item = null;
        size--;
        modCount++;
        return item;
    }

    @Override
    public int indexOf(Object o) {
        int index = 0;
        for(Node<E> x = first; x != null; x = x.next){
            if(o.equals(x.item))
                return index;
            index++;
        }
        return -1;
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    private Node<E> node(int index) {
        Node<E> x = first;
        for(int i = 0;i < index;i++) {
            if(x == null)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
            x = x.next;
        }

        return x;
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node<E> prev;
        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

    }


}
