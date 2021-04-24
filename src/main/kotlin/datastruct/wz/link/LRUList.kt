package datastruct.wz.link

/**
 * desc:    单链表实现LRU.
 *          最近访问的越靠近链表头部，越早访问的越靠近尾部。
 *
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/4/24 17:45
 */
class LRUList<T> {

    inner class Node<T> constructor(val data: T?, var next: Node<T>?)

    private var mHead: Node<T>? = null
    private val mCapacity = 5  //缓存容量
    private var mSize = 0

    private fun insert(e: T): Node<T> {
        if (mSize == mCapacity) {//空间不足，移除尾结点
            removeLast()
            mSize--
        }

        val newNode = Node(e, mHead)
        mHead = newNode// 直接插入到头部
        mSize++

        return newNode
    }

    //由于单链表的删除、插入，需要前驱结点，所以返回值，直接就是前驱结点
    private fun search(e: T): Node<T>? {
        var p = mHead
        while (p != null && p.data != e) {
            p = p.next
        }
        return p
    }

    private fun searchPrev(node: Node<T>): Node<T>? {
        var p = mHead
        while (p?.next != node) {
            p = p?.next
        }
        return p
    }

    private fun removeLast() {
        if (mHead == null) return
        var p = mHead
        var q = p?.next
        while (q?.next != null) {
            p = q
            q = q.next
        }
        //q?.next == null, 循环结束，表示是尾结点
        p?.next = q?.next
    }

    /*
     * 查找是否有缓存？
     * 否：直接insert到头部。 若缓存已满，先删除尾结点，再insert到头部。
     * 是：找到其前驱结点，删除它，并移到首结点
     */
    fun access(e: T): Node<T> {
        //先遍历查找
        val findNode = search(e)
        val result: Node<T>
        if (findNode == null) {//没找到，就insert
            result = insert(e)
        } else {//找到了
            val prev = searchPrev(findNode)
            prev?.next = findNode.next //先移出了 findNode
            findNode.next = mHead  //findNode置前
            mHead = findNode
            result = findNode
        }
        return result
    }

    fun display() {
        var p = mHead
        while (p != null) {
            p.data?.let { print(it) }
            p = p.next
        }
    }
}

fun main() {
    val list = LRUList<Int>()
    list.access(1)
    list.access(7)
    list.access(8)
    list.access(7)
    list.access(4)
    list.access(1)
    list.access(2)
    list.access(3)
    list.display()
    /*
      添加
        1 7 8 -> 8 7 1
        7 -> 7 8 1
        4 -> 4 7 8 1
        1 -> 1 4 7 8
        2 -> 21478
        3 -> 32147
     */

}