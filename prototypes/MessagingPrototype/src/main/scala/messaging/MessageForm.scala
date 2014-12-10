package fviv.messaging

import scala.collection.JavaConversions

/**
 * Created by justusadam on 20/11/14.
 */
class MessageForm(val receives:Boolean, val sends:Boolean, val message:Iterable[String]) {

  def getReceives = receives

  def getSends = sends

  def getMessage = JavaConversions.asJavaIterable(message)

  def this(receives:Boolean, sends:Boolean) {
    this(receives, sends, List[String]())
  }
}
