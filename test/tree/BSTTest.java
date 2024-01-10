package tree;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BSTTest {

    @Test
    @DisplayName("BSTTest")
    void BSTTest() throws Exception {
        BST bst = new BST();
        bst.add(10);
        bst.add(11);
        bst.add(5);
        bst.add(7);
        bst.add(2);
        bst.add(13);
        bst.add(23);
        bst.add(1);

        bst.printPreorder();
        bst.printInorder();
        bst.printPostorder();
    }

    @Test
    @DisplayName("remove when root is null")
    void removeWhenRootIsNull() throws Exception {
        // given
        BST bst = new BST();
        // when // then
        assertThat(bst.remove(1)).isFalse();
        assertThat(bst.getRoot()).isNull();
    }

    @Test
    @DisplayName("remove non-target")
    void removeNonTarget() throws Exception {
        // given
        BST bst = new BST();
        bst.add(2);
        bst.add(0);
        bst.add(3);
        bst.add(4);

        // when // then
        assertThat(bst.remove(1)).isFalse();
        assertThat(bst.getAllNodeData().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("remove target that has no child and is root")
    void removeTargetThatHasNoChildAndIsRoot() throws Exception {
        // given
        BST bst = new BST();
        bst.add(2);

        // when
        boolean b = bst.remove(2);

        // then
        assertThat(b).isTrue();
        assertThat(bst.getRoot()).isNull();
    }

    @Test
    @DisplayName("remove target that has no child and is not root")
    void removeTargetThatHasNoChildAndIsNotRoot() throws Exception {
        // given
        BST bst = new BST();
        bst.add(5);
        bst.add(10);

        // when
        boolean b = bst.remove(10);

        // then
        assertThat(b).isTrue();
        assertThat(bst.getRoot()).isNotNull();
        assertThat(bst.getRoot().countChild()).isZero();
    }
    
    @Test
    @DisplayName("remove target that has left child")
    void removeTargetThatHasLeftChild() throws Exception {
        // given
        BST bst = new BST();
        bst.add(5);
        bst.add(10);
        bst.add(3);
        bst.add(2);

        // when
        boolean b = bst.remove(3);

        // then
        assertThat(b).isTrue();
        List<Integer> list = bst.getAllNodeData();
        //assertThat(list.size()).isEqualTo(3);
        BST.Node root = bst.getRoot();
        assertThat(root.left).isNotNull();
        assertThat(root.left.data).isEqualTo(2);
        assertThat(root.left.left).isNull();
        assertThat(root.left.right).isNull();
        assertThat(root.left.parent).isEqualTo(root);
    }

    @Test
    @DisplayName("remove target that has right child")
    void removeTargetThatHasRightChild() throws Exception {
        // given
        BST bst = new BST();
        bst.add(5);
        bst.add(10);
        bst.add(3);
        bst.add(4);

        // when
        boolean b = bst.remove(3);

        // then
        assertThat(b).isTrue();
        List<Integer> list = bst.getAllNodeData();
        //assertThat(list.size()).isEqualTo(3);
        BST.Node root = bst.getRoot();
        assertThat(root.left).isNotNull();
        assertThat(root.left.data).isEqualTo(4);
        assertThat(root.left.left).isNull();
        assertThat(root.left.right).isNull();
        assertThat(root.left.parent).isEqualTo(root);
    }
    
    @Test
    @DisplayName("remove target that has two child and successor is target's right node")
    void removeTargetThatHasTwoChildAndSuccessorIsTargetRightNode() throws Exception {
        // given
        BST bst = new BST();
        bst.add(5);
        bst.add(10);
        bst.add(3);
        bst.add(11);
        bst.add(9);
        bst.add(12);

        /** before
         *              5(root)
         *      3               10(target)
         *                  9       11(successor)
         *                              12
         * */

        /** after
         *              5(root)
         *      3               11(replace)
         *                  9       12
         * */
        
        // when
        boolean b = bst.remove(10);

        // then
        assertThat(b).isTrue();
        BST.Node root = bst.getRoot();
        BST.Node replacedNode = root.right;
        assertThat(replacedNode).isNotNull();
        assertThat(replacedNode.parent).isEqualTo(root);
        assertThat(replacedNode.left).isNotNull();
        assertThat(replacedNode.left.data).isEqualTo(9);
        assertThat(replacedNode.right).isNotNull();
        assertThat(replacedNode.right.data).isEqualTo(12);
    }
    
    @Test
    @DisplayName("remove target that has two child and successor has no child")
    void removeTargetThatHasTwoChildAndSuccessorHasNoChild() throws Exception {
        // given
        BST bst = new BST();
        bst.add(5);
        bst.add(10);
        bst.add(3);
        bst.add(12);
        bst.add(9);
        bst.add(13);
        bst.add(11);

        /** befores
         *              5(root)
         *      3               10(target)
         *                  9         12
         *              (successor)11    13
         *
         * */

        /** after
         *              5(root)
         *      3               11(replace)
         *                  9       12
         *                             13
         * */

        // when
        boolean b = bst.remove(10);

        // then
        assertThat(b).isTrue();
        BST.Node root = bst.getRoot();
        BST.Node replacedNode = root.right;
        assertThat(replacedNode).isNotNull();
        assertThat(replacedNode.parent).isEqualTo(root);
        assertThat(replacedNode.left).isNotNull();
        assertThat(replacedNode.left.data).isEqualTo(9);
        assertThat(replacedNode.right).isNotNull();
        assertThat(replacedNode.right.data).isEqualTo(12);
        assertThat(replacedNode.right.left).isNull();
    }

    @Test
    @DisplayName("remove target that has two child and successor has right child")
    void removeTargetThatHasTwoChildAndSuccessorHasRightChild() throws Exception {
        // given
        BST bst = new BST();
        bst.add(5);
        bst.add(10);
        bst.add(3);
        bst.add(13);
        bst.add(9);
        bst.add(14);
        bst.add(11);
        bst.add(12);

        /** befores
         *              5(root)
         *      3               10(target)
         *                  9            13
         *                (successor)11      14
         *                             12
         * */

        /** after
         *              5(root)
         *      3               11(replace)
         *                  9          13
         *                          12    14
         * */

        // when
        boolean b = bst.remove(10);

        // then
        assertThat(b).isTrue();
        BST.Node root = bst.getRoot();
        BST.Node replacedNode = root.right;
        assertThat(replacedNode).isNotNull();
        assertThat(replacedNode.parent).isEqualTo(root);
        assertThat(replacedNode.left).isNotNull();
        assertThat(replacedNode.left.data).isEqualTo(9);
        assertThat(replacedNode.right).isNotNull();
        assertThat(replacedNode.right.data).isEqualTo(13);
        assertThat(replacedNode.right.left).isNotNull();
        assertThat(replacedNode.right.left.data).isEqualTo(12);
        assertThat(replacedNode.right.left.parent).isEqualTo(replacedNode.right);
    }
}