//> using toolkit latest

import com.wiwiwa.ezscala.*

class EzScalaTest extends munit.FunSuite:
    test("LogicalTruthyOperator"){
        assertEquals(true && "1" || "2", "1")
        assertEquals(false && "1" || "2", "2")
        assertEquals(1 && "1" || "2", "1")
        assertEquals(0 && "1" || "2", "2")
        assertEquals("" && "1" || "0", "0")
        assertEquals("0" && "1" || "0", "1")
        assertEquals("false" && "1" || "0", "1")
        assertEquals(null && "1" || "0", "0")
    }
    test("PipeOperator"){
        val list = List(9,10,11)
        assertEquals( list|>Integer.toHexString, List("9","a","b") )
        assertEquals( list|?{_<10}, List(9) )
        assertEquals( list|>|{e=>List(e,e*e)}, List(9,81,10,100,11,121) )
    }
    test("Url"){
        assert{
            "./.gitignore".file
              |! { "Checking content of file " + _ |> println }
              |> { _.text.contains(".*") }
        }
        assert( "https://www.gov.cn/".http.get().text.contains("政府") )
    }
