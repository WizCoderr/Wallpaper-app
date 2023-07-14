package com.flaxstudio.wallpaperapp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.flaxstudio.wallpaperapp.databinding.ActivitySubscriptionBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class SubscriptionActivity : AppCompatActivity() , PaymentResultListener {
    private lateinit var binding : ActivitySubscriptionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val db = FirebaseFirestore.getInstance()
//        val userCollection = db.collection("users")
//        val auth = Firebase.auth
//        val currUser = auth.currentUser!!.uid


        /*
        * To ensure faster loading of the Checkout form,
        * call this method as early as possible in your checkout flow
        * */
        Checkout.preload(applicationContext)
        val co = Checkout()
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime
        co.setKeyID("rzp_test_gDktk0DfOwUVds")

        binding.btnPurchase.setOnClickListener {

            //userCollection.document(currUser).update("startDate" , System.currentTimeMillis())
           // Log.i("TAG" , "currUser : $")
           // Toast.makeText(this, "Subscription Added" , Toast.LENGTH_SHORT).show()
            startPayment()

        }
    }
    private fun startPayment() {
        /*
        *  You need to pass the current activity to let Razorpay create CheckoutActivity
        * */
        val activity: Activity = this
        val co = Checkout()

        try {
            val options = JSONObject()
            options.put("name","Razorpay Corp")
            options.put("description","Demoing Charges")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc");
            options.put("currency","INR");
            options.put("order_id", "order_DBJOWzybf0sJbb");
            options.put("amount","50000")//pass amount in currency subunits

            val retryObj =  JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            val prefill = JSONObject()
            prefill.put("email","gaurav.kumar@example.com")
            prefill.put("contact","9876543210")

            options.put("prefill",prefill)
            co.open(activity,options)
        }catch (e: Exception){
            Toast.makeText(activity,"Error in payment: "+ e.message,Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(s: String?) {
        Log.i("TAG" , "payment was successful with id : $s")
    }

    override fun onPaymentError(e: Int, s: String?) {
        Log.i("TAG" , "payment was fail :$e  and $s")
    }
}