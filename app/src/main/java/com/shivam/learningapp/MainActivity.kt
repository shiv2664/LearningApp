package com.shivam.learningapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.shivam.learningapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val myTag:String="MyTag"
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.title = "Dashboard"




        // background color
//        chart.setBackgroundColor(Color.WHITE)
//        chart.description.isEnabled = false
//        chart.setTouchEnabled(true)
//        chart.setDrawGridBackground(false)
//        chart.isDragEnabled = true
//        chart.setScaleEnabled(true)
//        chart.isScaleXEnabled = true;
//        chart.isScaleYEnabled = true;
//        chart.setPinchZoom(true)
//        val xAxis: XAxis = chart.xAxis
//        val yAxis: YAxis = chart.axisLeft
//        // disable dual axis (only use LEFT axis)
//        chart.axisRight.isEnabled = false
//        chart.xAxis.position=XAxis.XAxisPosition.BOTTOM
//        // draw limit lines behind data instead of on top
//            yAxis.setDrawLimitLinesBehindData(true)
//            xAxis.setDrawLimitLinesBehindData(true)
//        // add data
//        val seekBarX :SeekBar?=null
//        val seekBarY:SeekBar?=null
//        seekBarX?.progress = 45
//        seekBarY?.progress = 180
//        setData(10, 30f)
//        chart.animateX(1000)
//        val l = chart.legend
//        l.form = Legend.LegendForm.LINE



    }


//    private fun setData(count: Int, range: Float) {
//        val values = ArrayList<Entry>()
//        val values2 = ArrayList<Entry>()
//
//        values2.add(Entry(0F, 5F))
//        values2.add(Entry(2F, 10F))
//        values2.add(Entry(4F, 25F))
//        values2.add(Entry(5F, 15F))
//        values2.add(Entry(7F, 17F))
//        values2.add(Entry(10F, 5F))
//        values2.add(Entry(12F, 7F))
//        values2.add(Entry(14F, 12F))
//        values2.add(Entry(16F, 2F))
//        values2.add(Entry(20F, 8F))
//
//        val set2: LineDataSet = LineDataSet(values2, "DataSet 2")
////        set2.enableDashedLine(10f, 5f, 0f)
//        set2.mode = LineDataSet.Mode.CUBIC_BEZIER;
//        set2.color = Color.BLACK
//        set2.setCircleColor(Color.BLACK)
//        // line thickness and point size
//        set2.lineWidth = 2f
//        set2.circleRadius = 4f
//        // draw points as solid circles
//        set2.setDrawCircleHole(false)
//        // customize legend entry
//        set2.formLineWidth = 1f
//        set2.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
//        set2.formSize = 15f
//        // text size of values
//        set2.valueTextSize = 20f
//        // draw selection line as dashed
//        set2.enableDashedHighlightLine(10f, 5f, 0f)
//        // set the filled area
//        set2.setDrawFilled(true)
//        set2.fillFormatter =
//                IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }
//        val drawable = ContextCompat.getDrawable(this, R.drawable.fade_red)
//        set2.fillDrawable = drawable
//        val dataSets = ArrayList<ILineDataSet>()
//        dataSets.add(set2) // add the data sets
//        // create a data object with the data sets
//        val data = LineData(dataSets)
//        // set data
//        chart.data = data
//
//
//
////        for (i in 0 until count) {
////            val `val` = (Math.random() * range).toFloat()-5
////            values.add(Entry(i.toFloat(), `val`))
////        }
////
////        val set1: LineDataSet
////        if (chart.data != null &&
////            chart.data.dataSetCount > 0
////        ) {
////            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
////            set1.values = values
////            set1.notifyDataSetChanged()
////            chart.data.notifyDataChanged()
////            chart.notifyDataSetChanged()
////        }
//    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val findMenuItems = menuInflater
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

//        when (item.itemId) {
//            R.id.LogOut -> {
//
//                val mWebService2= MyWebService.retrofit?.create(MyWebService::class.java)
//                val logout=mWebService2?.logOutUser()
//                Log.d(myTag,"Clicked")
//
//                logout!!.enqueue(object: Callback<Any?> {
//                    override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
//
//                        if (response.isSuccessful) {
//                            Log.d(myTag, "successful " + response.code().toString())
//
//                            val modelResponse: ModelResponse = response.body() as ModelResponse
//
//                            if (modelResponse.message == "Logout Successful") {
//                                val sharedPref =
//                                    getSharedPreferences("Status", Context.MODE_PRIVATE)
//                                val editor = sharedPref.edit()
////                                editor.putInt(getString(R.string.saved_high_score_key),newHighScore)
//                                editor.putString("UserStatus", "LoggedOut")
//                                editor.apply()
//                                Toasty.info(
//                                    this@MainActivity, "Logged Out", Toasty.LENGTH_SHORT, true
//                                ).show()
//                                val intent =
//                                    Intent(this@MainActivity, RegistrationActivity::class.java)
//                                startActivity(intent)
//                                finish()
////
//                            } else {
//                                Log.d(myTag, "error " + response.code().toString())
//                            }
//
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Any?>, t: Throwable) {
//                       Log.d(myTag,""+t.localizedMessage)
//                    }
//                })
//            }
//
//        }

        return super.onOptionsItemSelected(item)
    }
}




