/**
 * Copyright (c) 2006-2010 Berlin Brown and botnode.com  All Rights Reserved
 *
 * http://www.opensource.org/licenses/bsd-license.php

 * All rights reserved.

 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:

 * * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * * Neither the name of the Botnode.com (Berlin Brown) nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written permission.

 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Date: 1/5/2009, updated 5/5/2010, added Scala 2.8rc3
 * 
 * Example DSL in Scala
 * 
 * Original created by: Debasish Ghosh
 * 
 * http://debasishg.blogspot.com/2009/07/dsl-composition-techniques-in-scala.html
 */

package org.berlin.p1.dsl

import scala.util.parsing.combinator.syntactical._

class IteratorWrapper[A](iter:java.util.Iterator[A]) {
    
    def foreach(f: A => Unit): Unit = {
        while(iter.hasNext) {
          f(iter.next)
        }
    }
}

class ClientOrderSpecial(lineItems:java.util.List[LineItem], acct:String) extends ClientOrder(lineItems, acct) {
    
    implicit def iteratorToWrapper[T](iter:java.util.Iterator[T]):IteratorWrapper[T] = new IteratorWrapper[T](iter)
    
    def toStringReport() : String = {
      
      val buf  = new StringBuilder
      val rows = this.getLineItems.iterator
      for (item <- rows) {
          buf.append(' ').append(item).append('\n')
      }
      "@ClientOrder - " + this.getAccountNo + " line-items : { \n" + buf.toString + "}"
    }
    
    override def toString() : String = { toStringReport }
}

/**
 * Basic DSL.
 * 
 * @author bbrown
 *
 */
class OrderDSL extends StandardTokenParsers {
    
  def scala2JavaList(sl: List[LineItem]): java.util.List[LineItem] = {
          var jl = new java.util.ArrayList[LineItem]()
          sl.foreach(jl.add(_))
          jl
  }

  lexical.delimiters ++= List("(", ")", ",")
  lexical.reserved   += ("buy", "sell", "shares", "at", "max", "min", "for", "trading", "account")

  def instr: Parser[ClientOrder] = {
          trans ~ account_spec ^^ { 
              case t ~ a => new ClientOrderSpecial(scala2JavaList(t), a) 
          }
  }

  def trans: Parser[List[LineItem]] = "(" ~> repsep(trans_spec, ",") <~ ")" ^^ { (ts: List[LineItem]) => ts }

  def trans_spec: Parser[LineItem] = buy_sell ~ buy_sell_instr ^^ { case bs ~ bsi => new LineItem(bsi._1._2, bsi._1._1, bs, bsi._2) }

  def account_spec = "for" ~> "trading" ~> "account" ~> stringLit ^^ {case s => s}

  def buy_sell: Parser[ClientOrder.BuySell] =
    ("buy" | "sell") ^^ { case "buy"  => ClientOrder.BuySell.BUY
                          case "sell" => ClientOrder.BuySell.SELL }

  def buy_sell_instr: Parser[((Int, String), Int)] = security_spec ~ price_spec ^^ { case s ~ p => (s, p) }

  def security_spec: Parser[(Int, String)] = numericLit ~ ident ~ "shares" ^^ { case n ~ a ~ "shares" => (n.toInt, a) }

  def price_spec: Parser[Int] = "at" ~ ("min" | "max") ~ numericLit ^^ { case "at" ~ s ~ n => n.toInt }
  
  def doMatch(dsldoc:String) {
      
      /* Example dsl: val dsl = "(buy 100 IBM shares at max 45, sell 40 Sun shares at min 24, buy 25 CISCO shares at max 56) for trading account \"A1234\"" */                
      instr(new lexical.Scanner(dsldoc)) match {
          case Success(ord, _) => println(ord)  /* Process Order */
          case Failure(msg, _) => println(msg)
          case Error(msg, _)   => println(msg)
      }
      
  } // End of the Method //
  
} // End of the Class //

/**
 * Main entry point.
 * @author bbrown
 *
 */
object RunOrderDSL {
    
    def main(args : Array[String]) : Unit = {
        
            // Read the file
            val lines = scala.io.Source.fromPath("./docs/stockbuys.dsl").mkString        
            val dsl = new OrderDSL()
            dsl.doMatch(lines)
    }
    
} // End of the Object //
