//> using scala 3.8.4

import scala.compiletime.deferred
import scala.quoted.{Expr, Quotes, Type}

trait T[A] {
  given Quotes => Type[A] = deferred

  def default(using Quotes): Expr[A]

  def assign(using Quotes): Expr[A] = {
    '{
      val x = $default
      x
    }
  }
}
