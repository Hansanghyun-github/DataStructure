package linkedList;

public interface List<T> {
    void add(T data);
    boolean remove(T data);
    int size();
    void clear();
    boolean isEmpty();
    T get(int index);
}
