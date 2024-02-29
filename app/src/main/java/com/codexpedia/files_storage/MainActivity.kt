package com.codexpedia.files_storage


import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import android.app.Activity
import android.content.Context
import android.os.Bundle
// import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity() {
    companion object {
        private val fileName = "my_note.txt"
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initButtonListeners()
    }

    private fun initButtonListeners() {
        btnWrite.setOnClickListener {
            if (!fileExists()) {
                try {
                    val inputString = et_input_string.text.toString()
                    writeFile(inputString)
                } catch (e: IOException) {
                    showError(e.message)
                }

            } else {
                displayText(fileName + " already exists!")
            }
        }

        btn_read.setOnClickListener {
            if (fileExists()) {
                try {
                    val myString = readFile()
                    displayText(myString)
                } catch (e: IOException) {
                    showError(e.message)
                }

            } else {
                displayText(fileName + " does not exist!")
            }
        }

        btn_delete.setOnClickListener {
            if (fileExists()) {
                deleteMyFile(fileName)
            } else {
                displayText(fileName + " does not exist")
            }
        }
    }

    @Throws(IOException::class)
    private fun writeFile(stringToSave: String) {
        var outStream: FileOutputStream? = null
        try {
            outStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            outStream!!.write(stringToSave.toByteArray())
            if (fileExists()) {
                displayText("File saved.")
            } else {
                displayText("File not saved.")
            }
        } catch (e: Exception) {
            showError(e.message)
        } finally {
            outStream!!.close()
        }
    }

    @Throws(IOException::class)
    private fun readFile(): String {
        var inStream: FileInputStream? = null
        var myInputStreamReader: InputStreamReader? = null
        var myBufferedReader: BufferedReader? = null
        var retrievedString = ""
        try {
            inStream = openFileInput(fileName)
            myInputStreamReader = InputStreamReader(inStream!!)
            myBufferedReader = BufferedReader(myInputStreamReader)
            var readLineString: String? = myBufferedReader.readLine()
            while (readLineString != null) {
                retrievedString = retrievedString + readLineString
                readLineString = myBufferedReader.readLine()
            }
        } catch (ioe: IOException) {
            showError(ioe.message)
        } finally {
            myBufferedReader!!.close()
            myInputStreamReader!!.close()
            inStream!!.close()
        }
        return retrievedString
    }

    private fun deleteMyFile(fileName: String) {
        val dir = filesDir
        val file = File(dir, fileName)
        val deleted = file.delete()
        if (deleted) {
            displayText("File deleted.")
        } else {
            displayText("File not deleted.")
        }
    }

    private fun fileExists(): Boolean {
        val dir = filesDir
        val file = File(dir, fileName)
        return file.exists()
    }

    private fun displayText(stringName: String) {
        tv_display.text = stringName
    }

    private fun showError(message: String?) {
        tv_display.setTextColor(resources.getColor(R.color.red))
        tv_display.text = "ERROR: " + message
    }

}
