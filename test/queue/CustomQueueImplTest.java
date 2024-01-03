package queue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomQueueImplTest {
    Queue<Integer> novelQueue;
    CustomQueue<Integer> customQueue;

    @BeforeEach
    public void beforeEach(){
        if(novelQueue != null) novelQueue.clear();
        if(customQueue != null) customQueue.clear();
        novelQueue = new LinkedList<>();
        customQueue = new CustomQueueImpl<>();
    }

    @AfterEach
    public void afterEach(){
        while(!novelQueue.isEmpty()){
            assertThat(novelQueue.element()).isEqualTo(customQueue.element());
            novelQueue.remove();
            customQueue.remove();
        }
    }

    @Test
    @DisplayName("add 두번")
    void addTwice() throws Exception {
        // given

        // when
        add(2);

        // then
    }

    void add(int index) {
        for(int i=0;i<index;i++){
            novelQueue.add(i);
            customQueue.add(i);
        }
    }
}