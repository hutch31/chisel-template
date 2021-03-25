package gcd

/**
 * @author ${user.name}
 */
object GenGCD {

  def main(args : Array[String]) {
    println( "Generating GCD" )
    chisel3.Driver.execute(args, () => new GCD)
  }
}
