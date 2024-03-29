# 해시테이블

키와 값을 효율적으로 저장하기 위해 사용하는 자료구조

> 리소스를 이용해서 시간복잡도를 줄이는 대표적인 자료구조

---

## 해시테이블의 핵심 요소

1. 해시테이블의 크기
2. 해시 함수(인덱싱)
3. 해시 충돌 전략


> 해시 충돌: 해시 함수가 서로 다른 키에 대해 동일한 해시 값을 내는 상황

> 해시 충돌 전략
> 
> 1. separate chainning  
> 동일한 인덱스를 가진 요소는 해당 인덱스와 연관된 링크된 목록에 저장하는 기법 (연결리스트 or 트리 이용)  
> 장점: 간단한 구현(새로운 버킷을 가리키기만 하면 됨), 동적 할당  
> 딘잠: 추가 메모리 오버헤드(포인터), Open Addressing보다 캐시 효율이 낮다(random access)
> > 추가로 특정 인덱스로 몰렸을 때 조회 등의 성능이 매우 낮아짐
> 
> 2. open addressing  
> 다른 해시 버킷에 해당 데이터를 삽입하는 방식  
> 장점: 연속된 공간에 데이터를 저장하기 때문에 Separate Chaining에 비하여 캐시 효율이 높다  
> (but 테이블의 크기가 커지면 캐시 적중률이 낮아져 캐시 효율이 낮아진다)  
> 단점: 삭제 연산이 비효율적

---

직접 구현 할 메서드

(시간복잡도는 충돌 없다고 가정)

boolean containsKey(K key) - O(1)

boolean containsValue(V value) - O(n)

V get(K key) - O(1)

V put(K key, V value) - O(1)

V remove(K key) - O(1)