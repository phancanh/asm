package com.example.asm

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.asm.adapter.KeyValueAdapter
import com.example.asm.adapter.ViewPagerAdapter
import com.example.asm.databinding.ActivityMainBinding
import com.example.asm.fragment.FragmentOne
import com.example.asm.fragment.FragmentReview
import com.example.asm.fragment.FragmentThree
import com.example.asm.fragment.FragmentTwo
import com.example.asm.model.KeyValue
import com.example.asm.model.ObjRestaurant
import com.example.asm.vmodel.MainVM
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    lateinit var mAdapter: KeyValueAdapter
    var mListStep: MutableList<KeyValue> = mutableListOf()
    private lateinit var viewModel: MainVM
    private lateinit var binding: ActivityMainBinding
    val adapter = ViewPagerAdapter(this@MainActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        initData()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        viewModel = ViewModelProvider(this)[MainVM::class.java]
        binding.vm = viewModel
        binding.lifecycleOwner = this
        adapter.addFragment(FragmentOne.newInstance())
        adapter.addFragment(FragmentTwo.newInstance())
        adapter.addFragment(FragmentThree.newInstance())
        adapter.addFragment(FragmentReview.newInstance())
        binding.viewPager.adapter = adapter

        binding.viewPager.currentItem = 0
        binding.viewPager.isUserInputEnabled = false

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun setOnClick() {
        viewModel.setIndex(binding.viewPager.currentItem)
    }

    private fun initData() {
        viewModel.indexStack.observe(this) {
            binding.viewPager.currentItem = it
        }

        var data = loadJSONFile(context = this, "dishes.json")
        val obj: ObjRestaurant = Gson().fromJson(data, ObjRestaurant::class.java)
        viewModel.setObj(obj)
    }

    private fun loadJSONFile(context: Context, path: String): String? {
        try {
            val inputStream: InputStream = context.assets.open(path)
            val size: Int = inputStream.available()
            val byteArray = ByteArray(size)
            inputStream.read(byteArray)
            inputStream.close()
            return String(byteArray, charset("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}