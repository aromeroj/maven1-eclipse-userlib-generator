package aromeroj.utils

import scala.xml.Node

/**
 * Created by Anderson on 6/13/15.
 */
trait XmlDeserializable[T] {

  def deserialize(dependencyXmlNode: Node): T
}
