package com.example.calculator

import java.lang.Math.pow
import kotlin.math.*

class StringCalculator {

    private fun factorial(current: Double):Double {
        if (current.toInt() == 1) {
            return 1.0
        }
        return current * factorial(current - 1)
    }

    fun calculate(calculationList: List<String>): Double {
        var currentOp: String = ""
        var currentNumber = 0.0

        calculationList.forEach { token ->
            when {
                token.equals("+")
                        || token.equals("/")
                        || token.equals("*")
                        || token.equals("-")
                        || token.equals("%") ||token.equals("\u221a(") || token.equals("^")
                        || token.equals("!") || token.equals("lg(") || token.equals("ln(")
                        || token.equals("sin(") || token.equals("cos(") || token.equals("tan(")-> currentOp = token
                currentOp.equals("%") -> currentNumber /= 100
                currentOp.equals("-") -> currentNumber -= token.toDouble()
                currentOp.equals("/") -> currentNumber /= token.toDouble()
                currentOp.equals("*") -> currentNumber *= token.toDouble()
                currentOp.equals("\u221a(")-> currentNumber=sqrt(token.toFloat()).toDouble()
                currentOp.equals("^")->currentNumber=pow(currentNumber,token.toDouble())
                currentOp.equals("!")->currentNumber=factorial(currentNumber)
                currentOp.equals("lg(")->currentNumber= log10(token.toDouble())
                currentOp.equals("ln(")->currentNumber= log(token.toDouble(), 2.718281)
                currentOp.equals("sin(")->currentNumber= sin(token.toDouble())
                currentOp.equals("cos(")->currentNumber= cos(token.toDouble())
                currentOp.equals("tan(")->currentNumber= tan(token.toDouble())

                else -> currentNumber += token.toDouble()

            }


        }

        return currentNumber
    }
}





