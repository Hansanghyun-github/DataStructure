트리의 조건
- 루트노드는 딱 한개
- 사이클이 없다
- 부모노드는 딱 한개

이진탐색트리의 조건  
트리의 특징 + 자식 노드가 2개(왼쪽, 오른쪽 - 왼쪽이 작고, 오른쪽이 크다)

preorder - 중왼오 순서 순회

inorder - 왼중오 순서 순회

postorder - 왼오중 순서 순회

---

구현할 API

put(int data) - 이진트리에 data 노드 저장

contains(int data) - 해당 data 노드 있는지 반환

printPreorder() - 중왼오 순서

printInorder() - 왼중오 순서

printPostorder() - 왼오중 순서

remove(int data) - data에 해당하는 노드 삭제 & 레퍼런스 수정

---

### remove 케이스

0. 루트가 없을 때

1. 삭제 대상이 없을 때

2. 삭제 대상의 자식 노드가 없음 & 대상이 루트   
   > root = null

3. 삭제 대상의 자식 노드가 없음 & 대상 루트x   
   > 부모의 자식 노드(left or right) = null

4. 삭제 대상의 자식 노드가 1개(left or right) & 대상이 루트   
   > root = 자식 노드(left or right)

5. 삭제 대상의 자식 노드가 1개(left or right) & 대상이 루트x  
   > 삭제 대상의 부모노드의 자식(left or right) 노드 = 삭제 대상의 자식 노드

6. 삭제 대상의 자식 노드가 2개

> 삭제 대상의 자식 노드가 2개일 때
> 
> 대상 노드의 data 보다 큰 값을 가진 노드들 중에 가장 값이 작은 노드를 찾는다 - 후계자(successor)  
> 후계자 노드가 삭제 대상 노드에 위치하도록 변경
> 
> `후계자 노드 케이스`
> 1. 후계자가 바로 오른쪽에 있음 
>     > 타켓 노드의 right 노드 = 후계자의 right 노드(후계자는 left 노드가 없다)   
>     타겟노드의 data = 후계자 노드의 data (reference 변경 없음) 
> 2. 후계자 노드가 바로 오른쪽에x & 후계자의 자식 노드 없음
>     > 타겟노드의 data = 후계자 노드의 data (reference 변경 없음)
> 3. 후계자 노드가 바로 오른쪽에x & 후계자의 자식 노드 있음(right)
>     > 후계자 노드의 부모 노드의 left 노드 = 후계자의 right 노드  
>     타겟노드의 data = 후계자 노드의 data (reference 변경 없음)

---

### Reference 

https://st-lab.tistory.com/300#remove