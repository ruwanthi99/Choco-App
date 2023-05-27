package com.example.chocomarts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val needhelptext = findViewById(R.id.needhelptext) as TextView;
        val useremail = findViewById(R.id.useremail) as EditText;
        val userpassword = findViewById(R.id.userpassword) as EditText;

        val loginbutton = findViewById<Button>(R.id.loginbutton)
        val signupbutton = findViewById<Button>(R.id.signupbutton);

        signupbutton.setOnClickListener {

            val loginActivity = Intent(this , LoginActivity::class.java)
            loginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(loginActivity)

        }
        loginbutton.setOnClickListener {
            var userEmailText = useremail.text.toString()
            var userpasswordText = userpassword.text.toString()

            if(userEmailText.isEmpty()){
                useremail.setError("Need Email address")
            }
            else if(userpasswordText.isEmpty()){
                userpassword.setError("Need your password")
            }
            else{
                auth.signInWithEmailAndPassword(userEmailText, userpasswordText)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Logged in successfully",
                                Toast.LENGTH_SHORT).show()

                            val homeIntent = Intent(this , HomeActivity::class.java)
                            startActivity(homeIntent)
                        } else {
                            Toast.makeText(baseContext, "Something went wrong",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
        needhelptext.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Hi !")

            val chooserIntent = Intent.createChooser(intent, "CHOCOMarts send message")
            startActivity(chooserIntent)

        }
        FirebaseApp.initializeApp(this)


    }
    override fun onStart() {
        super.onStart()
        val currentuser = auth.currentUser;
        if (currentuser != null){
            navigateHome();
        }
    }

    private fun navigateHome() {
        val homeIntent = Intent(this , HomeActivity::class.java)
        startActivity(homeIntent)
    }
}
