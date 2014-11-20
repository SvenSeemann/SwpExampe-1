package messaging

import org.salespointframework.catalog.Catalog

/**
 * Created by justusadam on 21/11/14.
 */
@
class DBInbox extends Inbox{
  override def store(message: Message): Boolean = ???

  override def fetchNew: List[Message] = ???

  override def delete(message: Message): Unit = ???

  override def fetchAll: List[Message] = ???

  override def hasNewMessages: Boolean = ???
}
