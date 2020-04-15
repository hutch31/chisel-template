package gcd

import chisel3._
import chiseltest._
import org.scalatest._
import chiseltest.experimental.TestOptionBuilder._
import chiseltest.internal.WriteVcdAnnotation

import scala.util.Random

class GcdTester extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior of "Testers2"

  it should "send data without errors" in {
    test(new GCD()) {
      c => {
        def computeGcd(a: Int, b: Int): (Int, Int) = {
            var x = a
            var y = b
            var depth = 1
            while(y > 0 ) {
            if (x > y) {
                x -= y
            }
            else {
                y -= x
            }
            depth += 1
            }
            (x, depth)
        }
    

        for(i <- 1 to 40 by 3) {
            for (j <- 1 to 40 by 7) {
                c.io.value1.poke(i.U)
                c.io.value2.poke(j.U)
                c.io.loadingValues.poke(true.B)
                c.clock.step(1)
                c.io.loadingValues.poke(false.B)

                val (expected_gcd, steps) = computeGcd(i, j)

                c.clock.step(steps-1) // -1 is because we step(1) already to toggle the enable

                c.io.outputGCD.expect(expected_gcd.U)
                c.io.outputValid.expect(true.B)
            }
        }
      }
    }
  }
}
