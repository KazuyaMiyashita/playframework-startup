import com.google.inject.AbstractModule
import auth.{UserRefiner, UserRefinerImpl, UserRepository, UserRepositoryImpl}
import cafe.{CafeRepository, CafeRepositoryImpl}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRefiner]).to(classOf[UserRefinerImpl])
    bind(classOf[UserRepository]).to(classOf[UserRepositoryImpl])
    bind(classOf[CafeRepository]).to(classOf[CafeRepositoryImpl])
  }
}
