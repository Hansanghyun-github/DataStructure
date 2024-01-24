package tree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static tree.RedBlackTree.Color.*;
import static tree.RedBlackTree.Violation.*;

class RedBlackTreeTest {

    RedBlackTree tree;
    @BeforeEach
    public void beforeEach(){
        tree = new RedBlackTree();
    }

    @Test
    @DisplayName("삽입 후 속성 위반x")
    void insertNotAttributeViolation() throws Exception {
        // given
        tree.add(2);
        tree.add(1);

        // when
        tree.add(3);

        // then
        assertThat(tree.violations.size()).isZero();

        assertThat(tree.size).isEqualTo(3);

        RedBlackTree.Node root = tree.root;
        rootNodeTest(root, 1, 2, 3, Black);
        leafNodeTest(root.left, root, 1, Red);
        leafNodeTest(root.right, root, 3, Red);
    }

    @Test
    @DisplayName("삽입 후 2번 속성 위반")
    void insertAttribute2Violation() throws Exception {
        // when
        tree.add(1);

        // then
        assertThat(tree.violations.size()).isOne();
        assertThat(tree.violations.contains(ADD_VIOLATION2)).isTrue();

        assertThat(tree.size).isOne();

        rootNodeTest(tree.root, EMPTY, 1, EMPTY, Black);
    }

    @Test
    @DisplayName("삽입 후 4번 속성 위반 케이스 1")
    void insertAttribute4ViolationCase1LeftVersion() throws Exception {
        // given
        tree.add(2);
        tree.add(1);

        // when
        tree.add(0);

        // then
        assertThat(tree.violations.size()).isOne();
        assertThat(tree.violations.contains(ADD_VIOLATION4_1)).isTrue();

        assertThat(tree.size).isEqualTo(3);

        RedBlackTree.Node root = tree.root;
        rootNodeTest(root, 0, 1, 2, Black);
        leafNodeTest(root.left, root, 0, Red);
        leafNodeTest(root.right, root, 2, Red);
    }

    @Test
    @DisplayName("삽입 후 4번 속성 위반 케이스 2")
    void insertAttribute4ViolationCase2() throws Exception {
        // given
        tree.add(3);
        tree.add(1);

        // when
        tree.add(2);

        // then
        assertThat(tree.violations.size()).isOne();
        assertThat(tree.violations.contains(ADD_VIOLATION4_2)).isTrue();

        assertThat(tree.size).isEqualTo(3);

        RedBlackTree.Node root = tree.root;
        rootNodeTest(root, 1, 2, 3, Black);
        leafNodeTest(root.left, root, 1, Red);
        leafNodeTest(root.right, root, 3, Red);
    }

    @Test
    @DisplayName("삽입 후 4번 속성 위반 케이스 3")
    void insertAttribute4ViolationCase3() throws Exception {
        // given
        tree.add(2);
        tree.add(1);
        tree.add(3);

        // when
        tree.add(0);

        // then
        assertThat(tree.violations.size()).isEqualTo(2);
        assertThat(tree.violations.contains(ADD_VIOLATION4_3)).isTrue();

        assertThat(tree.size).isEqualTo(4);

        RedBlackTree.Node root = tree.root;
        rootNodeTest(root, 1, 2, 3, Black);
        nodeTest(root.left, root, 0, 1, EMPTY, Black);
        leafNodeTest(root.left.left, root.left, 0, Red);
        leafNodeTest(root.right, root, 3, Black);
    }

    @Test
    @DisplayName("삭제 후 속성 위반 없음(red 노드 삭제 케이스)")
    void removeNotAttributeViolation() throws Exception {
        // given
        tree.add(2);
        tree.add(1);
        tree.add(3);

        // when
        boolean b = tree.remove(1);

        // then
        assertThat(b).isTrue();
        assertThat(tree.violations.size()).isZero();

        RedBlackTree.Node root = tree.root;

        rootNodeTest(root, 1, 2, EMPTY, Black);
        leafNodeTest(root.left, root, 1, Red);
    }

