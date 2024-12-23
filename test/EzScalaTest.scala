//> using test.toolkit 0.4.0

import com.wiwiwa.ezscala.*
import com.wiwiwa.ezscala.codec.{Base64, Json}

import java.io.FileInputStream
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
          val s = new FileInputStream(file).use: f=>
            new String(f.readAllBytes())
          s == file.text
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
      //base64 test
      val json = """{"name":"张三","age":20,"id":{"value":5}}"""
      val base64 = json.bytes.base64
      Base64.decode(base64).string |== json
      //json test
      case class User[T](name:String, age:Int, id:T)
      case class ID(value:Int)
      val user = Json.decode[User[ID]](json)
      user.age |== 20
      Json.encode(user) |== json
    test("os"):
      assert( os.sh("echo $HOME").startsWith("/") )
      assert( os.run("ls /").contains("etc\n") )
