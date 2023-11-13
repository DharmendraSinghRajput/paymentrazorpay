package com.example.paymentrazorpay

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.paymentrazorpay.databinding.ActivityMainBinding
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PaymentResultListener {
    val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        mBinding.apply {
            addMoney.setOnClickListener {
                startPayment(balanceEdit.text.toString().trim().toInt())
/*
                if (balanceEdit.text.isNullOrBlank()){
                }*/

            }
        }
        Checkout.preload(this@MainActivity)

    }

    private fun startPayment(Amount: Int) {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_wx9NLBgDn9arLd")
        try {
            val jsonObject = JSONObject()
            jsonObject.put("name", "Razorpay")
            jsonObject.put("description", "Payment Gateway Demo")
            jsonObject.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            jsonObject.put("theme.color", "#50B6F4")
            jsonObject.put("currency", "INR")
            jsonObject.put("amount", Amount * 100)
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            jsonObject.put("retry", retryObj)
            checkout.open(this@MainActivity, jsonObject)
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onPaymentSuccess(p0: String?) {
        try {
            mBinding.paymentStatus.text=p0
            mBinding.paymentStatus.setTextColor(Color.GREEN)
        } catch (e: Exception) {
            Toast.makeText(this@MainActivity, "eRROR : $e", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onPaymentError(p0: Int, p1: String?) {
        mBinding.paymentStatus.text=p1
        mBinding.paymentStatus.setTextColor(Color.RED)
        Toast.makeText(this@MainActivity, "Payment Unsuccessful$p1", Toast.LENGTH_LONG).show()

    }
}