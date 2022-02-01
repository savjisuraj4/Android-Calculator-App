package com.example.calculator

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.IllegalStateException
import java.lang.RuntimeException
import java.lang.reflect.InvocationTargetException

open class MainActivity : AppCompatActivity() {
    private var text = "0"
    private var answer = 0.0
    private var called = false
    private var point=0
    private var sign=""
    private var solution=0.0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lockDeviceRotation(true)
        
        updateDisplay("0")
        val butt1=findViewById<ImageButton>(R.id.fullscreen)
        butt1.setOnClickListener{
            val intent=Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }

    }


    private val operationList: MutableList<String> = arrayListOf()
    private var numberCache: MutableList<String> = arrayListOf()
    private fun lockDeviceRotation(value: Boolean) {
        if (value) {
            val currentOrientation = this.resources.getConfiguration().orientation
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT)
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT)
            }
        }
    }

    fun makeString(list: List<String>, joiner: String = ""): String {

        if (list.isEmpty()) return ""
        return list.reduce { r, s -> r + joiner + s }
    }

    fun clearCache() {
        numberCache.clear()
        operationList.clear()
    }

    private fun updateDisplay(mainDisplayString: String) {

        val fullCalculationString = makeString(operationList, " ")
        val fullCalculationTextView = findViewById(R.id.fullCalculationText) as TextView
        fullCalculationTextView.text = fullCalculationString

        val mainTextView = findViewById(R.id.textView) as TextView

        mainTextView.text = mainDisplayString
    }

    fun clearClick(view: View) {
        clearCache()
        updateDisplay("")
    }


    fun removea(view: View) {
        if (numberCache.isEmpty()) {
            clearCache()
        } else {
            var numberString1 = makeString(numberCache)
            val ss = numberString1.length
            numberString1 = numberString1.removeRange(ss - 1, endIndex = ss)
            updateDisplay(numberString1)
            numberCache.clear()
            numberCache.add(numberString1)
        }
    }
    fun buttonClick1(view: View) {
        called=true
        val button = view as Button
        sign=button.text.toString()

//        operationList.add(makeString(numberCache))
//        numberCache.clear()
        val sign1=sign+"("
        updateDisplay(sign1)
        operationList.add(sign1)

    }
    fun buttonClick(view: View) {
        called=true
        val button = view as Button
        sign=button.text.toString()
        if (numberCache.isEmpty()) return
        operationList.add(makeString(numberCache))
        numberCache.clear()
        when(sign){
            "x!"->sign="!"
            "x\u207f"->sign="^"
        }
        updateDisplay(sign)
        operationList.add(sign)

    }
    fun countOccurrences(s: String, ch: String): Int {
        point=s.filter { it.toString() == ch }.count()
        return point
    }
    fun numberClick(view: View) {
        val button = view as Button
        var numberString = button.text.toString()
        if (numberString == ".") {
            if (countOccurrences(numberCache.toString(), ".") > 0) {
                numberString = ""
            }
        }
        numberCache.add(numberString)

        text = makeString(numberCache)
        if(text.contains("π")){
//            operationList.remove("π")
//            operationList.add("3.14159265")
            text.replace("π","3.14159265")
        }
        updateDisplay(text)

    }
    fun equalsClick(view: View) {

        operationList.add(makeString(numberCache))
        if(operationList.contains("π")){
            val index=operationList.indexOf("π")
            operationList.remove("π")
            operationList.add(index,"3.14159265")
        }
        if(operationList.contains("e")){
            val index=operationList.indexOf("e")
            operationList.remove("e")
            operationList.add(index,"2.718281")
        }
        numberCache.clear()
        val calculator = StringCalculator()
        try {
            answer = calculator.calculate(operationList)
            val number3digits: Double = String.format("%.3f", answer).toDouble()
            solution = String.format("%.2f", number3digits).toDouble()

            if (solution.toString() == "Infinity") {
                updateDisplay("∞")
            } else {
                updateDisplay(" = " + solution.toString())
            }
        }catch (eee:RuntimeException){
            updateDisplay("error")
        }
        catch (e: InvocationTargetException){
                updateDisplay("error")
        }catch (ee: IllegalStateException){
            updateDisplay("error")
        }
        clearCache()

        if (called == true) {
            numberCache.add(solution.toString())
        }
    }
}





