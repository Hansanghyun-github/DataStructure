# Red-Black 트리

이진 탐색 트리(BST)의 한 종류
스스로 균형을 잡는 트리(self-balanced tree)
-> BST의 worst case의 단점을 개선(skewed binary tree - O(n))

> AVL 트리는 균형을 엄격하게 잡는다는 한계가 있음  
> 이를 개선한 트리가 레드-블랙 트리  
> (레드-블랙 트리는 균형을 덜 엄격하게 잡는다)

---

## 레드-블랙 트리의 속성

#1 모든 노드는 red or black

#2 루트 노드는 black  

#3 모든 nil(leaf) 노드는 black
   > nil 노드
   >
   > 존재하지 않음을 의미하는 노드  
   > 자녀가 없을 때 자녀를 nil 노드로 표기  
   > & 같이 있는 노드와 동등하게 취급
   >
   > -> leaf 노드는 nil 노드

#4 red의 자녀들은 모두 black  
   -> red는 연속으로 존재할 수 없다.

#5 임의의 노드에서 자손 nil 노드까지 가는 경로들의 black 수는 같다  
   (자기 자신은 카운트에서 제외)

> 노드 x의 black height
> 
> 노드 x에서 임의의 자손 nil 노드까지  
> 내려가는 경로에서 black의 수  
> (자기 자신은 카운트에서 제외)
> 
> #5를 만족해야 성립하는 개념

---

