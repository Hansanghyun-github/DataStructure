# 해시테이블

키와 값을 효율적으로 저장하기 위해 사용하는 자료구조

> 리소스를 이용해서 시간복잡도를 줄이는 대표적인 자료구조

---

## 해시테이블의 핵심 요소

1. 해시테이블의 크기
2. 해시 함수(인덱싱)
3. 해시 충돌 전략


> 해시 충돌: 해시 함수가 서로 다른 두 개의 입력값에 대해 동일한 출력값을 내는 상황

> 해시 충돌 전략
> 
> 1. separate chainning  
> 동일한 인덱스를 가진 요소는 해당 인덱스와 연관된 링크된 목록에 저장하는 기법 (연결리스트 or 트리 이용)  
> 장점: 간단한 구현(새로운 버킷을 가리키기만 하면 됨), 동적 할당 
> 딘잠: 추가 메모리 오버헤드
> > 추가로 특정 인덱스로 몰렸을 때 조회 등의 성능이 매우 낮아짐
> 
> 2. open addressing  
> array에서 사용 가능한 다음(빈) 슬롯을 찾는 기법  
> 장점: 메모리 오버헤드 적음(있는 거 사용하니까)  
> 단점: 구현이 복잡해질 수 있음

---

직접 구현 할 메서드

(시간복잡도는 충돌 없다고 가정)

boolean containsKey(K key) - O(1)

boolean containsValue(V value) - O(n)

V get(K key) - O(1)

V put(K key, V value) - O(1)

V remove(K key) - O(1)