package tree;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BstWithParentTest {

    @Test
    @DisplayName("BSTTest")
    void BSTTest() throws Exception {
        BstWithParent bstWithParent = new BstWithParent();
        bstWithParent.add(10);
        bstWithParent.add(11);
        bstWithParent.add(5);
        bstWithParent.add(7);
        bstWithParent.add(2);
        bstWithParent.add(13);
        bstWithParent.add(23);
        bstWithParent.add(1);

        bstWithParent.printPreorder();
        bstWithParent.printInorder();
        bstWithParent.printPostorder();
    }

    @Test
    @DisplayName("remove when root is null")
    void removeWhenRootIsNull() throws Exception {
        // given
        BstWithParent bstWithParent = new BstWithParent();
        // when // then
        assertThat(bstWithParent.remove(1)).isFalse();
        assertThat(bstWithParent.getRoot()).isNull();
    }

    @Test
    @DisplayName("remove non-target")
    void removeNonTarget() throws Exception {
        // given
        BstWithParent bstWithParent = new BstWithParent();
        bstWithParent.add(2);
        bstWithParent.add(0);
        bstWithParent.add(3);
        bstWithParent.add(4);

        // when // then
        assertThat(bstWithParent.remove(1)).isFalse();
        assertThat(bstWithParent.getAllNodeData().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("remove target that has no child and is root")
    void removeTargetThatHasNoChildAndIsRoot() throws Exception {
        // given
        BstWithParent bstWithParent = new BstWithParent();
        bstWithParent.add(2);

        // when
        boolean b = bstWithParent.remove(2);

        // then
        assertThat(b).isTrue();
        assertThat(bstWithParent.getRoot()).isNull();
    }

    @Test
    @DisplayName("remove target that has no child and is not root")
    void removeTargetThatHasNoChildAndIsNotRoot() throws Exception {
        // given
        BstWithParent bstWithParent = new BstWithParent();
        bstWithParent.add(5);
        bstWithParent.add(10);

        // when
        boolean b = bstWithParent.remove(10);

        // then
        assertThat(b).isTrue();
        assertThat(bstWithParent.getRoot()).isNotNull();
        assertThat(bstWithParent.getRoot().countChild()).isZero();
    }
    
    @Test
    @DisplayName("remove target that has left child")
    void removeTargetThatHasLeftChild() throws Exception {
        // given
        BstWithParent bstWithParent = new BstWithParent();
        bstWithParent.add(5);
        bstWithParent.add(10);
        bstWithParent.add(3);
        bstWithParent.add(2);

        // when
        boolean b = bstWithParent.remove(3);

        // then
        assertThat(b).isTrue();
        List<Integer> list = bstWithParent.getAllNodeData();
        //assertThat(list.size()).isEqualTo(3);
        BstWithParent.Node root = bstWithParent.getRoot();
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
        BstWithParent bstWithParent = new BstWithParent();
        bstWithParent.add(5);
        bstWithParent.add(10);
        bstWithParent.add(3);
        bstWithParent.add(4);

        // when
        boolean b = bstWithParent.remove(3);

        // then
        assertThat(b).isTrue();
        List<Integer> list = bstWithParent.getAllNodeData();
        //assertThat(list.size()).isEqualTo(3);
        BstWithParent.Node root = bstWithParent.getRoot();
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
        BstWithParent bstWithParent = new BstWithParent();
        bstWithParent.add(5);
        bstWithParent.add(10);
        bstWithParent.add(3);
        bstWithParent.add(11);
        bstWithParent.add(9);
        bstWithParent.add(12);

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
        boolean b = bstWithParent.remove(10);

        // then
        assertThat(b).isTrue();
        BstWithParent.Node root = bstWithParent.getRoot();
        BstWithParent.Node replacedNode = root.right;
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
        BstWithParent bstWithParent = new BstWithParent();
        bstWithParent.add(5);
        bstWithParent.add(10);
        bstWithParent.add(3);
        bstWithParent.add(12);
        bstWithParent.add(9);
        bstWithParent.add(13);
        bstWithParent.add(11);

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
        boolean b = bstWithParent.remove(10);

        // then
        assertThat(b).isTrue();
        BstWithParent.Node root = bstWithParent.getRoot();
        BstWithParent.Node replacedNode = root.right;
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
        BstWithParent bstWithParent = new BstWithParent();
        bstWithParent.add(5);
        bstWithParent.add(10);
        bstWithParent.add(3);
        bstWithParent.add(13);
        bstWithParent.add(9);
        bstWithParent.add(14);
        bstWithParent.add(11);
        bstWithParent.add(12);

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
        boolean b = bstWithParent.remove(10);

        // then
        assertThat(b).isTrue();
        BstWithParent.Node root = bstWithParent.getRoot();
        BstWithParent.Node replacedNode = root.right;
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