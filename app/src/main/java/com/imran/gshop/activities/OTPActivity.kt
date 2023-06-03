package com.imran.gshop.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.ktx.Firebase
import com.imran.gshop.R
import com.imran.gshop.databinding.ActivityLoginBinding
import com.imran.gshop.databinding.ActivityOtpactivityBinding

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnVerify.setOnClickListener{
            if (binding.userOTP.text!!.isEmpty()){
                Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show()
        }else{
            verifyUser(binding.userOTP.text.toString())
        }
        }
    }

    private fun verifyUser(otp: String) {
        val credential = PhoneAuthProvider.getCredential(
            intent.getStringExtra("verificationId")!!, otp)
        signInWithPhoneAuthCredential(credential)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("number",intent.getStringExtra("number")!!)
                    editor.apply()

                    startActivity(Intent(this,MainActivity::class.java))
                    finish()

                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }

                }
    }
}