package com.homework.logintest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.homework.logintest.data.Account
import com.homework.logintest.data.room.AccountDao
import com.homework.logintest.data.room.AccountRoomDatabase
import com.homework.logintest.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    private lateinit var mAccountDao: AccountDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        val db = AccountRoomDatabase.getDatabase(this)
        mAccountDao = db.accountDao()

        activityLoginBinding.btnLogin.setOnClickListener {
            val email = activityLoginBinding.edtEmail.text.toString()
            val password = activityLoginBinding.edtPassword.text.toString()

            lifecycleScope.launch(Dispatchers.Default) {
                val account = mAccountDao.authenticate(email, password)

                this@LoginActivity.runOnUiThread {
                    checkUser(email, password, account)
                }
            }
        }

        activityLoginBinding.btnRegister.setOnClickListener {
            val moveToRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveToRegister)
        }
    }

    private fun checkUser(email: String, password: String, data: Account?) {
        Log.d("user account ", data.toString())

        if ((email == "") || (password == "")) {
            Toast.makeText(applicationContext, "can not be empty", Toast.LENGTH_SHORT).show()
        } else {

            if ((data?.email == email) && (data.password == password)) {
                Toast.makeText(applicationContext, "login successful", Toast.LENGTH_SHORT).show()

                val moveToHome = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(moveToHome)
                finish()
            } else {
                Toast.makeText(applicationContext, "Failed to log in , please try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
}