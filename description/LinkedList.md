## LinkedList의 특징

각 노드가 데이터와 포인터를 가지고 연결되어 있는 방식으로 데이터를 저장하는 자료 구조

인덱스가 존재하지 않기 떄문에, 검색 및 수정시 첫 번째 노드부터 순차적으로 모든 노드를 검색해야 한다.

대신 맨 앞 노드의 삽입 & 삭제시 O(1)만에 해결할 수 있다.

> ArrayList는 O(n)이 걸린다.
> (삽입 & 삭제 후, 한 칸씩 땡겨야 하기 때문)

---

직접 구현 할 메서드 리스트

boolean	add(E e) - 맨 뒤 노드 다음에 추가

void	clear() - 모든 노드를 삭제

boolean	contains(Object o) - o라는 원소를 가지고 있는 노드가 있는지 반환

E	get(int index) - index 번째 노드의 원소를 반환

int	indexOf(Object o) - o라는 원소를 가지고 있는 노드의 번호를 반환

boolean	isEmpty() - 연결 리스트가 비었는지 반환

E	remove(int index) - index 번째 노드의 원소를 삭제

boolean	remove(Object o) - o라는 원소를 가지고 있는 노드를 삭제

E	set(int index, E element) - index 번째 노드의 원소를 바꿈

int	size() - 연결 리스트의 사이즈를 반환

Object[]	toArray() - 연결 리스트의 노드들을 배열로 반환