//자료구조 수업자료를 활용하여 작성
public class MyAVLTree<E extends Comparable<E>, K extends Comparable<K>>{
    private TreeNode<E, K> root;
    static final TreeNode NIL = new TreeNode(null, null, null, 0, -1);
    public MyAVLTree(){
        root = NIL;
    }

    public void insert(E x, K key) {
        root = insertItem(root, x, key);
    }
    public TreeNode<E, K> insertItem(TreeNode<E, K> root, E x, K key){
        int type = NO_NEED;
        if(root==NIL){
            root = new TreeNode<>(x, key);
        }
        else if(key.compareTo(root.key)<0){
            root.left = insertItem(root.left, x, key);
            root.height = 1 + Math.max(root.left.height, root.right.height);
            type = needBalance(root);
            if(type!=NO_NEED) {
                root = balanceAVL(root, type);
            }
        }
        else if(key.compareTo(root.key)==0){
            root.item.append(x);
        }
        else {
            root.right = insertItem(root.right, x, key);
            root.height = 1 + Math.max(root.left.height, root.right.height);
            type = needBalance(root);
            if (type != NO_NEED)
                root = balanceAVL(root, type);
        }
        return root;
    }



    public TreeNode<E, K> search(K x) {
        return searchItem(root, x);
    }
    public TreeNode<E, K> searchItem(TreeNode<E, K> root, K key){
        if(root==NIL) {return null;}
        else if(key.compareTo(root.key)==0) return root;
        else if(key.compareTo(root.key)<0) return searchItem(root.left, key);
        else  return searchItem(root.right, key);
    }

    public boolean isEmpty(){
        return root==NIL;
    }

    //삭제 연산의 경우 사용하지 않으므로 구현하지 않음
//    public void delete(E x, K key) {
//        root = deleteItem(root, x, key);
//    }
//    public TreeNode<E, K> deleteItem(TreeNode<E, K> root, E x, K key){
//        if(root==NIL) return null;
//        else if(key.compareTo(root.key)==0){
//            root.item.removeItem(x);
//        }
//        else if(key.compareTo(root.key)<0){
//            root.left = deleteItem(root.left, x, key);
//            root.height = 1+Math.max(root.right.height, root.left.height);
//            type = needBalance(root);
//            if(type!=NO_NEED)
//                root = balanceAVL(root, type);
//        }
//        else{
//            root.right = deleteItem(root.right, x, key);
//            root.height = 1+Math.max(root.right.height, root.left.height);
//            type = needBalance(root);
//            if(type!=NO_NEED)
//                root = balanceAVL(root, type);
//        }
//        return root;
//    }

    private final int LL = 1, LR =2, RR =3, RL=4, NO_NEED=0, ILLEGAL = -1;
    private int needBalance(TreeNode<E, K> t){
        int type = ILLEGAL;
        if(t.left.height+2 <= t.right.height){
            type = RR;
            if(t.right.left.height>t.right.right.height)
                type = RL;
        }
        else if(t.left.height >= t.right.height+2){
            type = LL;
            if(t.left.left.height<t.left.right.height)
                type = LR;
        }else
            type = NO_NEED;
        return type;
    }
    private TreeNode<E, K> balanceAVL(TreeNode<E, K> t, int type){
        TreeNode<E, K> returnNode = NIL;
        switch (type){
            case RR:
                returnNode = leftRotate(t);
                break;
            case RL:
                t.right = rightRotate(t.right);
                returnNode = leftRotate(t);
                break;
            case LL:
                returnNode = rightRotate(t);
                break;
            case LR:
                t.left = leftRotate(t.left);
                returnNode = rightRotate(t);
                break;
            default:
                break;
        }
        return returnNode;
    }

    private TreeNode<E, K> leftRotate(TreeNode<E, K> t){
        TreeNode<E, K> rightChild = t.right;
        if(rightChild!=NIL){
            TreeNode<E, K> leftRchild = rightChild.left;
            rightChild.left = t;
            t.right = leftRchild;
            t.height = 1+Math.max(t.left.height, t.right.height);
            rightChild.height=1+Math.max(rightChild.left.height, rightChild.right.height);
        }
        return rightChild;
    }
    private TreeNode<E, K> rightRotate(TreeNode<E, K> t){
        TreeNode<E, K> leftChild = t.left;
        if(leftChild!=NIL){
            TreeNode<E, K> rightLchild = leftChild.right;
            leftChild.right = t;
            t.left = rightLchild;
            t.height = 1+Math.max(t.left.height, t.right.height);
            leftChild.height=1+Math.max(leftChild.left.height, leftChild.right.height);
        }
        return leftChild;
    }

    public void traverse(){
        if(root!=NIL) {
            System.out.print(root.key);
            traverseItem(root.left);
            traverseItem(root.right);
        }
        System.out.println();
    }
    public void traverseItem(TreeNode<E, K> t){
        if(t!=NIL) {
            System.out.print(" ");
            System.out.print(t.key);
            traverseItem(t.left);
            traverseItem(t.right);
        }
    }

}
