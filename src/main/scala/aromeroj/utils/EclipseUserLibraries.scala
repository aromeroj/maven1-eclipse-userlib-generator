package aromeroj.utils

import scala.collection.mutable.ArrayBuffer
import scala.xml.Elem

/**
 * Created by Anderson on 6/13/15.
 */
class EclipseUserLibraries extends XmlSerializable[EclipseUserLibraries] {

  val libraries = ArrayBuffer[EclipseUserLibrary]()

  def addLibrary(eclipseUserLibrary: EclipseUserLibrary): Unit = {
    libraries += eclipseUserLibrary
  }

  override def serialize: Elem = {
    <eclipse-userlibraries version="2">
      {libraries.map( x => x.serialize )}
    </eclipse-userlibraries>
  }
}
