package queue;

 interface CustomQueue<E> {
     boolean add(E e);

     E remove();

     E element();

     boolean isEmpty();

     void clear();
 }
