package com.loginproject.karangtaruna
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    private var stError: EditText? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        val btnRegister: Button = findViewById(R.id.btn_register)
        btnRegister.setOnClickListener {
            val goRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(goRegister)
            finish()
        }
        val fabLogin: FloatingActionButton = findViewById(R.id.fab_login)
        fabLogin.setOnClickListener(View.OnClickListener {
            val username = etUsername!!.text.toString()
            val password = etPassword!!.text.toString()
            val errMessage = stError!!.text.toString()
            if (username == "") {
                Toast.makeText(this@LoginActivity, "Silahkan input username", Toast.LENGTH_SHORT)
                    .show()
            } else if (password == "") {
                Toast.makeText(this@LoginActivity, "Silahkan input password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                mAuth!!.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(
                        this@LoginActivity,
                        object : OnCompleteListener<AuthResult?> {
                            override fun onComplete(@NonNull task: Task<AuthResult?>) {
                                if (task.isSuccessful()) {


                                    val user: FirebaseUser? = mAuth!!.getCurrentUser()
                                    Toast.makeText(
                                        this@LoginActivity, "Authentication success.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this@LoginActivity, "Authentication failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }


                            }
                        })
            }
        })
    }

    override fun onStart() {
        super.onStart()
        // mengecheck user log in.
        val currentUser: FirebaseUser? = mAuth?.getCurrentUser()
    }
}
