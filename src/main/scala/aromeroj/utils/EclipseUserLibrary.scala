package aromeroj.utils

import scala.xml.Elem

/**
 * Created by Anderson on 6/13/15.
 */
class EclipseUserLibrary (name: String, systemLibrary: Boolean, archivePaths: Array[String]) extends XmlSerializable[EclipseUserLibrary] {

  override def serialize: Elem = {
    <library name={ name } systemlibrary={ systemLibrary.toString }>
      {archivePaths.map( x => <archive path={x}/>)}
    </library>
  }
}

