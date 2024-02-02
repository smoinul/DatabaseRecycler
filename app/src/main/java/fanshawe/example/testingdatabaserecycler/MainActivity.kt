package fanshawe.example.testingdatabaserecycler

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {

    // Set up the RecyclerView
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerView.Adapter<*>
    lateinit var recyclerViewManager: RecyclerView.LayoutManager

    // DAO
    lateinit var personArrayList: ArrayList<Person>

    // Database
    private var db:DBAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewPeople)
        recyclerViewManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = recyclerViewManager
        recyclerView.setHasFixedSize(true)

        // instantiate the personArrayList
        personArrayList = ArrayList<Person>()
        // configure the person arrayList from the database
        try {
            val destinationPath = "data/data/$packageName/databases"
            val f = File(destinationPath)
            if(!f.exists()) {
                f.mkdirs()
                //copy the db from the assets folder
                CopyDB( baseContext.assets.open("mydb"), FileOutputStream("$destinationPath/MyDB"))
            }
        }catch(e: FileNotFoundException) {
            e.printStackTrace()
        }catch (e:IOException){
            e.printStackTrace()
        }catch (e:Exception){
            e.printStackTrace()
        }

        db = DBAdapter(this)
        db!!.open()
        // read all the items from the database file
        val c: Cursor? = db!!.allContacts
        if(c!!.moveToFirst()) {
            do {
                personArrayList.add(
                    Person(c.getInt(0),c.getString(1),c.getString(2),c.getInt(3))
                )
            } while (c.moveToNext())
        }
        db!!.close()
        recyclerAdapter = RecyclerAdapter(personArrayList)
        recyclerView.adapter = recyclerAdapter
    }


    // function that copies over the database from the assets folder
    // copy database from assets to phone
    // items in the assets folder preserve filename
    @Throws(IOException::class)
    fun CopyDB(inputStream: InputStream, outputStream: OutputStream) {
        //Copy one byte at a time
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) {
            outputStream.write(buffer, 0, length)
        }
        inputStream.close()
        outputStream.close()
    }


    fun onButtonClick(view: View) {
        var textViewDisplay = findViewById<TextView>(R.id.textViewInfo)
        when(view.id)
        {
            R.id.buttonTotal-> {
                textViewDisplay.text = "The total items in the RecyclerView is: " + recyclerView.adapter?.itemCount.toString()
            }
            R.id.buttonFirst-> {
                textViewDisplay.text = "The first person is: " + personArrayList[0].toString()
            }
            R.id.buttonSecond-> {
                // exercise change this button functionality to delete the last item in the database and then have it removed in the recyclerView too!
                // and then the other button to add the last person back into the database and recyclerView
                textViewDisplay.text = "The first person is: " + personArrayList[1].toString()
            }
        }
    }




}