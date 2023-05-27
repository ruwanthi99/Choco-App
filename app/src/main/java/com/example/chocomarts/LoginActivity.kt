package com.example.chocomarts

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    val auth = FirebaseAuth.getInstance()
    private lateinit var database: DatabaseReference
    val user = auth.currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailAddress = findViewById(R.id.useremail) as EditText;
        val password = findViewById(R.id.userpassword) as EditText;
        val Username = findViewById(R.id.Username) as EditText;
        val ReenterPassword = findViewById(R.id.ReenterPassword) as EditText;


        val needhelptext = findViewById(R.id.needhelptext) as TextView;

        val signUpButton = findViewById(R.id.signupbutton) as Button;
        val logInButton = findViewById(R.id.loginbutton) as Button;


        signUpButton.setOnClickListener {
            val emailaddressofuser = emailAddress.text.toString();
            val userpassword = password.text.toString();

            val Usernamenext = Username.text.toString()


            if (emailaddressofuser.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailaddressofuser)
                    .matches()
            ) {
                Toast.makeText(
                    baseContext, "empty email.",
                    Toast.LENGTH_SHORT
                ).show()
                emailAddress.setError("Empty")


            } else if (Username.text.isEmpty()) {
                Username.setError("Give a username")

            } else if (userpassword.length < 6) {
                password.setError("Password is too short")

            } else {
                auth.createUserWithEmailAndPassword(emailaddressofuser, userpassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {


                            user?.sendEmailVerification()
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {

                                    }
                                }









                            database = FirebaseDatabase.getInstance().getReference("Users")

                            val RegisterUserClass =
                                RegisterUserClassActivity(Usernamenext, emailaddressofuser)

                            database.child(Usernamenext).setValue(RegisterUserClass)
                                .addOnSuccessListener {


                                    val homeIntent = Intent(this, HomeActivity::class.java)
                                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    startActivity(homeIntent)

                                    emailAddress.text.clear()
                                    password.text.clear()
                                    Username.text.clear()
                                    ReenterPassword.text.clear()

                                    Toast.makeText(
                                        baseContext, "Authentication successful.",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        baseContext, "Something went wrong",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }


                        } else {

                            Toast.makeText(
                                baseContext, "You have already registered or error",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
            }


        }
        needhelptext.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Hi !")

            val chooserIntent = Intent.createChooser(intent, "CHOCOMARTS send message")
            startActivity(chooserIntent)

        }
        logInButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(homeIntent)


        }

    }
}