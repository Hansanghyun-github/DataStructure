package heap;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomPriorityQueueTest {
    @Test
    @DisplayName("addTest")
    void addTest() throws Exception {
        // given
        CustomPriorityQueue pQ = new CustomPriorityQueue();

        pQ.add(1);
        pQ.add(2);
        List<Integer> list1 = pQ.getAll();

        // then
        assertThat(list1).isEqualTo(List.of(0, 2,1));
        pQ.add(3);
        List<Integer> list2 = pQ.getAll();

        // then
        assertThat(list2).isEqualTo(List.of(0, 3, 1, 2));
        pQ.add(4);

        /**
         * 1    ->     2    ->    3    ->    4
         *           1          1  2       3   2
         *                               1
         * */


        // when
        List<Integer> list = pQ.getAll();

        // then
        assertThat(list).isEqualTo(List.of(0, 4, 3, 2, 1));
    }
    
    @Test
    @DisplayName("removeTest")
    void removeTest() throws Exception {
        // given
        CustomPriorityQueue pQ = new CustomPriorityQueue();
        pQ.add(1);
        pQ.add(2);
        pQ.add(3);
        pQ.add(4);
        assertThat(pQ.getAll()).isEqualTo(List.of(0, 4, 3, 2, 1));

        /**
         *          4      ->     3
         *       3     2        1   2
         *     1
         * */
        
        // when
        pQ.remove();
        
        // then
        assertThat(pQ.getAll()).isEqualTo(List.of(0, 3, 1, 2));
    }

}