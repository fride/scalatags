package scalatags

import org.scalatest.FreeSpec
import Util._
import scalatags.all._

/**
 * A set of examples used in the documentation.
 */
class ExampleTests extends FreeSpec{
  "examples" - {
    "Splash example" in strCheck(
      html(
        head(
          script(src:="..."),
          script(
            "alert('Hello World')"
          )
        ),
        body(
          div(
            h1(id:="title", "This is a title"),
            p("This is a big paragraph of text")
          )
        )
      ),
      """
      <html>
          <head>
              <script src="..."></script>
              <script>alert('Hello World')</script>
          </head>
          <body>
              <div>
                  <h1 id="title">This is a title</h1>
                  <p>This is a big paragraph of text</p>
              </div>
          </body>
      </html>
      """
    )
    "Hello World" in strCheck(
      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my title"),
          div(
            p("This is my first paragraph"),
            p("This is my second paragraph")
          )
        )
      ),
      """
      <html>
          <head>
              <script>some script</script>
          </head>
          <body>
              <h1>This is my title</h1>
              <div>
                  <p>This is my first paragraph</p>
                  <p>This is my second paragraph</p>
              </div>
          </body>
      </html>
      """
    )
    "Variables" in strCheck(
      {
        val title = "title"
        val numVisitors = 1023

        html(
          head(
            script("some script")
          ),
          body(
            h1("This is my ", title),
            div(
              p("This is my first paragraph"),
              p("you are the ", numVisitors.toString, "th visitor!")
            )
          )
        )
      },
      """
      <html>
          <head>
              <script>some script</script>
          </head>
          <body>
              <h1>This is my title</h1>
              <div>
                  <p>This is my first paragraph</p>
                  <p>you are the 1023th visitor!</p>
              </div>
          </body>
      </html>
      """
    )
    "Control Flow" in strCheck(
      {
        val numVisitors = 1023
        val posts = Seq(
          ("alice", "i like pie"),
          ("bob", "pie is evil i hate you"),
          ("charlie", "i like pie and pie is evil, i hat myself")
        )

        html(
          head(
            script("some script")
          ),
          body(
            h1("This is my title"),
            div("posts"),
            for ((name, text) <- posts) yield div(
                h2("Post by ", name),
                p(text)
            ),
            if(numVisitors > 100) p("No more posts!")
            else p("Please post below...")
          )
        )
      },
      """
      <html>
          <head>
              <script>some script</script>
          </head>
          <body>
              <h1>This is my title</h1>
              <div>posts</div>
              <div>
                  <h2>Post by alice</h2>
                  <p>i like pie</p>
              </div>
              <div>
                  <h2>Post by bob</h2>
                  <p>pie is evil i hate you</p>
              </div>
              <div>
                  <h2>Post by charlie</h2>
                  <p>i like pie and pie is evil, i hat myself</p>
              </div>
              <p>No more posts!</p>
          </body>
      </html>
      """
    )
    "Functions" in strCheck(
      {
        def imgBox(source: String, text: String) = div(
          img(src:=source),
          div(
            p(text)
          )
        )

        html(
          head(
            script("some script")
          ),
          body(
            h1("This is my title"),
            imgBox("www.mysite.com/imageOne.png", "This is the first image displayed on the site"),
            div(`class`:="content")(
              p("blah blah blah i am text"),
              imgBox("www.mysite.com/imageTwo.png", "This image is very interesting")
            )
          )
        )
      },
      """
      <html>
          <head>
              <script>some script</script>
          </head>
          <body>
              <h1>This is my title</h1>
              <div>
                  <img src="www.mysite.com/imageOne.png" />
                  <div>
                      <p>This is the first image displayed on the site</p>
                  </div>
              </div>
              <div class="content">
                  <p>blah blah blah i am text</p>
                  <div>
                      <img src="www.mysite.com/imageTwo.png" />
                  <div>
                      <p>This image is very interesting</p>
                  </div>
                  </div>
              </div>
          </body>
      </html>
      """
    )
    "Custom Attributes" in strCheck(
      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my title"),
          div(
            p("onclick".attr:="... do some js")(
              "This is my first paragraph"
            ),
            a("href".attr:="www.google.com")(
              p("Goooogle")
            )
          )
        )
      ),
      """
      <html>
          <head>
              <script>some script</script>
          </head>
          <body>
              <h1>This is my title</h1>
              <div>
                  <p onclick="... do some js">This is my first paragraph</p>
                  <a href="www.google.com">
                      <p>Goooogle</p>
                  </a>
              </div>
          </body>
      </html>
      """
    )
    "Attributes" in strCheck(
      html(
        head(
          script("some script")
        ),
        body(
          h1("This is my title"),
          div(
            p(onclick:="... do some js")(
              "This is my first paragraph"
            ),
            a(href:="www.google.com")(
              p("Goooogle")
            )
          )
        )
      ),
      """
      <html>
          <head>
              <script>some script</script>
          </head>
          <body>
              <h1>This is my title</h1>
              <div>
                  <p onclick="... do some js">
                      This is my first paragraph</p>
                  <a href="www.google.com">
                      <p>Goooogle</p>
                  </a>
              </div>
          </body>
      </html>
      """
    )
    "Classes and CSS Custom" in strCheck(
      {
        val contentpara = "contentpara".cls
        val first = "first".cls
        html(
          head(
            script("some script")
          ),
          body(
            h1(backgroundColor:="blue", color:="red")("This is my title"),
            div(backgroundColor:="blue", color:="red")(
              p(contentpara, first)(
                "This is my first paragraph"
              ),
              a(opacity:=0.9)(
                p("contentpara".cls)("Goooogle")
              )
            )
          )
        )
      },
    html(
      head(
        script("some script")
      ),
      body(
        h1(style:="background-color: blue; color: red;")("This is my title"),
        div(style:="background-color: blue; color: red;")(
          p(`class`:="contentpara first")(
            "This is my first paragraph"
          ),
          a(style:="opacity: 0.9;")(
            p("contentpara".cls)("Goooogle")
          )
        )
      )
    ),
      """
      <html>
          <head>
              <script>some script</script>
          </head>
          <body>
              <h1 style="background-color: blue; color: red;">This is my title</h1>
              <div style="background-color: blue; color: red;">
              <p class="contentpara first">This is my first paragraph</p>
              <a style="opacity: 0.9;">
                  <p class="contentpara">Goooogle</p>
              </a>
              </div>
          </body>
      </html>
      """
    )
    "Non-String Attributes and Styles" in strCheck(
      div(
        p(float.left)(
          "This is my first paragraph"
        ),

        a(tabindex:=10)(
          p("Goooogle")
        ),


        input(disabled:=true)
      ),
      """
      <div>
          <p style="float: left;">This is my first paragraph</p>
          <a tabindex="10">
              <p>Goooogle</p>
          </a>
          <input disabled="true" />
      </div>
      """
    )
    "Force-Stringifying Attributes and Styles" in strCheck(
      div(
        p(float:="left")(
          "This is my first paragraph"
        ),
        a(tabindex:="10")(
          p("Goooogle")
        ),
        input(disabled:="true")
      ),
      """
      <div>
          <p style="float: left;">This is my first paragraph</p>
          <a tabindex="10">
              <p>Goooogle</p>
          </a>
          <input disabled="true" />
      </div>
      """
    )
    "Filters and Transformations" in strCheck({
      def uppercase(node: Node): Node = {
        node match{
          case t: HtmlTag => t.copy(children = t.children.map(uppercase))
          case r: RawNode => r
          case StringNode(v) => StringNode(v.toUpperCase)

        }
      }
      html(
        head(
          script("some script")
        ),
        uppercase(
          body(
            h1("This is my title"),
            div(
              p(onclick:="... do some js")(
                "This is my first paragraph"
              ),
              a(href:="www.google.com")(
                p("Goooogle")
              )
            )
          )
        )
      )},
      """
      <html>
          <head>
              <script>some script</script>
          </head>
          <body>
              <h1>THIS IS MY TITLE</h1>
              <div>
                  <p onclick="... do some js">
                      THIS IS MY FIRST PARAGRAPH
                  </p>
                  <a href="www.google.com">
                      <p>GOOOOGLE</p>
                  </a>
              </div>
          </body>
      </html>
      """
    )
    "Filters and Transformations Complex" in strCheck({
      def autoLink(node: Node): Seq[Node] = {
        node match{
          case t: HtmlTag => Seq(t.copy(children = t.children.flatMap(autoLink)))
          case r: RawNode => Seq(r)
          case StringNode(v) =>
            val regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#]".r
            val text = regex.split(v).map(StringNode)
            val links =
              regex.findAllMatchIn(v)
                   .map(_.toString)
                   .map{m => a(href:=m)(m)}
                   .toSeq

            text.zipAll(links, StringNode(""), StringNode(""))
                .flatMap{case (x, y) => Seq(x, y)}
                .reverse
        }
      }

      html(
        head,
        autoLink(
          body(
            h1("This is my title"),
            div(
              p(
                "This is my first paragraph on http://www.github.com wooo"
              ),
              "I love http://www.google.com"
            )
          )
        )
      )
    },
    """
      <html>
          <head></head>
          <body>
              <h1>This is my title</h1>
              <div>
                  <p>
                      This is my first paragraph on
                      <a href="http://www.github.com">http://www.github.com</a> wooo
                  </p>
                  I love <a href="http://www.google.com">http://www.google.com</a>
              </div>
          </body>
      </html>
    """
    )

    "Layouts" in strCheck(
    {
      def page(scripts: Seq[Node], content: Seq[Node]) =
        html(
          head(scripts),
          body(
            h1("This is my title"),
            div("content".cls)(content)
          )
        )

      page(
        Seq(
          script("some script")
        ),
        Seq(
          p("This is the first ", b("image"), " displayed on the ", a("site")),
          img(src:="www.myImage.com/image.jpg"),
          p("blah blah blah i am text")
        )
      )
    },
    """
    <html>
        <head>
            <script>some script</script>
        </head>
        <body>
            <h1>This is my title</h1>
                <div class="content">
                <p>This is the first <b>image</b> displayed on the <a>site</a></p>
                    <img src="www.myImage.com/image.jpg" />
                <p>blah blah blah i am text</p>
            </div>
        </body>
    </html>
    """
    )

    "Inheritence" in strCheck(
      {
        class Parent{
          def render = html(
            headFrag,
            bodyFrag

          )
          def headFrag = head(
            script("some script")
          )
          def bodyFrag = body(
            h1("This is my title"),
            div(
              p("This is my first paragraph"),
              p("This is my second paragraph")
            )
          )
        }

        object Child extends Parent{
          override def headFrag = head(
            script("some other script")
          )
        }


        Child.render
      },
      """
      <html>
          <head>
              <script>some other script</script>
          </head>
          <body>
              <h1>This is my title</h1>
              <div>
                  <p>This is my first paragraph</p>
                  <p>This is my second paragraph</p>
              </div>
          </body>
      </html>
      """
    )


    "Manual imports" in strCheck(
      {
        import scalatags.short._
        div(
          p(*.color:="red")("Red Text"),
          img(*.href:="www.imgur.com/picture.jpg")
        )
      },
      {
        import scalatags.{Attrs => attr, Styles => css, _}
        import scalatags.Tags._
        div(
          p(css.color:="red")("Red Text"),
          img(attr.href:="www.imgur.com/picture.jpg")
        )
      },
      {
        object custom extends Tags{
          val attr = new Attrs {}
          val css = new Styles {}
        }
        import custom._
        div(
          p(css.color:="red")("Red Text"),
          img(attr.href:="www.imgur.com/picture.jpg")
        )
      },
      """
      <div>
          <p style="color: red;">Red Text</p>
          <img href="www.imgur.com/picture.jpg" />
      </div>
      """
    )

    "Proper Escaping" in strCheck(
      {
        val evilInput1 = "\"><script>alert('hello!')</script>"
        val evilInput2 = "<script>alert('hello!')</script>"

        html(
          head(
            script("some script")
          ),
          body(
            h1(
              title:=evilInput1,
              "This is my title"
            ),
            evilInput2
          )
        )

      },
      """
      <html>
          <head>
              <script>some script</script>
          </head>
          <body>
              <h1 title="&quot;&gt;&lt;script&gt;alert('hello!')&lt;/script&gt;">
                  This is my title
              </h1>
              &lt;script&gt;alert('hello!')&lt;/script&gt;
          </body>
      </html>
      """
    )

    "Unsanitized Input" in strCheck(
      {
        val evilInput = "<script>alert('hello!')</script>"

        html(
          head(
            script("some script")
          ),
          body(
            h1("This is my title"),
            raw(evilInput)
          )
        )
      },
      """
      <html>
          <head>
              <script>some script</script>
          </head>
          <body>
              <h1>This is my title</h1>
              <script>alert('hello!')</script>
          </body>
      </html>
      """
    )
    "Additional Imports" in strCheck(
      {
        import Styles2.pageBreakBefore
        import Tags2.address
        import SvgTags.svg
        import SvgStyles.stroke
        div(
          p(pageBreakBefore.always, "a long paragraph which should not be broken"),
          address("500 Memorial Drive, Cambridge MA"),
          svg(stroke:="blue")
        )
      },
      """
      <div>
          <p style="page-break-before: always;">
              a long paragraph which should not be broken
          </p>
          <address>500 Memorial Drive, Cambridge MA</address>
          <svg style="stroke: blue;"></svg>
      </div>
      """
    )
    "Typesafe CSS" in strCheck(
      div(zIndex:=10),
      """<div style="z-index: 10;"></div>"""
    )
    "Custom attributes and styles" in strCheck(
      {
        val dataAppKey = "data-app-key".attr
        val mozBorderRadius = "-moz-border-radius".style
        div(
          dataAppKey:="YOUR_APP_KEY",
          mozBorderRadius:="10px"
        )
      },
      """<div data-app-key="YOUR_APP_KEY" style="-moz-border-radius: 10px;"></div>"""
    )

    "Different ways of static typing" in strCheck(
      div(
        div(backgroundColor:=hex"ababab"),
        div(color:=rgb(0, 255, 255)),
        div(color.red),
        div(borderRightColor:=hsla(100, 0, 50, 0.5)),
        div(backgroundImage:=radialGradient(hex"f00", hex"0f0"~50.pct, hex"00f")),
        div(backgroundImage:=url("www.picture.com/my_picture")),
        div(backgroundImage:=(
          radialGradient(45.px, 45.px, "ellipse farthest-corner", hex"f00", hex"0f0"~500.px, hex"00f"),
          linearGradient("to top left", hex"f00", hex"0f0"~10.px, hex"00f")
          ))
      ),
      """
      <div>
        <div style="background-color: #ababab;"></div>
        <div style="color: rgb(0, 255, 255);"></div>
        <div style="color: red;"></div>
        <div style="border-right-color: hsla(100, 0, 50, 0.5);"></div>
        <div style="background-image: radial-gradient(#f00, #0f0 50%, #00f);"></div>
        <div style="background-image: url(www.picture.com/my_picture);"></div>
        <div style="background-image: radial-gradient(45px 45px, ellipse farthest-corner, #f00, #0f0 500px, #00f), linear-gradient(to top left, #f00, #0f0 10px, #00f);"></div>
      </div>
      """
    )
  }
}
