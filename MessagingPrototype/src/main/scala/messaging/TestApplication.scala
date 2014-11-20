package messaging

import org.salespointframework.{Salespoint, SalespointWebConfiguration}
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.orm.jpa.EntityScan
import org.springframework.context.annotation.{ComponentScan, Configuration}
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Created by justusadam on 20/11/14.
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = Array(classOf[Salespoint], classOf[TestApplication]))
@EnableJpaRepositories(basePackageClasses = Array(classOf[Salespoint], classOf[TestApplication]))
@ComponentScan
class TestApplication {
}

object TestApplication extends App{
  override def main(args:Array[String]) = {
    SpringApplication.run(Array(classOf[TestApplication], args))
  }

  @Configuration
  class TestApplicationConfiguration extends SalespointWebConfiguration {

  }

}