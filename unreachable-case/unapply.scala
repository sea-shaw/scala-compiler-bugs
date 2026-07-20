//> using scala 3.8.4

class Extractor {
  def unapply(x: String): Option[Int] = Some(0)
}

@main def main = {
  val x = Extractor()
  val y = "" match {
    case x(Some(a))  => a
    case x(a :: Nil) => a
    case x(a)        => a
  }
  println(y)
}
