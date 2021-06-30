package com.example.sendanonymously

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.hbb20.CountryCodePicker
import com.hbb20.CountryCodePicker.PhoneNumberValidityChangeListener
import java.net.URLEncoder


class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        requestWindowFeature(Window.FEATURE_NO_TITLE)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

//      for making the display full screen
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

//      for the gradient animations
//        val animDrawable = findViewById(R.id.number).background as AnimationDrawable
//        animDrawable.setEnterFadeDuration(10)
//        animDrawable.setExitFadeDuration(5000)
//        animDrawable.start()

//for the whatsapp api
        val number : EditText = findViewById(R.id.number)
        val msg : EditText = findViewById(R.id.msg)

        val btn : Button = findViewById(R.id.sendBtn)

        val ccp: CountryCodePicker = findViewById(R.id.ccp);

        ccp.registerCarrierNumberEditText(number)
        ccp.fullNumberWithPlus;


        btn.setOnClickListener{
            if(number.text.isNotEmpty() && msg.text.isNotEmpty()){

                val packageManager : PackageManager = packageManager
                val intent = Intent(Intent.ACTION_VIEW)
                val url = "https://api.whatsapp.com/send?phone=" + ccp.fullNumberWithPlus + "&text=" + URLEncoder.encode(
                    msg.text.toString(),
                    "UTF-8"
                )

                intent.setPackage("com.whatsapp")
                intent.data = Uri.parse(url)

                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
            }
            else if(number.text.isNotEmpty()){
                Toast.makeText(this, "Please Enter Your Message.", Toast.LENGTH_LONG).show()
            }
            else if(msg.text.isNotEmpty()){
                Toast.makeText(
                    this,
                    "Please Enter Receiver's Number Along With Country Code.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if(number.text.isEmpty() && msg.text.isEmpty()){
                Toast.makeText(
                    this,
                    "Number and Message Field Cannot Be Set Empty.",
                    Toast.LENGTH_LONG
                ).show()
            }
            msg.text.clear()
            number.text.clear()
        }

    }

}
