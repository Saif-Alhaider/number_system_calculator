package com.example.number_system_calculator


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener


class MainActivity : AppCompatActivity() {


    lateinit var editText1: EditText
    lateinit var editText2: EditText
    lateinit var editText3: EditText
    lateinit var editText4: EditText

    private var currentEditText: EditText? = null
    private var textWatcher: TextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText1: EditText = findViewById(R.id.editText1)
        val editText2: EditText = findViewById(R.id.editText2)
        val editText3: EditText = findViewById(R.id.editText3)
        val editText4: EditText = findViewById(R.id.editText4)
        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                when (currentEditText?.id) {
                    editText1.id -> {
                        editText2.setText(convertDecimal(p0)?.get("binary") ?: "NaN")
                        editText3.setText(convertDecimal(p0)?.get("hex") ?: "NaN")
                        editText4.setText(convertDecimal(p0)?.get("octal") ?: "NaN")
                    }
                    editText2.id -> {
                        editText1.setText(convertBinary(p0)?.get("decimal") ?: "NaN")
                        editText3.setText(convertBinary(p0)?.get("hex") ?: "NaN")
                        editText4.setText(convertBinary(p0)?.get("octal") ?: "NaN")
                    }
                    editText3.id -> {
                        editText1.setText(convertHex(p0)?.get("decimal") ?: "NaN")
                        editText2.setText(convertHex(p0)?.get("binary") ?: "NaN")
                        editText4.setText(convertHex(p0)?.get("octal") ?: "NaN")
                    }
                    else -> {
                        editText1.setText(convertOctal(p0)?.get("decimal") ?: "NaN")
                        editText2.setText(convertOctal(p0)?.get("binary") ?: "NaN")
                        editText3.setText(convertOctal(p0)?.get("hex") ?: "NaN")
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}


        }

        editText1.onFocusChangeListener = CustomOnFocusChange()
        editText2.onFocusChangeListener = CustomOnFocusChange()

        editText3.onFocusChangeListener = CustomOnFocusChange()
        editText4.onFocusChangeListener = CustomOnFocusChange()
    }

    inner class CustomOnFocusChange() : OnFocusChangeListener {
        override fun onFocusChange(p0: View?, p1: Boolean) {
            currentEditText?.removeTextChangedListener(textWatcher)

            currentEditText = p0 as EditText

            currentEditText?.addTextChangedListener(textWatcher)
        }

    }

    private fun convertDecimal(number: CharSequence?): Map<String, String>? {

        if (number == null) return null
        val hexString = number.toString().toIntOrNull()?.toString(16)?.uppercase() ?: return null
        val binaryString = number.toString().toIntOrNull()?.toString(2) ?: return null
        val octalString = number.toString().toIntOrNull()?.toString(8) ?: return null
        return mapOf(
            "hex" to hexString,
            "binary" to binaryString,
            "octal" to octalString
        )
    }

    private fun convertBinary(number: CharSequence?): Map<String, String>? {

        if (number == null) return null
        val hexString = number.toString().toIntOrNull()?.toString(16)?.uppercase() ?: return null
        val decimalString = number.toString().toIntOrNull(2)?.toString() ?: return null
        val octalString = number.toString().toIntOrNull()?.toString(8) ?: return null
        return mapOf(
            "hex" to hexString,
            "decimal" to decimalString,
            "octal" to octalString
        )
    }

    private fun convertHex(number: CharSequence?): Map<String, String>? {

        if (number == null) return null
        val binaryString = number.toString().toIntOrNull(16)?.toString(2) ?: return null
        val decimalString = number.toString().toIntOrNull(16)?.toString() ?: return null
        val octalString = number.toString().toIntOrNull(16)?.toString(8) ?: return null
        return mapOf(
            "binary" to binaryString,
            "decimal" to decimalString,
            "octal" to octalString
        )
    }

    private fun convertOctal(number: CharSequence?): Map<String, String>? {

        if (number == null) return null
        val binaryString = number.toString().toIntOrNull(8)?.toString(2) ?: return null
        val decimalString = number.toString().toIntOrNull(8)?.toString() ?: return null
        val hexString = number.toString().toIntOrNull(8)?.toString(16) ?: return null
        return mapOf(
            "binary" to binaryString,
            "decimal" to decimalString,
            "hex" to hexString
        )
    }
}