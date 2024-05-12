import org.junit.Test;

/**
 * ClassName: HashMapTest
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Chen Ziyun
 * @Version 1.0
 */
public class HashMapTest {
    @Test
    public void test0() {
        CzyHashMap map = new CzyHashMap();
        for (int i = 0; i < 100; i++) {
            map.put("刘华强" + i, "你这瓜保熟吗？" + i);
        }
        System.out.println(map.size());
        for (int i = 0; i < 100; i++) {
            System.out.println(map.get("刘华强" + i));
        }
    }

    @Test
    public void test1() {
        CzyHashMap map = new CzyHashMap();
        map.put("刘华强1","哥们，你这瓜保熟吗？");
        map.put("刘华强1","你这瓜熟我肯定要啊！");
        System.out.println(map.get("刘华强1"));
    }
}
