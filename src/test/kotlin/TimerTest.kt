import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.timer

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/2/11 11:39
 */

fun main() {
    /*
暑假到了老师给schedule和scheduleAtFixedRate两个同学布置作业。
老师要求学生暑假每天写2页，30天后完成作业。
这两个学生每天按时完成作业，直到第10天，出了意外，两个学生出去旅游花了5天时间，这5天时间里两个人都没有做作业。任务被拖延了。
这时候两个学生采取的策略就不同了：
schedule重新安排了任务时间，旅游回来的第一天做第11天的任务，第二天做第12天的任务，最后完成任务花了35天。
scheduleAtFixedRate是个守时的学生，她总想按时完成老师的任务，于是在旅游回来的第一天把之前5天欠下的任务以及第16天当天的任务全部完成了，
之后还是按照老师的原安排完成作业，最后完成任务花了30天。
     */
    val sf = SimpleDateFormat("yyyy MM dd hh:mm:ss")
    var count = 0;
//    timer(initialDelay = 100L, period = 500L) {
    fixedRateTimer(initialDelay = 100L, period = 500L) {
        count++
        if (count == 10) {
            Thread.sleep(3000)
        }
        println("No.$count ${sf.format(System.currentTimeMillis())} ${sf.format(scheduledExecutionTime())} ${Thread.currentThread().name}")
        if (count == 30) {
            cancel()
        }
    }
}
