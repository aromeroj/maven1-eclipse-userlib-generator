package aromeroj.utils

import scala.xml.Elem

/**
 * Created by Anderson on 6/13/15.
 */
trait XmlSerializable[T] {

  def serialize: Elem
}
