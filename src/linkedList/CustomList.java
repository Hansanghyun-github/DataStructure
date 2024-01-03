package linkedList;

public interface CustomList<E> {
    boolean	add(E e);

    void	clear();

    boolean	contains(Object o);

    E	get(int index);

    int	indexOf(Object o);

    boolean	isEmpty();

    E	remove(int index);

    boolean	remove(Object o);

    E	set(int index, E element);

    int	size();

    Object[]	toArray();
}
