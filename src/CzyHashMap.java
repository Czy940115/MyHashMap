/**
 * ClassName: CzyHashMap
 * Package: PACKAGE_NAME
 * Description:
 *      散列函数：hashCode()+除留余数法
 *      哈希冲突解决：链地址法
 *      扩容：节点重新hash获取位置
 * @Author Chen Ziyun
 * @Version 1.0
 */
public class CzyHashMap<K, V>{
    /**
     * 节点类
     * @param <K>
     * @param <V>
     */
    // Node节点
    class Node<K, V>{
        // 键值对
        K key;
        V value;

        // 后继节点
        Node<K, V> next;

        // 构造函数
        Node(K k, V v){
            key = k;
            value = v;
        }

        // 下一个键值对
        Node(K key, V value, Node<K, V> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    // 初始化参数
    final int DEFAULT_CAPACITY = 16;// 默认容量
    final float LOAD_FACTOR = 0.75f;// 负载因子
    int size = 0;// HashMap大小
    Node<K, V>[] buckets;// 桶数组

    /**
     * 无参构造，默认数组大小
     */
    CzyHashMap(){
        buckets = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    /**
     * 有参构造，传入数组带下
     */
    CzyHashMap(int capacity){
        buckets = new Node[capacity];
        size = 0;
    }

    /**
     * 哈希函数，获取地址
     */
    private int getIndex(K key, int length){
        // 获取key的hash值
        return key == null ? null : Math.abs(key.hashCode()%length);
    }

    public void put(K key, V value){
        // 在放入元素前，先判断是否需要扩容
        if (size > LOAD_FACTOR * buckets.length) resize();
        putVal(key, value, buckets);
    }

    /**
     * 将元素放置数组中
     */
    public void putVal(K key, V value, Node<K, V>[] table){
        // 1.构造节点
        Node<K, V> node = new Node<>(key, value);

        // 2.获取节点存放位置
        int index = getIndex(key, table.length);

        // 3.判断该节点是否为空
        if (table[index] == null){
            // 3.1 若为空，直接存储
            table[index] = node;
            size++;
            return;
        }
        // 3.2 不为空，拉链法存储
        Node<K, V> list = table[index];
        while (list != null){
            // 3.3 判断是否有相等的元素，有的话，覆盖
            if (list.key.hashCode() == key.hashCode() && (list.key == key || key.equals(list.key))){
                list.value = value;
                return;
            }
            list = list.next;
        }
        // 3.4 没有相等的元素，直接头插法存储
        node.next = table[index];
        table[index] = node;
        size++;
    }

    /**
     * 对数组进行扩容处理
     */
    public void resize(){
        int newCapacity = buckets.length << 2;
        Node<K, V>[] newBuckets = new Node[newCapacity];
        // 将原数组转移到现在数组上
        rehash(newBuckets);
        // 3.修改指针的指向
        buckets = newBuckets;
    }

    public void rehash(Node<K, V>[] table){
        // 1.遍历旧数组中的元素
        for (int i = 0; i < buckets.length; ++i){
            if (buckets[i] == null) {// 2.为空，不需要移动
                continue;
            }

            // 3.不为空，遍历链表
            Node<K, V> list = buckets[i];
            while (list != null){
                // 4.重新计算hash值
                int index = getIndex(list.key, table.length);
                // 5.移动元素
                putVal(list.key, list.value, table);
                list = list.next;
            }
        }
    }

    /**
     * 根据key值获取元素的value值
     */
    public V get(K key){
        // 1.根据key获取index值
        int index = getIndex(key, buckets.length);
        // 2.查看当前index中是否有值
        if (buckets[index] == null){
            // 2.1 没有值，直接返回null
            return null;
        }
        // 2.2 有值，遍历链表
        Node<K, V> list = buckets[index];
        while (list != null){
            // 2.3 判断节点是否相等，相等，返回value值
            if (list.key.hashCode() == key.hashCode() && (list.key == key || key.equals(list.key))){
                return list.value;
            }
            list = list.next;
        }

        // 2.4 最后返回null
        return null;
    }

    /**
     * 获取HashMap中的元素大小
     * @return
     */
    public int size(){
        return size;
    }
}
