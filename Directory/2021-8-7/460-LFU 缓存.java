

class LFUCache {

    HashMap<Integer, Integer> KeyToValue;  // KV 表
    HashMap<Integer, Integer> KeyToFreq;  // KF 
    HashMap<Integer, LinkedHashSet<Integer>> FreqToKey;  //FK 
    int minFreq; //
    int cap;
    public LFUCache(int capacity) {
        KeyToValue = new HashMap<>();
        KeyToFreq = new HashMap<>(); 
        FreqToKey = new HashMap<>();
        this.cap = capacity;
        this.minFreq = 0;

    }
    
    public void removeLastFreq() {
        // 也是 核心！！！
        LinkedHashSet<Integer> keyset = FreqToKey.get(this.minFreq);
        //最久的，就是最开始被插入的
        int delekey = keyset.iterator().next();
        //  注意是从当前的列表中删掉，也就先更新FK，再去删 KV、KF表的
        keyset.remove(delekey);
        if (keyset.isEmpty()) {
            // 如果这个列表删掉后变空了，删掉这个链表
            // 这个时候，minfreq被删掉了，可以不补上，因为会用到这个函数的地方是新建，然后溢出再删除，新建的话，minfreq就自动为1
            // 但是要注意先后顺序，一定是先移掉原来的 minfreq对应的，再令后面的minfreq = 1
            FreqToKey.remove(this.minFreq);
        }
        // 更新 KV KF
        KeyToValue.remove(delekey);
        KeyToFreq.remove(delekey);

    }

    public void increaseFreq(int key) {
        // 这个用的实在太频繁了，抽象出一个函数更方便，LFU核心！
        // 首先得到原来的 freq, KV就不用调整了
        int freq = KeyToFreq.get(key);
        // KF
        KeyToFreq.put(key, freq+1);
        // 原来的key对应的freq删除
        FreqToKey.get(freq).remove(key);
        // FK,看如果+1后的freq是否存在，存在加进去，不存在，新建 LinkedHashSet
        FreqToKey.putIfAbsent(freq + 1, new LinkedHashSet<Integer>());
        FreqToKey.get(freq+1).add(key);

        // freq少了一个数，看看有没有空
        if (FreqToKey.get(freq).isEmpty()) {

            FreqToKey.remove(freq);
            // 如果刚好是minFreq,需要更新一下
            if (freq == minFreq) {
                this.minFreq++;
            }
        }


    }

    public int get(int key) {
        if (!KeyToValue.containsKey(key)) {
            // not exist
            return -1;
        }

        increaseFreq(key);
        return KeyToValue.get(key);

    }
    
    public void put(int key, int value) {
        if (this.cap <= 0) return;
        if (KeyToValue.containsKey(key)) {
            KeyToValue.put(key, value);
            increaseFreq(key);
            return;
        }

        if (this.cap <= KeyToValue.size()) {
            removeLastFreq();
        }

        // not exist
        // 更新三个表
        KeyToValue.put(key, value);
        KeyToFreq.put(key, 1);
        // 可能有其他的 freq也为1 的key
        FreqToKey.putIfAbsent(1, new LinkedHashSet<>());
        FreqToKey.get(1).add(key);
        // 对于最小的，直接就是1，也不用特意去找
        this.minFreq = 1;


    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
