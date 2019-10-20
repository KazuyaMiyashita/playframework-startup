import com.google.inject.AbstractModule
import com.google.inject.TypeLiteral
import auth.driver.{UserRefiner, UserRefinerMock}
import auth.repository.{UserRepository, UserRepositoryImpl}
import cafe.{CafeRepository, CafeRepositoryImpl}

import scala.concurrent.Future

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRefiner]).to(classOf[UserRefinerMock])
    bind(new TypeLiteral[UserRepository[Future]]() {}).to(classOf[UserRepositoryImpl])
    bind(classOf[CafeRepository]).to(classOf[CafeRepositoryImpl])
  }
}
