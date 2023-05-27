package com.example.chocomarts

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdminActivity : AppCompatActivity() {



    private lateinit var database : DatabaseReference
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var imageitem: ImageView
    val userID: String? = auth.currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        val addietm = findViewById(R.id.addchocolates) as Button;
        var itemname = findViewById<TextView>(R.id.chocolatename)
        var theprice = findViewById<TextView>(R.id.chocolateprice)
        imageitem = findViewById<ImageView>(R.id.addtheimage)

        val sendNotification = findViewById<Button>(R.id.sendnotification)
        val notificationMessage = findViewById<EditText>(R.id.notificationmessage)

        imageitem.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)

            }




        }
        sendNotification.setOnClickListener {
            val notiText = notificationMessage.text.toString()
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val channelId = "my_channel_id"
            val channelName = "My Channel Name"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }


            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.kitkat)
                .setContentTitle("ChocoMarts")
                .setContentText(notiText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)


            notificationManager.notify(0, builder.build())


        }

        addietm.setOnClickListener {

            val storeName = itemname.text.toString()
            val storeprice = theprice.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Items")
            val User = UserActivity(storeName , storeprice)
            database.child(storeName).setValue(User).addOnSuccessListener {

                Toast.makeText(baseContext, "Item added",
                    Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(baseContext, "Something went wrong",
                        Toast.LENGTH_SHORT).show()
                }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data

            imageitem.setImageURI(selectedImageUri)
        }
    }


}
