//> using test.toolkit 0.4.0

import com.wiwiwa.ezscala.EzScala.*
import com.wiwiwa.ezscala.codec.{Base64, Json}

import java.io.ByteArrayOutputStream
import java.util.concurrent.atomic.AtomicInteger

class EzScalaTest extends munit.FunSuite:
    test("LogicalTruthyOperator"):
        true && "1" || "2" |== "1"
        false && "1" || "2" |== "2"
        1 && "1" || "2" |== "1"
        0 && "1" || "2" |== "2"
        "" && "1" || "0" |== "0"
        "0" && "1" || "0" |== "1"
        "false" && "1" || "0" |== "1"
        null && "1" || "0" |== "0"
    test("PipeOperator"):
        val list = List(9,10,11)
        list|>Integer.toHexString |== List("9","a","b")
        list|?{_<10} |== List(9)
        list|>|{e=>List(e,e*e)} |== List(9,81,10,100,11,121)
    test("IO"):
        val file =  "./.gitignore".file
        assert{ file.text.contains(".*") }
        assert{
            val buf = new ByteArrayOutputStream()
            file.withStream(_ >> buf)
            buf.toByteArray.string.contains(".*")
        }
        assert( "https://www.gov.cn/".http.get().text.contains("政府") )
    test("VirtualThread"):
        val counter = new AtomicInteger()
        val ret1 = (0 until 5).goEach(3): _=>
          counter.incrementAndGet()
          Thread.sleep(100)
          counter.decrementAndGet()
        val  ret2 = go:
          Thread.sleep(50)
          counter.get()
        assert{ ret2.get() == 3  }
        ret1.get()  //wait all thread to stop
    test("Codec"):
      val json = """{"name":"张三","age":20}"""
      val base64 = Base64.urlEncode(json.bytes)
      Base64.decode(base64).string |== json
      val user = Json.decode[User](json)
      user.age |== 20
      Json.encode(user) |== json

case class User(name:String, age:Int)