import com.wiwiwa.ezscala.EzScala.*
import org.junit.jupiter.api.Test

class EzScalaTest:
    @Test def myTest() =
        "./.gitignore".file
            |! { "File content of " + _ + ": " |> println }
            |> {_.text}
            |> println
