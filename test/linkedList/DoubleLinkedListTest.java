package linkedList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DoubleLinkedListTest {
    @Test
    @DisplayName("커스텀 링크드리스트와 util 링크드리스트 비교")
    void linkedListTest() throws Exception {
        List<Integer> list = new java.util.LinkedList<>();
        DoubleLinkedList<Integer> customList = new DoubleLinkedList<>();

        list.add(10);
        customList.add(10);

        list.add(12);
        customList.add(12);
        list.add(14);
        customList.add(14);

        list.remove(Integer.valueOf(12));
        customList.remove(Integer.valueOf(12));

        assertThat(list.size()).isEqualTo(customList.size());

        for(int i=0;i<list.size();i++)
            assertThat(list.get(i)).isEqualTo(customList.get(i));

        list.clear();
        customList.clear();

        assertThat(list.size()).isEqualTo(customList.size());

        list.add(10);
        customList.add(10);
        list.add(16);
        customList.add(16);

        list.remove(Integer.valueOf(10));
        customList.remove(Integer.valueOf(10));

        for(int i=0;i<list.size();i++)
            assertThat(list.get(i)).isEqualTo(customList.get(i));

    }


}