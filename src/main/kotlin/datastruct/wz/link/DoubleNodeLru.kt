package datastruct.wz.link

/**
 * desc:    双向链表实现LRU.
 *          最近访问的越靠近链表头部，越早访问的越靠近尾部。
 *
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2021/5/16 20:45
 */
class DoubleNodeLru<T> {

    inner class Node<T> constructor(val data: T?, var prev: Node<T>?, var next: Node<T>?)

    var mHead: Node<T>? = null
    private val mCapacity = 7  //缓存容量
    private var mSize = 0

    private fun insertFirst(e: T) {
        if (mSize == mCapacity) {//空间不足，移除尾结点
            removeLast()
        }

        val newNode = Node(e, null, mHead)
        mHead?.prev = newNode
        mHead = newNode// 直接插入到头部
        mSize++
    }

    private fun search(e: T): Node<T>? {
        var p = mHead
        while (p != null && p.data != e) {
            p = p.next
        }
        return p
    }

    fun removeLast() {
        if (mHead?.next == null) {
            mHead = null
            return
        }
        var p = mHead
        while (p?.next != null) {
            p = p.next
        }
        p?.prev?.next = null
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
            return
        }
        //此时链表中至少有两个结点
        realRemoveLostForRecursive(mHead?.next!!)
        mSize--
    }

    private fun realRemoveLostForRecursive(p: Node<T>?) {
        println("xx: ${p?.data}")
        if (p?.next == null) {
            println("yy: ${p?.data}   ${p?.prev?.data}")
            p?.prev?.next = null
            p?.prev = null
        }
        else realRemoveLostForRecursive(p.next)
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
            if (findNode.prev != null) {
                findNode.prev?.next = findNode.next
                findNode.next?.prev = findNode.prev  //没有这句，可以遍历；但删除时就报错了
                findNode.next = mHead
                findNode.prev = null
                mHead?.prev = findNode
                mHead = findNode
            }
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
    val list = DoubleNodeLru<Int>()
    list.access(5)
    list.access(7)
//    list.display()
//    println()
//    list.removeFirst()
//    list.removeLast()
//    list.display()
    println()
    list.access(8)
    list.access(7)
    list.display()
    println()
    list.removeLostForRecursive()
    list.display()
    println()
//    list.removeLostForRecursive()

    list.access(4)
    list.access(1)
    list.access(2)
    list.access(3)
    list.display()

}