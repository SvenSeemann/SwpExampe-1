package messaging.errors

/**
 * Created by justusadam on 19/11/14.
 */
class NoSuchUserError(identifier:Any) extends NoSuchElementException{
  override def toString = {
    "User with identifier " + identifier + " could not be found"
  }
}
