/**********************************************************************************************\
* Rapture URI Library                                                                          *
* Version 1.1.0                                                                                *
*                                                                                              *
* The primary distribution site is                                                             *
*                                                                                              *
*   http://rapture.io/                                                                         *
*                                                                                              *
* Copyright 2010-2014 Jon Pretty, Propensive Ltd.                                              *
*                                                                                              *
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file    *
* except in compliance with the License. You may obtain a copy of the License at               *
*                                                                                              *
*   http://www.apache.org/licenses/LICENSE-2.0                                                 *
*                                                                                              *
* Unless required by applicable law or agreed to in writing, software distributed under the    *
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,    *
* either express or implied. See the License for the specific language governing permissions   *
* and limitations under the License.                                                           *
\**********************************************************************************************/
package rapture.uri

import rapture.core._

import language.experimental.macros
import scala.reflect.macros._

object Paramable {
  implicit val stringParamable = new Paramable[String] { def paramize(s: String): String = s }
  implicit val intParamable = new Paramable[Int] { def paramize(s: Int): String = s.toString }
  implicit val doubleParamable = new Paramable[Double] { def paramize(s: Double): String = s.toString }
}

trait Paramable[T] {
  def paramize(t: T): String
}

object UriMacros {
  
  def paramsMacro[T: c.WeakTypeTag](c: Context): c.Expr[QueryType[AnyPath, T]] = {
    import c.universe._
    require(weakTypeOf[T].typeSymbol.asClass.isCaseClass)

    val paramable = typeOf[Paramable[_]].typeSymbol.asType.toTypeConstructor

    val params = weakTypeOf[T].declarations collect {
      case m: MethodSymbol if m.isCaseAccessor => m.asMethod
    } map { p =>
      val implicitParamable = c.Expr[Paramable[_]](c.inferImplicitValue(appliedType(paramable, List(p.returnType)), false, false)).tree
      val paramValue = Apply(
        Select(
          implicitParamable,
          newTermName("paramize")
        ),
        List(
          Select(
            Ident(newTermName("t")),
            p.name
          )
        )
      )

      val paramName = Literal(Constant(p.name.toString+"="))
      
      Apply(
        Select(paramName, newTermName("$plus")),
        List(paramValue)
      )
    }

    val listOfParams = c.Expr[List[String]](Apply(
      Select(
        Ident(newTermName("List")),
        newTermName("apply")
      ),
      params.to[List]
    ))

    reify {
      new QueryType[AnyPath, T] {
        def extras(existing: Map[Char, (String, Double)], t: T): Map[Char, (String, Double)] =
          existing ++ Map('?' -> (listOfParams.splice.mkString("&"), 1.0))
      }
    }
  }
  
  def uriImplementation(c: Context)(content: c.Expr[String]*): c.Expr[Any] = {
    import c.universe._

    c.prefix.tree match {
      case Apply(_, List(Apply(_, rawParts))) =>
        val xs = content.map(_.tree).to[Array]
        val ys = rawParts.to[Array]

        rawParts.head match {
          case Literal(Constant(part: String)) =>
            val Array(scheme, _) = part.split(":", 2)
            c.Expr(
              Apply(
                Select(
                  Ident(
                    newTermName(scheme.capitalize)
                  ),
                  newTermName("parse")
                ),
                List(
                  Apply(
                    Select(
                      Apply(
                        Select(
                          Ident(newTermName("List")),
                          newTermName("apply")
                        ),
                        (0 until (xs.length + ys.length) map { i => if(i%2 == 0) ys(i/2) else xs(i/2) }).to[List]
                      ),
                      newTermName("mkString")
                    ),
                    List(Literal(Constant("")))
                  )
                )
              )
            )
        }
    }
  }
}

object Test { def apply(x: String) = Symbol(x) }
