package tree;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toCollection;
import static org.assertj.core.api.Assertions.*;

public class TreePerformanceTest {
    static List<Integer> ranNum;
    static final int ranL = 1000000;

    @BeforeAll
    public static void beforeAll(){
        setRandomNumber();
    }
    @Test
    @DisplayName("AVL 트리 vs Red-Black 트리 insert 성능 비교")
    void compareInsertPerformance() throws Exception {
        // given
        AvlTree atree = new AvlTree();
        RedBlackTree rbtree = new RedBlackTree();

        // when // then
        long sTime = System.currentTimeMillis(); // 시작시간

        // 코드 입력 부분
        for(int n: ranNum){
            atree.add(n);
        }

        // (현재시간 - 시작시간) / 1000
        Double sec = (System.currentTimeMillis() - sTime) / 1000.0;
        System.out.printf("AVL 트리 insert 소요시간 --- (%f초)%n", sec);

        sTime = System.currentTimeMillis(); // 시작시간

        // 코드 입력 부분
        for(int n: ranNum){
            rbtree.add(n);
        }

        // (현재시간 - 시작시간) / 1000
        sec = (System.currentTimeMillis() - sTime) / 1000.0;
        System.out.printf("Red-Black 트리 insert 소요시간 --- (%f초)%n", sec);
    }
    
    @Test
    @DisplayName("AVL 트리 vs Red-Black 트리 delete 성능 비교")
    void compareDeletePerformance() throws Exception {
        // given
        AvlTree atree = new AvlTree();
        RedBlackTree rbtree = new RedBlackTree();

        for(int n: ranNum){
            atree.add(n);
        }
        for(int n: ranNum){
            rbtree.add(n);
        }

        // when // then
        long sTime = System.currentTimeMillis(); // 시작시간

        for(int n: ranNum){
            atree.remove(n);
        }

        // (현재시간 - 시작시간) / 1000
        Double sec = (System.currentTimeMillis() - sTime) / 1000.0;
        System.out.printf("AVL 트리 delete 소요시간 --- (%f초)%n", sec);

        sTime = System.currentTimeMillis(); // 시작시간

        for(int n: ranNum){
            rbtree.remove(n);
        }

        // (현재시간 - 시작시간) / 1000
        sec = (System.currentTimeMillis() - sTime) / 1000.0;
        System.out.printf("Red-Black delete 트리 소요시간 --- (%f초)%n", sec);
    }

    private static void setRandomNumber() {
        ranNum = IntStream.rangeClosed(1, ranL).boxed().collect(toCollection(ArrayList::new));
        Collections.shuffle(ranNum, new Random(5));

        System.out.println("number of node: " + ranNum.size());
    }
}
