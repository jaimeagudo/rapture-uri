/**********************************************************************************************\
* Rapture URI Library                                                                          *
* Version 0.10.0                                                                               *
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
import java.util.zip._
import java.io._
import language.higherKinds

import language.experimental.macros

object `package` {

  type AnyPath = Path[_]

  /** Support for URI string literals */
  implicit class UriContext(sc: StringContext) {
    def uri(content: String*) = macro UriMacros.uriImplementation
  }

  /** Convenient empty string for terminating a path (which should end in a /). */
  val `$`: String = ""

  /** The canonical root for a simple path */
  val `^`: SimplePath = new SimplePath(Nil, Map())

  type AfterPath = Map[Char, (String, Double)]
  
  implicit val simplePathsLinkable: Linkable[SimplePath, SimplePath] = SimplePathsLinkable

  implicit def navigableExtras[Res: Navigable](url: Res): NavigableExtras[Res] =
    new NavigableExtras(url)

}
