package com.example.sokogarden

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //        retrieve the data put on he Extra
        val name = intent.getStringExtra("product_name")
        val cost = intent.getIntExtra("product_cost", 0)
        val product_photo = intent.getStringExtra("product_photo")

//        find textviews inside of makepayment activity amd replace data
        val txtname = findViewById<TextView>(R.id.txtProductName)
        val txtxcost = findViewById<TextView>(R.id.txtProductCost)

        val imgProduct = findViewById<ImageView>(R.id.imgProduct)

//        update the textviews with values passed via intent
        txtname.text = name
        txtxcost.text = "Ksh $cost"

        val imageUrl = "https://kbenkamotho.alwaysdata.net/static/images/$product_photo"

        //Load image using Glide, Load Faster with Glide
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background) // Make sure you have a placeholder image
            .into(imgProduct)
//        find the edittext and buttton by use of id

        val btnpay = findViewById<Button>(R.id.pay)
        val phone = findViewById<EditText>(R.id.phone)

        btnpay.setOnClickListener {
//            specify api endpoint
            val api = "https://kbenkamotho.alwaysdata.net/api/mpesa_payment"

//            get phone number
            val phone = phone.text.toString().trim()

//            create Request param
            val data = RequestParams()

//put data into Requetsparams() object
            data.put("amount", cost)
            data.put("phone", phone)

//            import the APIhelper
            val helper = ApiHelper(applicationContext)

//            acceess post method/function inside helper class

            helper.post(api, data)
        }
    }
}