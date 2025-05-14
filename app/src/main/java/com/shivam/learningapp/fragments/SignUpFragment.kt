package com.shivam.learningapp.fragments

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.shivam.learningapp.DashboardActivity
import com.shivam.learningapp.api.MyAuthWebService
import com.shivam.learningapp.databinding.FragmentSignUpBinding
import com.shivam.learningapp.model.ModelResponse
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SignUpFragment : Fragment() {

    private lateinit var binding:FragmentSignUpBinding
    val myTag:String="MyTag"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentSignUpBinding.inflate(inflater,container,false)
        
        val mWebService=MyAuthWebService.retrofit?.create(MyAuthWebService::class.java)

        textAnim(binding)

        binding.GetOTP.setOnClickListener {

            try {
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                Log.d(myTag,""+e.localizedMessage)
            }

            Log.d(myTag,"Button clicked ")
            val emailId=binding.emailSignUp.text.toString().trim()

            if (emailId.isNotEmpty()) {

                val getOtp = mWebService?.getOtp(emailId)

                getOtp!!.enqueue(object : Callback<ModelResponse?> {
                    override fun onResponse(call: Call<ModelResponse?>, response: Response<ModelResponse?>) {

                        if (response.isSuccessful) {
                            Log.d(myTag, " response body OTP" + response.body().toString())
                            Log.d(myTag, " successful otp code : " + response.code().toString())
                            Toast.makeText(
                                activity,
                                "OTP sent please check your Email",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (!response.isSuccessful) {
                            Log.d(
                                myTag,
                                " unsuccessful response otp: " + response.body().toString()
                            )
                            Log.d(myTag, " unsuccessful otp : " + response.code().toString())
                        }

                    }

                    override fun onFailure(call: Call<ModelResponse?>, t: Throwable) {
                        Log.d(myTag, "Failure " + t.localizedMessage)
                    }
                })
            }else{
                activity?.let { it1 -> Toasty.error(it1,"Enter your email",Toasty.LENGTH_SHORT).show() }
            }

        }

        binding.signUpButton.setOnClickListener {

            val name=binding.username.text.toString().trim()
            val email=binding.emailSignUp.text.toString().trim()
            val password=binding.password.text.toString().trim()
            val otp=binding.OTP.text.toString().trim()

            try {
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
            } catch (e: Exception) {
                Log.d(myTag,""+e.localizedMessage)
            }

            Log.d(myTag,"\n$name\n$email\n$password\n$otp\n")
            if ( name.isNotEmpty()  && email.isNotEmpty()  && password.isNotEmpty() && otp.isNotEmpty()) {

                try {
                    val intOtp = Integer.parseInt(otp)

                    Log.d(myTag, "" + intOtp)

                    val register = mWebService?.register(name, email, password, password, intOtp)
                    binding.lottieanimsignup.visibility=View.VISIBLE

                    register!!.enqueue(object : Callback<ModelResponse?> {
                        override fun onResponse(
                            call: Call<ModelResponse?>,
                            response: Response<ModelResponse?>
                        ) {
                            binding.lottieanimsignup.visibility=View.INVISIBLE
                            if (response.isSuccessful) {
                                Log.d(myTag, " " + response.code().toString())
                                val modelResponse=response.body()
                                Log.d(myTag,"model Response is : ${modelResponse?.message}")
//                                Toast.makeText(activity,"Signup Complete",Toast.LENGTH_SHORT).show()
                                activity?.let { it1 -> Toasty.info(it1, "Signup Complete"
                                    , Toasty.LENGTH_SHORT, true).show() }

                                if(response.code()==200){

                                    val sharedPref = activity!!.getSharedPreferences("Status",Context.MODE_PRIVATE)
                                    val editor = sharedPref.edit()
//                                editor.putInt(getString(R.string.saved_high_score_key),newHighScore)
                                    editor.putString("UserStatus","LoggedIn")
                                    editor.apply()



                                    val intent = Intent(activity, DashboardActivity::class.java)
                                    startActivity(intent)
                                    activity?.finish()
                                }

                            }else{
                                Log.d(myTag," unsuccessful response : "+response.code().toString())
                            }

                        }
                        override fun onFailure(call: Call<ModelResponse?>, t: Throwable) {
                            Log.d(myTag, " " + t.localizedMessage)
                            binding.lottieanimsignup.visibility=View.INVISIBLE

                        }
                    })

                }catch (e :Exception){
                    activity?.let { it1 -> Toasty.error(it1,"Please enter a valid otp "
                        , Toasty.LENGTH_SHORT,true).show() }
                }


            } else{
                Toast.makeText(requireActivity(),"Please fill the required fields",Toast.LENGTH_SHORT).show()
            }


        }

        return binding.root
    }

    private fun textAnim(binding: FragmentSignUpBinding){
//        val view=findViewById(R.id.headerTextLogin)
        ObjectAnimator.ofFloat(binding.signUpHeaderText, View.ALPHA,0f,1f).apply { duration=1000 }.start()
    }

}
