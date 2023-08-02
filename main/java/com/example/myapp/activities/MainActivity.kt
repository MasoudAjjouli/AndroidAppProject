package com.example.myapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        intView()
        initSomeView()
    }

    private fun intView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.myVar = ("Welcome Back!")

        binding.login.setOnClickListener {

            binding.emailAddress.addTextChangedListener(object : TextWatcher { //email validation

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })

            if (binding.emailAddress.text.isNullOrBlank() && binding.password.text.isNullOrBlank())
                Toast.makeText(this, "Please Enter your Email and Passowrd", Toast.LENGTH_SHORT)
                    .show()

            if (binding.emailAddress.text.isNotBlank() && binding.password.text.isNullOrBlank()) {

                Toast.makeText(this, "Please Enter your Password", Toast.LENGTH_SHORT).show()
            }

            if (binding.emailAddress.text.isNullOrBlank() && binding.password.text.isNotBlank()) {

                Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show()
            }

            if ((binding.emailAddress.text.contains("admin@gmail.com")) && (binding.password.text.contains(
                    "admin"
                ))
            ) {

                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
            }
            if (binding.emailAddress.text.isNotBlank() && binding.password.text.isNotBlank() && !binding.emailAddress.text.contains(
                    "admin@gmail.com"
                ) && !binding.password.text.contains("admin")     //
            ) {
                Toast.makeText(this, "Email or Password are not coreect", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun initSomeView() {
        binding.emailAddress.doAfterTextChanged {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailAddress.text.toString())
                    .matches()
            )
                binding.login.isEnabled = true
            else {
                binding.login.isEnabled = false
                binding.emailAddress.setError("Invalid Email Address!")
            }
//hi
        }
    }
}

