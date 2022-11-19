import kotlin.random.Random

var isBuy = true
var singleOrdersNum = 1.0
var ordersNum = 0.0
var maxOrdersNum = 20

val historyOrders = mutableListOf(
//    Order(true, -2.3),
//    Order(false, -3.0),
//    Order(false, -5.3),
    Order(true, 1.5),
//    Order(true, 2.2),
//    Order(false, 3.8)
)
val currentOrders = mutableListOf<Order>()

data class Order(val isBuy: Boolean, val profit: Double)

/**
 *
 */
fun main() {
    while (true) {
        Thread.sleep(1500)
        strategy()
//        print( "  "+ random.nextDouble() * 10)

        val first = currentOrders.removeFirst()
        historyOrders.add(Order(first.isBuy, Random.nextDouble() * if (Random.nextBoolean()) 10 else -10)) // 历史盈亏
        println("平仓后，$ordersNum  是否盈利：${historyOrders.last().profit > 0}")
        println()
    }
}

fun strategy() {

    val currentNum = currentOrders.size
    if (currentNum == 1) return

    var lostCount = 0
    var isLost = false
    var historyIsBuy = isBuy


    with (historyOrders.reversed()) lost@{
        this.forEachIndexed { index, it ->
    //        if (index > 0 && it.isBuy != historyIsBuy) {
    //            return
    //        }
            if (index == 0) {
                historyIsBuy = it.isBuy
            }
            if (it.profit < 0) {
                isLost = true
                lostCount++
                if (lostCount >= 10) {
                    lostCount = 10
                }
            } else {
                return@lost
            }
        }
    }
//    if (currentNum == 0) {
    if (!isLost) {
        ordersNum = singleOrdersNum
        lostCount = 0
    } else {
        ordersNum = singleOrdersNum * Math.pow(2.0, lostCount.toDouble())

    }
    if (isLost) {
        isBuy = !historyIsBuy
    }
    currentOrders.add(Order(isBuy, Random.nextDouble() * 10)) // 这个 profit 没啥用
    println("开仓，${if (currentOrders.first().isBuy) "buy" else "sell"}")
//    } else {

//    }


}