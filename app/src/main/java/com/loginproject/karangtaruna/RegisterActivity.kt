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

class RegisterActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var etUsername: EditText? = null
    private var etPassowrd: EditText? = null
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        etUsername = findViewById(R.id.et_username)
        etPassowrd = findViewById(R.id.et_password)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val goLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(goLogin)
            finish()
        }
        val fabRegister: FloatingActionButton = findViewById(R.id.fab_register)
        fabRegister.setOnClickListener(View.OnClickListener {
            val username = etUsername!!.text.toString()
            val password = etPassowrd!!.text.toString()
            if (username == "") {
                Toast.makeText(
                    this@RegisterActivity, "Silahkan isi username Anda",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password == "") {
                Toast.makeText(
                    this@RegisterActivity, "Silahkan isi password Anda",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mAuth!!.createUserWithEmailAndPassword(username, password)
                    .addOnCompleteListener(
                        this@RegisterActivity,
                        object : OnCompleteListener<AuthResult?> {
                            override fun onComplete(@NonNull task: Task<AuthResult?>) {
                                if (task.isSuccessful()) {
                                    // Jika login berhasil//.
                                    val user: FirebaseUser? = mAuth!!.getCurrentUser()
                                    Toast.makeText(
                                        this@RegisterActivity, "Authentication success.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    // Jika gagal akan akan ada tampilan pesan nya//.
                                    Toast.makeText(
                                        this@RegisterActivity, "Authentication failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                // ...
                            }
                        })
            }
        })
    }

    override fun onBackPressed() {
        val goLogin = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(goLogin)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val currentUser: FirebaseUser? = mAuth?.getCurrentUser()
    }
}