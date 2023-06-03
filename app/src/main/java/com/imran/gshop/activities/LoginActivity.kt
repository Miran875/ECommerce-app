package com.imran.gshop.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.imran.gshop.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        binding.btnSignin.setOnClickListener {
            if (binding.userNumber.text!!.isEmpty()){
                Toast.makeText(this, "Please enter number", Toast.LENGTH_SHORT).show()
            }else{
                sendOtp(binding.userNumber.text.toString())
            }
        }
    }
        private lateinit var builder : AlertDialog
            private fun sendOtp(number: String) {
            builder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()


        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$number")       // Phone number to verify
            .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
//***************** ################# *************
    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(e: FirebaseException) {

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            builder.dismiss()
            val intent  = Intent(this@LoginActivity, OTPActivity::class.java)
            intent.putExtra("verificationId",verificationId)
            intent.putExtra("number", binding.userNumber.text.toString())
            startActivity(intent)
        }
    }

}