## Queue의 특징

FIFO(선입선출) - 먼저 들어온 원소가, 가장 먼저 나간다.

---

직접 구현 할 메서드 리스트

boolean add(E o) - tail 뒤에 노드를 추가

E remove() - head를 삭제

E element() - 헤드의 원소를 반환

boolean isEmpty() - 큐가 비었는지 반환

void clear() - 큐의 노드들을 전부 삭제