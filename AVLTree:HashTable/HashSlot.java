public class HashSlot <E extends Comparable<E>, K extends Comparable<K>>{
    public MyAVLTree<E, K> tree;
    public HashSlot(){
        tree = new MyAVLTree<>();
    }
}