    @Test
    @DisplayName("Red And Black 케이스")
    void removeRedAndBlackCase() throws Exception {
        // given
        tree.add(2);
        tree.add(1);
        tree.add(3);
        tree.add(0);

        // when
        tree.remove(1);

        // then
        assertThat(tree.violations.size()).isOne();
        assertThat(tree.violations.contains(RED_AND_BLACK)).isTrue();

        RedBlackTree.Node root = tree.root;

        rootNodeTest(root, 0, 2, 3, Black);
        leafNodeTest(root.left, root, 0, Black);
        leafNodeTest(root.right, root, 3, Black);
    }

    @Test
    @DisplayName("Doubly Black 케이스 1")
    void doublyBlackCase1() throws Exception {
        // given
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(2);
        tree.add(7);
        tree.add(12);
        tree.add(17);
        tree.add(9);

        // when
        tree.remove(2);

        // then
        assertThat(tree.violations.size()).isOne();
        assertThat(tree.violations.contains(DOUBLY_BLACK1)).isTrue();

        assertThat(tree.size).isEqualTo(7);

        RedBlackTree.Node root = tree.root;

        rootNodeTest(root, 5, 10, 15, Black);
        nodeTest(root.left, root, 5, 7, 9, Red);
        leafNodeTest(root.left.left, root.left, 5, Black);
        leafNodeTest(root.left.right, root.left, 9, Black);
        nodeTest(root.right, root, 12, 15, 17, Black);
        leafNodeTest(root.right.left, root.right, 12, Red);
        leafNodeTest(root.right.right, root.right, 17, Red);
    }

    @Test
    @DisplayName("Doubly Black 케이스 2")
    void doublyBlackCase2() throws Exception {
        // given
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(2);
        tree.add(7);
        tree.add(12);
        tree.add(17);
        tree.add(6);

        // when
        tree.remove(2);

        // then
        assertThat(tree.violations.size()).isOne();
        assertThat(tree.violations.contains(DOUBLY_BLACK2)).isTrue();

        assertThat(tree.size).isEqualTo(7);

        RedBlackTree.Node root = tree.root;

        rootNodeTest(root, 5, 10, 15, Black);
        nodeTest(root.left, root, 5, 6, 7, Black);
        leafNodeTest(root.left.left, root.left, 5, Red);
        leafNodeTest(root.left.right, root.left, 9, Red);
        nodeTest(root.right, root, 12, 15, 17, Black);
        leafNodeTest(root.right.left, root.right, 12, Red);
        leafNodeTest(root.right.right, root.right, 17, Red);
    }

    @Test
    @Disabled
    @DisplayName("Doubly Black 케이스 3")
    void doublyBlackCase3() throws Exception {
        // given

        // when

        // then
        assertThat(tree.violations.contains(DOUBLY_BLACK3)).isTrue();
    }

    @Test
    @Disabled
    @DisplayName("Doubly Black 케이스 4")
    void doublyBlackCase4() throws Exception {
        // given

        // when

        // then
        assertThat(tree.violations.contains(DOUBLY_BLACK4)).isTrue();
    }

    private void rootNodeTest(RedBlackTree.Node target,
                              int leftData,
                              int data,
                              int rightData,
                              RedBlackTree.Color color){
        assertThat(target).isEqualTo(tree.root);
        nodeTest(target, null, leftData, data, rightData, color);
    }

    private void nodeTest(RedBlackTree.Node target,
                          RedBlackTree.Node parent,
                          int leftData,
                          int data,
                          int rightData,
                          RedBlackTree.Color color){
        assertThat(target).isNotNull();
        assertThat(target.data).isEqualTo(data);
        assertThat(target.parent).isEqualTo(parent);
        assertThat(target.color).isEqualTo(color);

        if(leftData == EMPTY)
            assertThat(target.left).isNull();
        else{
            assertThat(target.left).isNotNull();
            assertThat(target.left.data).isEqualTo(leftData);
        }

        if(rightData == EMPTY)
            assertThat(target.right).isNull();
        else{
            assertThat(target.right).isNotNull();
            assertThat(target.right.data).isEqualTo(rightData);
        }
    }

    private void leafNodeTest(RedBlackTree.Node target,
                              RedBlackTree.Node parent,
                              int data,
                              RedBlackTree.Color color){
        nodeTest(target, parent, EMPTY, data, EMPTY, color);
    }

    private static final int EMPTY = -987654321;
}