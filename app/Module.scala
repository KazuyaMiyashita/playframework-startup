import com.google.inject.AbstractModule

import cafe.{CafeRepository, MockCafeRepository}

class Module extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CafeRepository]).to(classOf[MockCafeRepository])
  }
}
