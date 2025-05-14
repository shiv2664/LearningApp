package com.shivam.learningapp.fragments

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.shivam.learningapp.DashboardActivity
import com.shivam.learningapp.IMainActivity
import com.shivam.learningapp.R
import com.shivam.learningapp.api.MyAuthWebService
import com.shivam.learningapp.databinding.FragmentLoginBinding
import com.shivam.learningapp.model.ModelResponse
import es.dmoral.toasty.Toasty

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment : Fragment() {

    private lateinit var binding:FragmentLoginBinding
    val myTag="MyTag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentLoginBinding.inflate(inflater,container,false)

        binding.listener = context as IMainActivity

        textAnim(binding)


        val mWebService = MyAuthWebService.retrofit?.create(MyAuthWebService::class.java)


        binding.forgotPassword.setOnClickListener {

            try {
                val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                Log.d(myTag,""+e.localizedMessage)
            }

            val email1 = binding.email.text.toString().trim()

            if (email1.isEmpty()) {
                activity?.let { it1 -> Toasty.info(it1, "Enter your email to send reset password mail."
                    , Toasty.LENGTH_SHORT, true).show() }
            }else{
                val resetPassword = mWebService?.resetPasswordOtp(email1)

                resetPassword!!.enqueue(object :Callback<ModelResponse?>{
                    override fun onResponse(
                        call: Call<ModelResponse?>,
                        response: Response<ModelResponse?>
                    ) {

                        if (response.isSuccessful) {
                            val modelResponse = response.body()
                            Log.d(myTag, "model response is : ${modelResponse?.message}")
                            activity?.let { it1 -> Toasty.info(it1, "${modelResponse?.message}",
                                Toasty.LENGTH_SHORT, true).show() }

                            if (modelResponse?.message=="Mail Sent successful"){

                                val fragment:Fragment=ForgetPasswordFragment()
                                activity!!
                                    .supportFragmentManager
                                    .beginTransaction()
                                    .addToBackStack("LoginFragment")
                                    .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
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

                    }
                })
            }
        }



        binding.loginButton.setOnClickListener {
            Log.d(myTag, "Button clicked")

            try {
                val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
               Log.d(myTag,""+e.localizedMessage)
            }



            val email1 = binding.email.text.toString().trim()
            val password1 = binding.password.text.toString().trim()

            if(email1.isEmpty() || password1.isEmpty()) {
                activity?.let { it1 -> Toasty.error(it1,"Please fill the required fields",
                    Toasty.LENGTH_LONG,true).show() }
            }else {

                val loginCall = mWebService!!.login(email1, password1)
                binding.lottieanimlogin.visibility=View.VISIBLE
                loginCall.enqueue(object : Callback<ModelResponse?> {
                    override fun onResponse(
                        call: Call<ModelResponse?>,
                        response: Response<ModelResponse?>
                    ) {
                        if (response.isSuccessful) {

                            binding.lottieanimlogin.visibility=View.INVISIBLE
                            val modelResponse = response.body()
                            Log.d(myTag, "model response is : ${modelResponse?.message}")

                            activity?.let { it1 ->
                                Toasty.info(it1, " ${modelResponse?.message}", Toasty.LENGTH_SHORT, true).show() }

                            binding.lottieanimlogin.visibility=View.INVISIBLE
                            if (response.code() == 200){

                                val sharedPref = activity!!.getSharedPreferences("Status",Context.MODE_PRIVATE)
                                val editor = sharedPref.edit()

                                if (modelResponse != null) {
                                    editor.putString("token",modelResponse.data.token)
                                }
                                editor.putString("UserStatus","LoggedIn")
                                editor.apply()

                                val intent = Intent(activity, DashboardActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }else{
                                activity?.let { it1 -> Toasty.error(it1, "Login Failed", Toasty.LENGTH_SHORT, true).show() }
                            }
                        } else {
                            binding.lottieanimlogin.visibility=View.INVISIBLE
                            activity?.let { it1 -> Toasty.error(it1,"invalid email or password ",Toasty.LENGTH_SHORT,true).show() }
                        }
                    }

                    override fun onFailure(call: Call<ModelResponse?>, t: Throwable) {
                        Log.d(myTag, "" + t.localizedMessage)
                        binding.lottieanimlogin.visibility=View.INVISIBLE
                    }
                })
            }


        }

        return binding.root
    }

    private fun textAnim(binding: FragmentLoginBinding){
//        val view=findViewById(R.id.headerTextLogin)
        ObjectAnimator.ofFloat(binding.headerTextLogin, View.ALPHA,0f,1f).apply { duration=1000 }.start()
//        val view2=findViewById<View>(R.id.textView2)
        ObjectAnimator.ofFloat(binding.childTextLogin, View.ALPHA,0f,1f).apply { duration=1000 }.start()

    }

}