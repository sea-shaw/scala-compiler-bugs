//> using scala 3.8.4

import scala.quoted.{Expr, Type, Quotes}

type Unapply[A] = A match {
  case Unit => Boolean
  case _    => Option[A]
}

trait Extractor[A] {
  def unapply(s: String): Unapply[A]
}

object Extractor {
  inline def apply[A] = ${ extractorCode[A] }
}

def extractorCode[A: Type](using Quotes): Expr[Extractor[A]] = '{
  new Extractor[A] {
    override def unapply(s: String): Unapply[A] = ${ unapplyCode[A] }
  }
}

def unapplyCode[A: Type](using Quotes): Expr[Unapply[A]] = {
  val expr = Type.of[A] match {
    case '[Unit] => '{ false }
    case _       => '{ None }
  }
  expr.asExprOf[Unapply[A]]
}
