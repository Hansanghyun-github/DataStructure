package linkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class CustomLinkedListTest {
    CustomList<Integer> customList;
    List<Integer> novelList;

    @BeforeEach
    public void beforeEach(){
        if(customList != null) customList.clear();
        if(novelList != null) novelList.clear();
        customList = new CustomLinkedList<>();
        novelList = new LinkedList<>();
    }

    @AfterEach
    public void afterEach() {
        assertThat(customList.isEmpty()).isEqualTo(novelList.isEmpty());
        assertThat(customList.size()).isEqualTo(novelList.size());
        int size = customList.size();
        for(int i=0;i<size;i++)
            assertThat(customList.get(i))
                    .isNotNull()
                    .isEqualTo(novelList.get(i));
    }

    @Test
    @DisplayName("first가 null일 때 add")
    void addWhenFirstIsNull() throws Exception {
        customList.add(10);
        novelList.add(10);
    }

    @Test
    @DisplayName("add 두번")
    void addTwice() throws Exception {
        customList.add(1);
        novelList.add(1);
        customList.add(2);
        novelList.add(2);
    }

    @Test
    @DisplayName("clear")
    void clear() throws Exception {
        // given
        customList.add(1);
        novelList.add(1);
        customList.add(2);
        novelList.add(2);

        // when
        customList.clear();
        novelList.clear();

        // then
    }

    @Test
    @DisplayName("remove first")
    void removeFirst() throws Exception {
        // given
        customList.add(1);
        novelList.add(1);

        // when
        customList.remove(Integer.valueOf(1));
        novelList.remove(Integer.valueOf(1));

        // then
    }

    @Test
    @DisplayName("remove")
    void remove() throws Exception {
        // given
        customList.add(1);
        novelList.add(1);
        customList.add(2);
        novelList.add(2);

        // when
        customList.remove(Integer.valueOf(1));
        novelList.remove(Integer.valueOf(1));

        // then
    }

    @Test
    @DisplayName("remove last")
    void removeLast() throws Exception {
        // given
        customList.add(1);
        novelList.add(1);
        customList.add(2);
        novelList.add(2);

        // when
        customList.remove(Integer.valueOf(2));
        novelList.remove(Integer.valueOf(2));

        // then
    }

    @Test
    @DisplayName("remove specific index")
    void removeSpecificIndex() throws Exception {
        // given
        customList.add(1);
        novelList.add(1);
        customList.add(2);
        novelList.add(2);
        customList.add(3);
        novelList.add(3);
        customList.add(4);
        novelList.add(4);

        // when
        customList.remove(2);
        novelList.remove(2);

        // then
    }

    @Test
    @DisplayName("set")
    void set() throws Exception {
        // given
        customList.add(1);
        novelList.add(1);
        customList.add(2);
        novelList.add(2);
        customList.add(3);
        novelList.add(3);

        // when
        customList.set(1, 10);
        novelList.set(1, 10);

        // then
    }

    @Test
    @DisplayName("indexOf")
    void indexOf() throws Exception {
        // given
        customList.add(1);
        novelList.add(1);
        customList.add(2);
        novelList.add(2);
        customList.add(3);
        novelList.add(3);

        // when
        assertThat(customList.indexOf(1)).isEqualTo(novelList.indexOf(1));
        assertThat(customList.indexOf(2)).isEqualTo(novelList.indexOf(2));
        assertThat(customList.indexOf(3)).isEqualTo(novelList.indexOf(3));

        // then
    }
    
    @Test
    @DisplayName("contains")
    void contains() throws Exception {
        // given
        customList.add(1);
        novelList.add(1);
        customList.add(2);
        novelList.add(2);
        customList.add(3);
        novelList.add(3);
        
        // when
        assertThat(customList.contains(2)).isEqualTo(novelList.contains(2));
        assertThat(customList.contains(4)).isEqualTo(novelList.contains(4));
        
        // then
    }
}