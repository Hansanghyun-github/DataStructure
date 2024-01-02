package linkedList;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class CustomLinkedListTest {
    List<Integer> customList;
    List<Integer> novelList;

    @BeforeEach
    public void beforeEach(){
        if(customList != null) customList.clear();
        if(novelList != null) novelList.clear();
        customList = new CustomLinkedList<>();
        novelList = new LinkedList<>();
    }

    // 그냥 내가 만든 LinkedList가 원본 LinkedList랑 결과가 같은지 테스트하는 거라서,
    // DisplayName에 테스트라는 단어를 넣음

    @Test
    @DisplayName("first가 null일 때 add 테스트")
    void testAddWhenFirstIsNull() throws Exception {
        // given
        
        // when
        
        // then
    }



}