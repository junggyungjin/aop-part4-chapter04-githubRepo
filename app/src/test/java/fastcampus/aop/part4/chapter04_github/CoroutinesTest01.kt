package fastcampus.aop.part4.chapter04_github

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.system.measureTimeMillis

class CoroutinesTest01 {

    @Test
    fun test01() = runBlocking {
        val time = measureTimeMillis {
            val name = getFirstName()
            val LastName = getLastName()
            print("Hello, $name $LastName")
        }
        print("measure time : $time")
    }

    @Test
    fun test02() = runBlocking {
        val time = measureTimeMillis {
            val name = async { getFirstName() }
            val LastName = async { getLastName() }
            print("Hello, ${name.await()} ${LastName.await()}")
        }
        print("measure time : $time")
    }

    suspend fun getFirstName(): String {
        delay(1000)
        return "정"
    }

    suspend fun getLastName(): String {
        delay(1000)
        return "경진"
    }
}