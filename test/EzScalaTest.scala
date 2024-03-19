//> using toolkit default

import com.wiwiwa.ezscala.*

import java.io.ByteArrayOutputStream

class EzScalaTest extends munit.FunSuite:
    test("LogicalTruthyOperator"):
        assertEquals(true && "1" || "2", "1")
        assertEquals(false && "1" || "2", "2")
        assertEquals(1 && "1" || "2", "1")
        assertEquals(0 && "1" || "2", "2")
        assertEquals("" && "1" || "0", "0")
        assertEquals("0" && "1" || "0", "1")
        assertEquals("false" && "1" || "0", "1")
        assertEquals(null && "1" || "0", "0")
    test("PipeOperator"):
        val list = List(9,10,11)
        assertEquals( list|>Integer.toHexString, List("9","a","b") )
        assertEquals( list|?{_<10}, List(9) )
        assertEquals( list|>|{e=>List(e,e*e)}, List(9,81,10,100,11,121) )
    test("IO"):
        val file =  "./.gitignore".file
        assert{ file.text.contains(".*") }
        assert{
            val buf = new ByteArrayOutputStream()
            file.withStream:
                _ >> buf
            buf.toByteArray.string.contains(".*")
        }
        assert( "https://www.gov.cn/".http.get().text.contains("政府") )
    test("VirtualThread"):
        val  ret = go:
            println(Thread.currentThread.getName)
            "returned string"
        go.await(-1)
        assert(ret.get().nonEmpty)
