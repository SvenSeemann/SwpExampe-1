package messaging.errors

/**
 * Created by justusadam on 19/11/14.
 *
 * Exception if a user or something identified by a user could not be found
 */
class NoSuchUserError(identifier:Any) extends NoSuchElementException{
  override def toString = {
    "User with identifier " + identifier + " could not be found"
  }
}
