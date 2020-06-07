package com.example.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.IntegerRes
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import java.lang.Thread.yield
import java.math.BigInteger

class MainActivity : AppCompatActivity() {

    lateinit var factorial: BigInteger;
    var number: Int = 0;

    var op:String="-1";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        one.setOnClickListener{
            appendOnExpression("1",true)
            number=number*10+1

        }
        two.setOnClickListener{
            appendOnExpression("2",true)
            number=number*10+2

        }
        three.setOnClickListener{
            appendOnExpression("3",true)
            number=number*10+3

        }
        four.setOnClickListener{
            appendOnExpression("4",true)
            number=number*10+4

        }
        five.setOnClickListener{
            appendOnExpression("5",true)
            number=number*10+5

        }
        six.setOnClickListener{
            appendOnExpression("6",true)
            number=number*10+6

        }
        seven.setOnClickListener{
            appendOnExpression("7",true)
            number=number*10+7

        }
        eight.setOnClickListener{
            appendOnExpression("8",true)
            number=number*10+8

        }
        nine.setOnClickListener{
            appendOnExpression("9",true)
            number=number*10+9

        }
        zero.setOnClickListener{
            appendOnExpression("0",true)
            number=number*10
        }
        dot.setOnClickListener{
            appendOnExpression(".",true)
            number=0

        }

        plus.setOnClickListener{
            appendOnExpression("+",false)
            op="+"
        }
        minus.setOnClickListener{
            appendOnExpression("-",false)
            op="-"

        }
        divide.setOnClickListener{
            appendOnExpression("!",false)

        }
        multi.setOnClickListener{
            appendOnExpression("*",false)
            op="*"

        }
        openParenthesis.setOnClickListener{
            appendOnExpression("(",false)
            op="("

        }
        closedParenthesis.setOnClickListener{
            appendOnExpression(")",false)
            op=")"

        }

        clear.setOnClickListener{
            expression.text=""
            result.text=""
        }

        back.setOnClickListener{
            val string = expression.text.toString()
            if(string.isNotEmpty()){
                expression.text = string.substring(0,string.length-1)
                number=number/10
            }
        }

        equals.setOnClickListener{
            try {

                val expression = ExpressionBuilder(expression.text.toString()).build()
                val resultfinal = expression.evaluate()
                val longResult = resultfinal.toLong()
                op="-1"
                number=0
                if(resultfinal == longResult.toDouble()){
                    result.text = longResult.toString()
                }else{
                    result.text =resultfinal.toString()
                }

            }catch (e:Exception){
                Log.d("Expression ", "Message: "+e.message)
            }
        }

    }

    suspend fun calculateFactorialInMainThreadUsingYield(number: Int): BigInteger {
        Log.d("Inside","Inside:"+number)
        factorial = BigInteger.ONE
        for (i in 1..number) {
            yield()
            factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        }
        return factorial
    }

    fun appendOnExpression(string: String,canClear: Boolean){
        if(result.text.isNotEmpty()){
            expression.text=""
        }

        if(string.equals("!")) {
            val str = expression.text
            if (op.equals("-1")) {
                try {

                    Coroutines.main {
                        Log.d("Cor", "ca" + calculateFactorialInMainThreadUsingYield(number))
                        expression.text=calculateFactorialInMainThreadUsingYield(number).toString()
                    }
                } catch (nfe: NumberFormatException) {
                    Log.d("Number", ": Exception ")
                }
//

            } else {

                Log.d("Number", "Number: " + number)
                Coroutines.main {

                    expression.text = str.substring(
                        0,
                        (str.lastIndexOf(op) + 1)
                    ) + (calculateFactorialInMainThreadUsingYield(number)).toString()

                }
            }

        }else{
            if(canClear){
                result.text =""
                expression.append(string)
            }else{
                expression.append(result.text)
                expression.append(string)
                result.text =""
                number=0
            }
        }


    }
}
