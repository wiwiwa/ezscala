import com.wiwiwa.ezscala.*
import utest.*

object EzScalaTest extends TestSuite:
    override def tests = Tests{
        testPipeOperator()
        testUrl()
    }
    def testPipeOperator() =
        val list = List(9,10,11)
        (list | Integer.toHexString) ==> List("9","a","b")
        (list |? {_<10}) ==> List(9)
        (list || {e=>List(e,e*e)}) ==> List(9,81,10,100,11,121)
    def testUrl() =
        "./.gitignore".file
          |! { "Checking content of file " + _ |> println }
          |> { _.text.contains(".*") ==> true }
        "http://www.gov.cn/".url.text.contains("政府") ==> true
