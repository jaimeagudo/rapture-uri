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

trait Sizable[UrlType] {
  type ExceptionType <: Exception
  /** Returns the size in bytes of the specified URL */
  def size(url: UrlType)(implicit eh: ExceptionHandler): eh.![Long, ExceptionType]
}

class SizableExtras[UrlType: Sizable](url: UrlType) {
  /** Returns the size in bytes of this URL */
  def size(implicit eh: ExceptionHandler): eh.![Long, Exception] =
    eh.wrap(?[Sizable[UrlType]].size(url)(raw))
}
