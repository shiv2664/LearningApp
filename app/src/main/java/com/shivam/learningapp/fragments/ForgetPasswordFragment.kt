package com.shivam.learningapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shivam.learningapp.R
import com.shivam.learningapp.api.MyAuthWebService
import com.shivam.learningapp.databinding.FragmentForgetPasswordBinding
import com.shivam.learningapp.model.ModelResponse
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ForgetPasswordFragment : Fragment() {

    private lateinit var binding:FragmentForgetPasswordBinding

    val myTag ="MyTag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentForgetPasswordBinding.inflate(inflater,container,false)

        val mWebService = MyAuthWebService.retrofit?.create(MyAuthWebService::class.java)

        binding.ResetPasswordButton.setOnClickListener {
            val email1 = binding.email.text.toString().trim()
            val password1 = binding.password.text.toString().trim()
            val otp = binding.resetPasswordOtp.text.toString()

            if (email1.isNotEmpty() && password1.isNotEmpty() && otp.isNotEmpty()){

                try {
                    val otpInt=Integer.parseInt(otp)

                    val passwordReset= mWebService?.resetPassword(email1,password1,password1,otpInt)

                    passwordReset!!.enqueue(object : Callback<ModelResponse?>{
                        override fun onResponse(
                            call: Call<ModelResponse?>,
                            response: Response<ModelResponse?>
                        ) {

                            if (response.isSuccessful) {
                                val modelResponse = response.body()

                                Log.d(myTag, "model response is : ${modelResponse?.message}")
                                activity?.let { it1 -> Toasty.info(it1, "${modelResponse?.message}",
                                    Toasty.LENGTH_SHORT, true).show() }

                                if (modelResponse?.message=="Password Updated"){

                                    val fragment:Fragment=LoginFragment()
                                    activity!!
                                        .supportFragmentManager
                                        .beginTransaction()
                                        .setCustomAnimations(R.anim.left_enter,R.anim.right_out)
                                        .replace(R.id.fragmentcontainer, fragment)
                                        .commit()

                                }


                                Log.d(myTag, "response body on successful :" + response.body().toString())
                                Log.d(myTag, "response code on successful :" + response.code().toString())

                            }else{
                                Log.d(myTag, "response body on unSuccessful :" + response.body().toString())
                                Log.d(myTag, "response code on unSuccessful :" + response.code().toString())
                            }



                        }

                        override fun onFailure(call: Call<ModelResponse?>, t: Throwable) {

                            Log.d(myTag, "response code on failure : "+t.localizedMessage)
                        }
                    })


                }catch (e :Exception){
                    activity?.let { it1 -> Toasty.error(it1,"Please enter a valid otp "
                        ,Toasty.LENGTH_SHORT,true).show() }
                }




            }

        }

        return binding.root
    }

}