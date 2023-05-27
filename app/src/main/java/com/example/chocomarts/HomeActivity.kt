package com.example.chocomarts

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        val orderNowBtn = findViewById(R.id.AddToCart) as Button;
        val name = findViewById(R.id.chocolatename) as TextView;
        val realPrice = findViewById(R.id.RealPrice) as TextView;




        orderNowBtn.setOnClickListener {

            val namenext = name.text.toString();
            val pricenext = realPrice.text.toString()


            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("nameOfChocolate", namenext)
            intent.putExtra("chocolateprice", pricenext)


            startActivity(intent)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.adminpanel -> {

                val intent = Intent(this, AdminActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

                true
            }
            R.id.logout -> {

                auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)



                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}




