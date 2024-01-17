package tree;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class AvlTreeTest {
// TODO 특정 메서드가 어떤 값을 반환했고, 몇번 호출됐는지 알 수없나

    private static final int EMPTY = -987654321;

    @Test
    @DisplayName("right-right lead node tree test")
    void rotateLeftTest() throws Exception {
        // given
        AvlTree avlTree = new AvlTree();
        avlTree.add(1);
        avlTree.add(2);

        // when
        avlTree.add(3);

        /**
         * 1                    2
         *  \                 /  \
         *   2      ->       1    3
         *    \
         *     3
         * */

        // then
        AvlTree.Node root = avlTree.root;

        nodeTest(root, null, 1, 2, 3);
        leafNodeTest(root.left, root, 1);
        leafNodeTest(root.right, root, 3);
    }

    @Test
    @DisplayName("left-left leaf node tree test")
    void rotateRightTest() throws Exception {
        // given
        AvlTree avlTree = new AvlTree();
        avlTree.add(3);
        avlTree.add(2);

        // when
        avlTree.add(1);

        /**
         *     1                2
         *    /               /  \
         *   2      ->       1    3
         *  /
         * 3
         * */

        // then
        AvlTree.Node root = avlTree.root;
        nodeTest(root, null, 1, 2, 3);
        leafNodeTest(root.left, root, 1);
        leafNodeTest(root.right, root, 3);
    }

    @Test
    @DisplayName("left-left bias tree test")
    void leftLeftBiasTreeTest() throws Exception {
        // given
        AvlTree avlTree = new AvlTree();
        avlTree.add(4);
        avlTree.add(5);
        avlTree.add(2);
        avlTree.add(1);
        avlTree.add(3);

        // when
        avlTree.add(0);

        /**
         *          4                2
         *        /   \            /   \
         *       2     5   ->     1     4
         *      / \              /    /   \
         *     1   3            0    3     5
         *    /
         *   0(insert)
         * */

        // then
        AvlTree.Node root = avlTree.root;
        nodeTest(root, null, 1, 2, 4);
        nodeTest(root.left, root, 0, 1, EMPTY);
        nodeTest(root.right, root, 3, 4, 5);
        leafNodeTest(root.left.left, root.left, 0);
        leafNodeTest(root.right.left, root.right, 3);
        leafNodeTest(root.right.right, root.right, 5);
    }

    @Test
    @DisplayName("right-right bias tree test")
    void rightRightBiasTreeTest() throws Exception {
        // given
        AvlTree avlTree = new AvlTree();
        avlTree.add(2);
        avlTree.add(1);
        avlTree.add(4);
        avlTree.add(3);
        avlTree.add(5);

        // when
        avlTree.add(6);

        /**
         *     2                       4
         *   /   \                   /   \
         *  1      4       ->       2     5
         *       /   \            /   \    \
         *      3     5          1     3    6
         *             \
         *              6(insert)
         * */

        // then
        AvlTree.Node root = avlTree.root;
        nodeTest(root, null, 2, 4, 5);

        nodeTest(root.left, root, 1, 2, 3);
        leafNodeTest(root.left.left, root.left, 1);
        leafNodeTest(root.left.right, root.left, 3);

        nodeTest(root.right, root, EMPTY, 5, 6);
        leafNodeTest(root.right.right, root.right, 6);
    }

    @Test
    @DisplayName("left-right bias tree test")
    void leftRightBiasTreeTest() throws Exception {
        // given
        AvlTree avlTree = new AvlTree();
        avlTree.add(5);
        avlTree.add(2);
        avlTree.add(6);
        avlTree.add(1);
        avlTree.add(3);

        // when
        avlTree.add(4);

        /**
         *          5                3
         *        /   \            /   \
         *       2     6   ->     2     5
         *      / \              /    /   \
         *     1   3            1    4     6
         *          \
         *           4(insert)
         * */

        // then
        AvlTree.Node root = avlTree.root;
        nodeTest(root, null, 2, 3, 5);

        nodeTest(root.left, root, 1, 2, EMPTY);
        leafNodeTest(root.left.left, root.left, 1);

        nodeTest(root.right, root, 4, 5, 6);
        leafNodeTest(root.right.left, root.right, 4);
        leafNodeTest(root.right.right, root.right, 6);
    }

    @Test
    @DisplayName("right-left bias tree test")
    void rightLeftBiasTreeTest() throws Exception {
        // given
        AvlTree avlTree = new AvlTree();
        avlTree.add(2);
        avlTree.add(1);
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(6);

        // when
        avlTree.add(4);


        /**
         *     2                       3
         *   /   \                   /   \
         *  1      5       ->       2     5
         *       /   \            /     /  \
         *      3     6          1     4    6
         *       \
         *        4(insert)
         * */

        // then
        AvlTree.Node root = avlTree.root;
        nodeTest(root, null, 2, 3, 5);

        nodeTest(root.left, root, 1, 2, EMPTY);
        leafNodeTest(root.left.left, root.left, 1);

        nodeTest(root.right, root, 4, 5, 6);
        leafNodeTest(root.right.left, root.right, 4);
        leafNodeTest(root.right.right, root.right, 6);
    }

    @Test
    @DisplayName("after remove, maintain balance test")
    void maintainBalanceTestAfterRemove() throws Exception {
        // given
        AvlTree avlTree = new AvlTree();
        avlTree.add(3);
        avlTree.add(2);
        avlTree.add(5);
        avlTree.add(1);
        avlTree.add(4);
        avlTree.add(6);
        avlTree.add(7);

        // when
        avlTree.remove(1);

        /**
         *        3                     5
         *      /   \                 /   \
         *     2      5      ->      3     6
         *   /       / \           /  \     \
         *  1(R)    4   6         2   4      7
         *               \
         *                7
         * */

        // then
        AvlTree.Node root = avlTree.root;
        nodeTest(root, null, 3, 5, 6);

        nodeTest(root.left, root, 2, 3, 4);
        leafNodeTest(root.left.left, root.left, 2);
        leafNodeTest(root.left.right, root.left, 4);

        nodeTest(root.right, root, EMPTY, 6, 7);
        leafNodeTest(root.right.right, root.right, 7);
    }

    private void nodeTest(AvlTree.Node target,
                          AvlTree.Node parent,
                          int leftData,
                          int data,
                          int rightData){
        assertThat(target).isNotNull();
        assertThat(target.data).isEqualTo(data);
        assertThat(target.parent).isEqualTo(parent);

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

    private void leafNodeTest(AvlTree.Node target,
                              AvlTree.Node parent,
                              int data){
        assertThat(target).isNotNull();
        assertThat(target.data).isEqualTo(data);
        assertThat(target.parent).isEqualTo(parent);

        assertThat(target.left).isNull();
        assertThat(target.right).isNull();
    }
}