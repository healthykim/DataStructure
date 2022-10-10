//자료구조 수업자료를 활용하여 작성
public class TreeNode <T extends Comparable<T>, E extends Comparable<E>>{
    public MyLinkedList<T> item = new MyLinkedList<>();
    public TreeNode<T, E> left;
    public TreeNode<T, E> right;
    public int height;
    public E key;
    public TreeNode(T item, E key){
        this.item.append(item);
        this.left = this.right = MyAVLTree.NIL;
        this.height = 1;
        this.key = key;
    }
    public TreeNode(T item, TreeNode<T, E> leftChild, TreeNode<T, E> rightChild, int height, E key){
        this.item.append(item);
        this.left = leftChild; this.right = rightChild;
        this.height = height;
        this.key = key;
    }

}
