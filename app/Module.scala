import com.google.inject.AbstractModule
import auth.driver.{UserRefiner, UserRefinerMock}
import auth.repository.{UserRepository, UserRepositoryImpl}
import cafe.{CafeRepository, CafeRepositoryImpl}

import scala.concurrent.Future

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRefiner]).to(classOf[UserRefinerMock])
    bind(classOf[UserRepository[Future]]).to(classOf[UserRepositoryImpl[Future]])
    bind(classOf[CafeRepository]).to(classOf[CafeRepositoryImpl])
  }
}
