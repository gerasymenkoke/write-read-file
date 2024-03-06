package com.codexpedia.files_storage


import androidx.appcompat.app.AppCompatActivity
  
import android.content.Context  

//import android.support.v7.app.AppCompatActivity  
import android.os.Bundle  
import android.view.View  
import android.widget.Button  
import android.widget.EditText  
import android.widget.Toast  
import java.io.*  
import kotlin.io.*


  
class MainActivity : AppCompatActivity() {  
  
    override fun onCreate(savedInstanceState: Bundle?) {  
        super.onCreate(savedInstanceState)  
        setContentView(R.layout.activity_main)  
  
        val fileName = findViewById<EditText>(R.id.editFile)  
        val fileData = findViewById<EditText>(R.id.editData)  
  
        val btnSave = findViewById<Button>(R.id.btnSave)  
        val btnView = findViewById<Button>(R.id.btnView)  
  
        btnSave.setOnClickListener(View.OnClickListener {  
            val file:String = fileName.text.toString()  
            val data:String = fileData.text.toString()  
            val fileOutputStream:FileOutputStream  
            try {  
                fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)  
                fileOutputStream.write(data.toByteArray())  
            } catch (e: FileNotFoundException){  
                e.printStackTrace()  
            }catch (e: NumberFormatException){  
                e.printStackTrace()  
            }catch (e: IOException){  
                e.printStackTrace()  
            }catch (e: Exception){  
                e.printStackTrace()  
            }  
            Toast.makeText(applicationContext,"data save",Toast.LENGTH_LONG).show()  
            fileName.text.clear()  
            fileData.text.clear()  


            // copy from internal to external storage
   
          
 val resolver = this.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "myDoc1")
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents")
        }
        val uri: Uri? = resolver.insert(MediaStore.Files.getContentUri("external"), contentValues)
        Log.d("Uri", "$uri")
        write.setOnClickListener {
            val edt : String = editText.text.toString()
            if (uri != null) {
                resolver.openOutputStream(uri).use {
                    it?.write("Harish Gyanani $edt".toByteArray())
                    it?.close()
                }
            }
        }






  



            
        })  
  
     
      
      
      btnView.setOnClickListener(View.OnClickListener {  
                val filename = fileName.text.toString()  
                if(filename.toString()!="" && filename.toString().trim()!=""){  
                   // var fileInputStream: FileInputStream? = null  
                   var fileInputStream = openFileInput(filename)  
                   var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)  
                   val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)  
                   val stringBuilder: StringBuilder = StringBuilder()  
                   var text: String? = null  
                   while ({ text = bufferedReader.readLine(); text }() != null) {  
                       stringBuilder.append(text)  
                   }  
                   //Displaying data on EditText  
                   fileData.setText(stringBuilder.toString()).toString()  
               }else{  
                   Toast.makeText(applicationContext,"file name cannot be blank",Toast.LENGTH_LONG).show()  
               }  
        })  
  
    }  
}  
