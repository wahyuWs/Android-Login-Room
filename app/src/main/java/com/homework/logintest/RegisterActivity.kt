package com.homework.logintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.homework.logintest.data.Account
import com.homework.logintest.data.room.AccountDao
import com.homework.logintest.data.room.AccountRoomDatabase
import com.homework.logintest.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RegisterActivity : AppCompatActivity() {

    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    private lateinit var mAccountDao: AccountDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        val db = AccountRoomDatabase.getDatabase(this)
        mAccountDao = db.accountDao()

        activityRegisterBinding.btn2Register.setOnClickListener {
            val username = activityRegisterBinding.edtUsernameRegister.text.toString()
            val email = activityRegisterBinding.edtEmailRegister.text.toString()
            val password = activityRegisterBinding.edtPasswordRegister.text.toString()

            lifecycleScope.launch(Dispatchers.Default) {
                val account = mAccountDao.checkEmail(email)

                this@RegisterActivity.runOnUiThread {
                    userAccount(email, username, password, account)
                }
            }
        }
    }

    private fun userAccount(email: String, username: String, password: String, data: Account?) {
        Log.d("checking email ", data.toString())

        if ((username == "") || (email == "") || (password == "")) {
            Toast.makeText(applicationContext, "can not be empty", Toast.LENGTH_SHORT).show()
        } else if (data?.email == email){
            Toast.makeText(applicationContext, "email already exists", Toast.LENGTH_SHORT).show()
        } else {
            val createAccount = Account(email, username, password)

            executorService.execute { mAccountDao.insertData(createAccount) }
            Toast.makeText(applicationContext, "account created successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}