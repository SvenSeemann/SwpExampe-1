package fviv.messaging

import org.salespointframework.core.SalespointRepository
import org.springframework.context.annotation.Bean

/**
  * Created by justusadam on 19/11/14.
  */
trait People extends SalespointRepository[Employee, Integer] {

}
