import com.google.inject.AbstractModule
import auth.{UserRefiner, UserRefinerMock}
import auth.{UserRepository, UserRepositoryImpl}
import cafe.{CafeRepository, CafeRepositoryImpl}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRefiner]).to(classOf[UserRefinerMock])
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])
    bind(classOf[CafeRepository]).to(classOf[CafeRepositoryImpl])
  }
}
