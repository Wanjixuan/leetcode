# 题目：[208. 实现 Trie (前缀树)](https://leetcode-cn.com/problems/implement-trie-prefix-tree/)

概念：

Trie树，即字典树，又称单词查找树或键树，是一种树形结构，是一种哈希树的变种。典型应用是用于统计和排序大量的字符串（但不仅限于字符串），所以经常被搜索引擎系统用于文本词频统计。**它的优点是：最大限度地减少无谓的字符串比较，查询效率比哈希表高。**

> Trie的核心思想是空间换时间。利用字符串的公共前缀来降低查询时间的开销以达到提高效率的目的。
    
它有3个基本性质：

- 根节点不包含字符，除根节点外每一个节点都只包含一个字符。
- 从根节点到某一节点，路径上经过的字符连接起来，为该节点对应的字符串。
- 每个节点的所有子节点包含的字符都不相同。


## 构建前缀树

前缀树可以看作是一个多叉树！结点的每个子节点都是一个字符，并且每个结点最多26个字符（如果是其他存储就不一定了）
类似多叉树那样构建, 子节点用一个数组表示就可以了，但是考虑到查询有两种：前缀查询和字符串查询。<br/>
并且像 `app` 和 `application` 都插进去，我也能知道其中还有 `app`这么一个字符。也就需要对

```java

public class TrieNode {
  private int[] next;
  // 需要一个标志位来判断是不是字符串结尾
  private boolean isEnd;
  public TrieNode() {
    this.next = new int[26];
    this.isEnd = false;
  }
}

```

## 插入

单独讨论插入的话，就是一个迭代新建前缀树的过程，但是插入到最后，需要令最后那个结点的字符串状态为true。
意味着到这里就有一个字符串了！

```java
    public void insert(String word) {
        TrieNode cur = root;
        int n = word.length();

        for (int i = 0; i < n; i++) {
            int ch = word.charAt(i) - 'a';
            if (cur.sonList[ch] == null) {
                cur.sonList[ch] = new TrieNode();
            }
            cur = cur.sonList[ch];
        }
        cur.isEnd = true;
    }
```

## 字符串查询

按照前缀树遍历的过程中，如果中途出现为空的结点（意味着这条路暂时还没存过字符串），那么就是没有这个字符串
还有一种情况，类似我存了 `application` 但是没有存 `app`，那么我要判断到最后一个 `p` 的时候，这里是不是一个字符串！利用数据结构里的结尾标志位！

```java
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode cur = root;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            int ch = word.charAt(i) - 'a';
            if (cur.sonList[ch] == null) {
                return false;
            }
            cur = cur.sonList[ch];
        }
        return cur.isEnd;  
    }
```


## 前缀查询

有一个字符串 str，如果我能按照他的顺序遍历前缀树，能把这个字符串给遍历到最后，那么就说明这个前缀是存在的
```java

    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        int n = prefix.length();

        for (int i = 0; i < n; i++) {
            int ch = prefix.charAt(i) - 'a';
            if (cur.sonList[ch] == null) {
                return false;
            }
            cur = cur.sonList[ch];
        }
        return true;

    }

```




## 实现
```java


class Trie {

    private class TrieNode{
        private TrieNode[] sonList;
        private boolean isEnd;

        public TrieNode() {
            this.isEnd = false;
            this.sonList = new TrieNode[26];
        }
    }
    private TrieNode root;
    /** Initialize your data structure here. */
    public Trie() {

        root = new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode cur = root;
        int n = word.length();

        for (int i = 0; i < n; i++) {
            int ch = word.charAt(i) - 'a';
            if (cur.sonList[ch] == null) {
                cur.sonList[ch] = new TrieNode();
            }
            cur = cur.sonList[ch];
        }
        cur.isEnd = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode cur = root;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            int ch = word.charAt(i) - 'a';
            if (cur.sonList[ch] == null) {
                return false;
            }
            cur = cur.sonList[ch];
        }
        return cur.isEnd;  
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode cur = root;
        int n = prefix.length();

        for (int i = 0; i < n; i++) {
            int ch = prefix.charAt(i) - 'a';
            if (cur.sonList[ch] == null) {
                return false;
            }
            cur = cur.sonList[ch];
        }
        return true;

    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
 ```
