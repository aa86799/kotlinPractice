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

    var mHead: Node<T>? = null
    private val mCapacity = 7  //缓存容量
    private var mSize = 0

    private fun insertFirst(e: T) {
        if (mSize == mCapacity) {//空间不足，移除尾结点
            removeLast()
        }

        val newNode = Node(e, mHead)
        mHead = newNode// 直接插入到头部
        mSize++

    }

    //查找 date == e 结点
    private fun search(e: T): Node<T>? {
        var p = mHead
        while (p != null && p.data != e) {
            p = p.next
        }
        return p
    }

    //由于单链表的删除、插入，需要前驱结点。
    private fun searchPrev(node: Node<T>): Node<T> {
        var p = mHead
        while (p?.next != node) {
            p = p?.next
        }
        return p
    }

    fun removeLast() {
        if (mHead?.next == null) {
            mHead = null
            return
        }
        //此时链表中至少有两个结点
        var p = mHead
        var q = p?.next
        while (q?.next != null) {
            p = q
            q = p.next
        }
        //q?.next == null, 循环结束，表示是尾结点
        q = null
        p?.next = null
        mSize--
    }

    fun removeFirst() {
        if (mHead == null) return
        mHead = mHead?.next
        mSize--
    }

    //递归删除尾结点
    fun removeLostForRecursive() {
        if (mHead?.next == null) {
            mHead = null
        } else {
            //此时链表中至少有两个结点
            realRemoveLostForRecursive(mHead!!, mHead?.next!!)
        }
        mSize--
    }

    private fun realRemoveLostForRecursive(p: Node<T>, q: Node<T>?) {
        if (q?.next == null) p.next = null
        else realRemoveLostForRecursive(q, q.next)
    }

    /*
     * 查找是否有缓存？
     * 否：若缓存已满，先删除尾结点，再insert到头部； else 直接insert到头部。
     * 是：找到其前驱结点，删除它，并移到首结点
     */
    fun access(e: T) {
        //先遍历查找
        val findNode = search(e)
        if (findNode == null) {//没找到，就insert
            insertFirst(e)
        } else {//找到了
            val prev = searchPrev(findNode)
            prev.next = findNode.next //先移出了 findNode
            findNode.next = mHead  //findNode置前
            mHead = findNode
        }
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
    list.access(5)
    list.access(7)
    list.display()
    println()
    list.removeLostForRecursive()
//    list.removeFirst()
//    list.removeLast()
    list.display()
    println()
    list.access(8)
    list.access(7)
    list.access(4)
    list.access(1)
    list.access(2)
    list.access(3)
    list.display()

}