> 색을 바꾸면서 #5 속성을 유지하기
> 
> RB 트리가 #5를 만족하고 있고,  
> 두 자녀가 같은 색을 가질 때
> 
> 부모와 두 자녀의 색을 바꿔줘도 #5 속성은 여전히 만족한다.  
> (대신 #4 속성을 다시 체크 해줘야 겠지)

몇몇 case에서 위 방법을 이용해서 RB 트리는 균형을 잡는다.

---

## RB 트리가 균형 잡는 법

주로 #4, #5 속성을 위반하며, 이들을 해결하려고 구조를 바꾸다 보면 자연스럽게 트리의 균형이 잡히게 된다.

---

## Red-Black 트리 삽입 과정

삽입 전 RB 트리 속성 만족한 상태  

삽입 과정은 일반적인 BST와 동일

삽입 후 RB 트리 속성 위반 여부 확인

RB 트리 속성을 위반했다면 재조정

삽입하는 노드의 색은 Red

> 삽입하는 노드의 색이 red인 이유  
> -> 삽입 후에도 #5를 만족하기 위해

---

### 삽입 케이스 1. 삽입 후 #2(루트노드 블랙)을 위반했을 때  
-> 루트 노드를 black으로 바꿔준다.

> 이 case는 트리가 아예 비었을 때만 해당하는 case

### 삽입 케이스 2. 삽입 후 #4 속성을 위반 했을 때

> 삽입한 노드의 부모 노드는 red 일 때 #4 속성이 위반된다.  
> (부모 노드가 black인 건 위반하지 않는다)
> 
> -> 부모 노드가 red일 때만 생각하면 됨

1. 삽입 된 노드가 부모의 왼쪽 자녀 &  
   부모도 red고 할아버지의 왼쪽 자녀 &   
   삼촌(부모의 형제)은 black이라면 or 삼촌이 없다면
   ```
           black                   black            
          /     \                 /    
       red      black          red    
      /                       /
   red(new)                red(new)
   ```  
   -> 부모와 할아버지의 색을 바꾼 후, 할아버지를 기준으로 오른쪽으로 회전  
   (왼쪽 오른쪽을 바꿔도 성립)
   > AVL 트리의 왼쪽-왼쪽 편향 & 할아버지의 반대 자식이 black(or none)일 때의 case
2. 아래 그림처럼  
   삽입 된 노드가 부모의 왼쪽 자녀 &  
   부모도 red고 할아버지의 오른쪽 자녀 &  
   삼촌(부모의 형제)은 black이라면 or 삼촌이 없다면,
   ```
           black                   black            
          /     \                 /    
       red      black          red    
          \                       \
          red(new)                red(new)
   ```    
   -> 부모를 기준으로 왼쪽으로 회전한 뒤, 1번 방식으로 해결  
   (왼쪽 오른쪽을 바꿔도 성립)
   > AVL 트리의 왼쪽-오른쪽 편향 & 할아버지의 반대 자식이 black(or none)일 때의 case
3. 삽입된 red 노드의 부모도 red &  
   삼촌(부모의 형제)도 red라면  
   -> 부모와 삼촌을 black으로 바꾸고, 할아버지를 red로 바꾼 뒤  
   할아버지에서 다시 속성 위반 여부를 확인
   ```
           black
          /     \
       red      red
      /
   red(new)
   ```  
   (자식이 어느 위치에 있든 성립하는 case)

> Red-Black 트리에서 삽입하는 과정은,  
> #4 속성을 위반했을 때의 case가 대부분이다.  
> (#5를 피하는 case를 피하기 위해 일부러 red 노드를 넣음)

## Red-Black 트리 삭제 과정

삭제 전 RB 트리 속성 만족한 상태

삭제 과정은 일반적인 BST와 동일

삭제 후 RB 트리 속성 위반 여부 확인 (1)

RB 트리 속성을 위반했다면 재조정 (2)

### (1) 노드 삭제 후, 속성 위반 여부 확인 과정

> RB 트리에서 노드를 삭제할 때  
> 어떤 색이 삭제되는지를 먼저 체크해야 한다.

맨 처음 먼저 삭제되는 노드의 색을 알아야 한다.

1. 삭제하려는 노드의 자녀가 없거나 하나라면,  
   삭제되는 색 = 삭제되는 노드의 색  
   (여기서 자녀란 유효한 값을 가진 자녀를 의미(not nil 노드))

2. 삭제하려는 노드의 자녀가 둘이라면,  
   삭제되는 색 = 삭제되는 노드의 successor의 색

> 나중에 따질 삭제되는 위치도,  
> 삭제되는 노드의 자녀가 둘이라면, successor의 위치가 삭제되는 위치이다.

---

그 다음 색에 따라 경우의 수가 나뉜다.

삭제되는 색이 red라면, `어떠한 속성도 위반하지 않는다.`

삭제되는 색이 black이라면, #2, #4, #5 속성을 위반할 수 있다.

> 특수한 상황을 제외하면,  
> 삭제되는 색이 black일 때는 대부분 #5를 위반하게 된다.

### (2) 속성 위반했을 때 재조정 과정

삭제되는 색이 black이고 #2 속성을 위반한다면, `루트 노드를 black으로 바꾸면 된다.`  
(매우 특수한 상황)

삭제되는 색이 black이고 #5 속성을 위반한다면, `extra black을 부여한다.`  
(#5 속성을 다시 만족시키기 위해)

> extra black의 역할
> 
> 경로에서 black 수를 카운트 할 때,  
> extra black은 하나의 black으로 카운트 된다.

---

삭제되는 색이 black이고 #5 속성을 위반했을 때, extra black 부여 결과

extra black을 부여 받은 노드는  
doubly black이 되거나  
red-and-black이 된다.

> 이제는 doubly black이나, red-and-black을 해결해야 한다.

red-and-black 해결하기 -> red-and-black을 black으로 변경

---

### doubly black 해결하기

> 현재 상황  
> black 노드를 삭제해서 #5 속성을 위반한 상태,  
> 삭제된 위치에 extra black을 부여했더니,  
> 해당 노드가 doubly black 노드가 되었다.  
> 이를 해결해야 한다.  
> (doubly black을 없애야 한다)

doubly black에서 extra black을 없애는 방법은 총 4가지로 분류된다.

네가지 case로 분류하는 기준  
-> doubly black의 형제의 색과, 형제의 자녀들의 색

---

`1. doubly black의 오른쪽 형제가 black & 그 형제의 오른쪽 자녀가 red일 때`

```
        a                     any
       / \                   /   \
      b   c                db     black
         / \                     /     \
        d   e                   any    red
```  
(b가 doubly black)

> (1) 형제의 색(c)은 부모의 색(a)으로,  
> (2) 형제의 자녀의 색(e)은 black으로,  
> (3) 부모의 색(a)은 black으로,  
> (4) 왼쪽으로 회전
> 
> > 반대도 성립

결과  
```
       c             any(이전 a의 색)
      / \           /   \
     a   e       black  black
    / \         /     \
   b  d        black  any
```

---

`2. doubly black의 형제가 black & 형제의 왼쪽 자녀가 red & 형제의 오른쪽 자녀가 black`

```
        a                     any
       / \                   /   \
      b   c                db     black
         / \                     /     \
        d   e                   red    black
```  
(b가 doubly black)

> (1) doubly black의 형제의 오른쪽 자녀를 red가 되게 만들어서  
> (c와 d의 색을 바꾸고 오른쪽 회전)  
> (2) 1번 case를 적용하여 해결
> 
> > 반대도 성립

---

`3. doubly black의 형제가 black & 형제의 자녀도 모두 black`

```
        a                     any
       / \                   /   \
      b   c                db     black
         / \                     /     \
        d   e                 black   black
``` 

> doubly black과 그 형제의 black을 부모에게 전달  
> (형제 노드는 red가 된다)  
> -> 부모가 extra black을 해결하도록 위임

```
   rb or db
     /   \
  black   red
        /     \
     black   black
```

---

`4. doubly black의 형제가 red`

```
        a                     any
       / \                   /   \
      b   c                db     red
         / \                    /     \
        d   e                black   black
``` 

> 부모와 형제의 색을 바꾸고,  
> 부모를 기준으로 왼쪽으로 회전하고,  
> doubly black을 기준으로  
> 위 case들 중 하나로 해결

```
        c              any
       / \            /   \
      a   e         red   black
     / \           /   \
    b   d         db   black
```  
(db의 형제노드의 정보를 다시 체크해야 함)

---

## Red-Black 트리 삭제 시나리오

1. 삭제되는 노드의 color가 red인가? -> 속성 위반 없음 (탈출)
2. 삭제되는 위치의 노드의 color가 red인가 -> Red and Black 케이스 (탈출) 
3. Doubly Black 케이스 처리 - 겁나 귀찮

---

> 삽입은 #4를 해결하는 것이 문제고,  
> 삭제는 #5를 해결하는 것이 문제네

---

## AVL 트리 vs Red-Black 트리

시간복잡도

| -      | AVL 트리 | Red-Black 트리 |
|--------|--------|--------------|
| insert |O(logn)|O(logn)|
| delete |O(logn)|O(logn)|
| search |O(logn)|O(logn)|

차이점

|-| AVL 트리   | Red-Black 트리 |
|--|----------|--------------|
|삽입/삭제 성능| RB보다 느리다 | AVL보다 빠르다    |
|조회 성능| RB보다 빠리다 | AVL보다 느리다    |
|균형 잡는 방식|BF = {-1,0,1} 되도록|Red-Black 속성 만족하도록|

> AVL 트리는 균형을 엄격하게 잡기 때문에,  
> Red-Black 트리보다 삽입/삭제 성능이 느리다.  
> 
> 하지만 엄격하게 잡기 때문에,  
> 조회 성능이 살짝 빠르다.

> 조회 성능이 빠르다고 해도, 유의미한 차이가 있다고는 생각하지 않는다.  
> (결국 AVL 트리나 RB 트리 모두 원소의 개수는 같기 때문)
> 
> 그래서 구현은 조금 힘들지만 삽입/삭제 성능이 빠른 RB 트리를 많이 사용하는 것 같다.

---

### reference

https://www.youtube.com/watch?v=6drLl777k-E&list=TLPQMTkwMTIwMjT3v3Is8U0ELA&index=2





