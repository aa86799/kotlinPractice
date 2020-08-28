import kotlinx.coroutines.runBlocking
import org.junit.Test

class Test1 {

    @Test
    fun testMySuspendingFunction() = runBlocking<Unit> {
        assert((1..20).contains(20))
        assert((1 to 20).toList().size == 2)
    }
}