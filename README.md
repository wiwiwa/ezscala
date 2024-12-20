EzScala is a Scala library that helps write Scala easier.

It provides the following features:
* Extension methods
  * Operators
    * `&&`: AND operator for Truthy/Falsy value
    * `||`: OR operator for Truthy/Falsy value
    * `|`: map left value to another value, by right function
      * For `String`: write string in UTF-8 encoding to a `File` or `OutputStream`
      * For `Array[Byte]`, `Iterator[Byte]`, `Iterable[Byte]`, `InputStream`: write bytes in UTF-8 encoding to a `File` or `OutputStream`
    * `|=`: call right function, and return left value
    * `|==`: assert left value is true, or throw AssertionError
    * `|>`: alias of `Iterable.map`, `Iterator.map`
    * `|>|`: alias of `Iterable.flatmap`, `Iterator.flatmap`
    * `|?`: alias of `Iterable.filter`, `Iterator.filter`
    * `|!`: alias of `Iterable.tapeach`, `Iterator.tapeach`
    * `|!!`: alias of `Iterable.foreach`, `Iterator.foreach`
  * `String`:
    * `bytes`: get UTF-8 encoded bytes of String
    * `find`, `find1`, `find2`, `find3`: find substring by regular expression, and return found substring, or group 1/2/3
    * `replace`, `replaceAll`: replace substring by regular expression
    * `file`: use this string as file path to create a `File` object
    * `http`: use this string as URL to create a Http request `HttpRequestData` object. See IO section
  * `Array[Byte]`
    * `string`: UTF-8 decode to String
    * `base64`: Base64 encode to String
    * `base64Url`: Base64 encode to String, with URL safe
  * IO operations
    * `AutoClosable`
      * `use`: consume resource, and auto close it
    * `File`
      * `bytes`: return all bytes as a `Array[Byte]`
      * `/`: resolve child item in directory
      * `parent`: resolve parent directory
    * `InputStream`
      * `bytes`: return all bytes as a `Array[Byte]`
    * `HttpRequestData`:
      * `header`: add http request header to request
      * `get`, `post`: send HTTP Get/Post request, and return a `HttpResponseWrapper`
        * `HttpResponseWrapper`:
          * `close`: implementation of `AutoClosable`
          * `statusCode`: return status code of HTTP response
          * `withStream`: call a function with an `InputStream` of HTTP response
          * `bytes`: return all bytes as a `Array[Byte]`
          * `text`: return http response content as UTF-8 String 
  * OS interaction
    * `os.run`: run a command line, check its return status, and return its output as String
    * `os.sh`: run a command line in shell, check its return status, and return its output as String
