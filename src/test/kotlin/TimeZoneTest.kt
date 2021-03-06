import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.ZoneId
import java.util.*
import kotlin.math.abs

class TimeZoneTest {

    @Test
    fun testMySuspendingFunction() = runBlocking<Unit> {
        assert((1..20).contains(20))
        assert((1 to 20).toList().size == 2)

        println(Calendar.getInstance().timeZone.rawOffset / 3600_000)
//        val rawOffset = TimeZone.getDefault().rawOffset
        val rawOffset = TimeZone.getTimeZone(ZoneId.of("America/New_York")).rawOffset
        val symbol = if (rawOffset > 0) "+" else "-"
        val offsetHour = abs(rawOffset) / 3600_000
        val offsetMinute = abs(rawOffset) % 3600_000 / 60_000
        val s =  "$symbol${String.format("%1$02d", offsetHour)}:${String.format("%1$02d", offsetMinute)}"
        println(s)

        var a = 1
        var b = 2
        b = a.also { a = b }
        assert(a == 2)
        assert(b == 1)

        b = a.apply { a = b }
        assert(a == 1)
        assert(b == 2)

        fun getVersion(): String {
            var version = "admin_3892"
            val index = version.indexOf("_")
            if (index != -1) {
                version = version.substring(0, index)
            }
            return version
        }
        assert(getVersion().length  == 5)
    }
}