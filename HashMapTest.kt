import java.lang.reflect.Field

/**
 * Created by zlw on 2020/6/15.
 */
class HashMapTest {

    @Throws(NoSuchFieldException::class, IllegalAccessException::class)
    fun test1() {
        val lastTime = System.currentTimeMillis()
        val map: MutableMap<Int, Int> = HashMap()
        val field: Field = map.javaClass.getDeclaredField("table")
        field.isAccessible = true
        var last = 0
        var now = 0
        for (index in 0..1024) {
            map[index] = index
            now = (field.get(map) as Array<*>).size
            if (last == 0) {
                last = now
                continue
            }
            if (now != last) {
                println(
                    String.format(
                        "HashMap扩容了从 %d 到 %d, 扩容位置: %d",
                        last,
                        now,
                        index
                    )
                )
            }
            last = now
        }
        println("总耗时:${System.currentTimeMillis()-lastTime}")
    }
}

fun main(args: Array<String>) {
    val mapTest = HashMapTest()
    mapTest.test1()
}
