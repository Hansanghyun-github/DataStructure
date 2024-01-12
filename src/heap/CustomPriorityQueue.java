package heap;

import java.util.Arrays;
import java.util.List;

public class CustomPriorityQueue { // 최대 힙
    Integer[] queue;
    // root index = 1 (not 0)

    int index;

    public CustomPriorityQueue() {
        queue = new Integer[50]; // 그냥 구현이 목적이기 때문에 limit 50으로 설정
        queue[0] = 0; // 루트 인덱스 1번 유지하기 위해

        index = 1;
    }

    /**
     * 1. 배열 마지막 위치에 add
     * 2. 부모 노드의 값이 크도록 배열 값 업데이트
     *
     * (부모노드 index = 자식노드 index / 2)
     * */
    public boolean add(Integer data) {
        queue[index] = data;
        siftUp();
        index++;
        return true;
    }

    private void siftUp() {
        int curIdx = this.index;
        while(parentIdx(curIdx) != 0) {
            int parentIdx = parentIdx(curIdx);

            Integer parent = queue[parentIdx];
            Integer cur = queue[curIdx];
            if(parent < cur) {
                queue[parentIdx] = cur;
                queue[curIdx] = parent;
            }

            curIdx = parentIdx;
        }
    }

    /**
     * 1. 마지막 데이터 1번으로 이동
     * 2. 마지막 데이터 노드 삭제
     * 3. 부모 노드의 값이 크도록 리스트 값 업데이트
     *
     * (부모노드 index = 자식노드 index / 2)
     * */
    public boolean remove() {
        queue[1] = queue[index - 1];
        queue[index - 1] = null;

        siftDown();

        index--;
        return true;
    }

    private void siftDown() {
        int curIdx = 1;
        while(rightChildIdx(curIdx) < index - 1) {
            int leftChildIdx = leftChildIdx(curIdx);
            int rightChildIdx = rightChildIdx(curIdx);

            Integer leftChild = queue[leftChildIdx];
            Integer rightChild = queue[rightChildIdx];

            if(leftChild < rightChild){
                if(rightChild > queue[curIdx]){
                    queue[rightChildIdx] = queue[curIdx];
                    queue[curIdx] = rightChild;
                }
                curIdx = rightChildIdx;
            } else {
                if(leftChild > queue[curIdx]){
                    queue[leftChildIdx] = queue[curIdx];
                    queue[curIdx] = leftChild;
                }
                curIdx = leftChildIdx;
            }
        }
    }

    private int parentIdx(int index) {
        return index / 2;
    }

    private int rightChildIdx(int index) {
        return index * 2 + 1;
    }

    private int leftChildIdx(int index) {
        return index * 2;
    }

    public Integer get() {
        return queue[1];
    }

    public List<Integer> getAll() {
        return Arrays.stream(queue)
                .limit(index)
                .toList();
    }
}
