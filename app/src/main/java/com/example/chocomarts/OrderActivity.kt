package com.example.chocomarts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val name = findViewById(R.id.chocolatename) as TextView;
        val price = findViewById(R.id.chocolateprice) as TextView;

        val userplaceorderbtn = findViewById<Button>(R.id.placeorderbtn)

        userplaceorderbtn.setOnClickListener {
            val homeIntent = Intent(this , HomeActivity::class.java)
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(homeIntent)
            Toast.makeText(baseContext,  "Order is placed",
                Toast.LENGTH_SHORT).show()
        }


        val receivedValue = intent.getStringExtra("nameOfElement")
        val chocolateprice = intent.getStringExtra("chocolateprice")


        name.text = receivedValue
        price.text = chocolateprice



    }
}