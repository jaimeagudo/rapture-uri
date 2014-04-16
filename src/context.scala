/**********************************************************************************************\
* Rapture URI Library                                                                          *
* Version 0.9.0                                                                                *
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

object UriMacros {
  def uriImplementation(c: whitebox.Context)(content: c.Expr[String]*): c.Expr[Any] = {
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
                    TermName(scheme.capitalize)
                  ),
                  TermName("parse")
                ),
                List(
                  Apply(
                    Select(
                      Apply(
                        Select(
                          Ident(TermName("List")),
                          TermName("apply")
                        ),
                        (0 until (xs.length + ys.length) map { i => if(i%2 == 0) ys(i/2) else xs(i/2) }).to[List]
                      ),
                      TermName("mkString")
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
