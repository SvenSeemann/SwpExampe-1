package fviv.messaging

import org.salespointframework.core.SalespointRepository

/**
 * Created by justusadam on 24/11/14.
 */
trait PostOffice extends SalespointRepository[Message, Integer]{
  def findByRecipient(recipient:Int):java.lang.Iterable[Message]
}
