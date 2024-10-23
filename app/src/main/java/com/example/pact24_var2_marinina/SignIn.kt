package com.example.pact24_var2_marinina

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class SignIn : AppCompatActivity() {
    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        login = findViewById<EditText>(R.id.login)
        password = findViewById(R.id.password)
        pref = getPreferences(MODE_PRIVATE)


        val savedLogin = pref.getString("login", "")
        val savedPassword = pref.getString("password", "")


        login.setText(savedLogin)
        password.setText(savedPassword)
    }

    fun nextekr(view: View) {
        if (login.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()) {
            if (login.text.length < 8 || password.text.length < 8) {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage("Логин и пароль должны содержать не менее 8 символов")
                    .setPositiveButton("Продолжить", null)
                    .create()
                    .show()
                return
            }

            val stlog = pref.getString("login", "")
            val stpas = pref.getString("password", "")

            if (login.text.toString() == stlog && password.text.toString() == stpas) {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Вход")
                    .setMessage("Успешно")
                    .setPositiveButton("Продолжить", null)
                    .create()
                    .show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage("Неверный логин или пароль")
                    .setPositiveButton("Повторить", null)
                    .create()
                    .show()
            }
        } else {
            val alert = AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("Введите данные для входа")
                .setPositiveButton("Продолжить", null)
                .create()
                .show()
        }
    }

    fun reg(view: View) {
        if (login.text.toString().isNotEmpty() && password.text.toString().isNotEmpty()) {
            if (login.text.length < 8 || password.text.length < 8) {
                val alert = AlertDialog.Builder(this)
                    .setTitle("Ошибка")
                    .setMessage("Логин и пароль должны содержать не менее 8 символов")
                    .setPositiveButton("Продолжить", null)
                    .create()
                    .show()
                return
            }

            val ed = pref.edit()
            ed.putString("login", login.text.toString())
            ed.putString("password", password.text.toString())
            ed.apply()

            val alert = AlertDialog.Builder(this)
                .setTitle("Регистрация")
                .setMessage("Регистрация успешна!")
                .setPositiveButton("Продолжить", null)
                .create()
                .show()
        } else {
            val alert = AlertDialog.Builder(this)
                .setTitle("Ошибка")
                .setMessage("Введите логин и пароль для регистрации")
                .setPositiveButton("Продолжить", null)
                .create()
                .show()
        }
    }
}