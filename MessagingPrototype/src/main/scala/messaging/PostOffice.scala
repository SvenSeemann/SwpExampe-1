package messaging

import org.springframework.data.repository.Repository

/**
 * Created by justusadam on 24/11/14.
 */
trait PostOffice extends Repository[Message, Integer]{
}
