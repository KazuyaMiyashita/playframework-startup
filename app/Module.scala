import com.google.inject.AbstractModule

import auth.{UserRefiner, UserRefinerImpl}
import cafe.{CafeRepository, MockCafeRepository}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[UserRefiner]).to(classOf[UserRefinerImpl])
    bind(classOf[CafeRepository]).to(classOf[MockCafeRepository])
  }
}
