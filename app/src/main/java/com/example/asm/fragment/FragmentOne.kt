package com.example.asm.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.asm.R
import com.example.asm.databinding.FragmentOneBinding
import com.example.asm.model.ObjRestaurant
import com.example.asm.vmodel.MainVM
import com.google.gson.Gson


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentOne.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentOne : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: MainVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentOneBinding
    private val items = ArrayList<String>()
    private var indexSelected: Int = 0
    private var selectedValue: String = ""
    private var mObject: ObjRestaurant? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_one, container, false)
        items.add("---")
        items.add("Breakfast")
        items.add("Lunch")
        items.add("Dinner")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

        binding.selectView.setAdapter(adapter)
        viewModel = ViewModelProvider(requireActivity())[MainVM::class.java]
        binding.selectView.setText(adapter.getItem(indexSelected), false)

        binding.selectView.setOnItemClickListener { _, _, i, _ ->
            indexSelected = i
            selectedValue = adapter.getItem(i)?.lowercase() ?: ""

        }
        viewModel.obj.observe(requireActivity(), Observer {
            selectedValue = adapter.getItem(0)?.lowercase() ?: ""
            mObject = it
        })

        binding.next.setOnClickListener {
            var amout: String = binding.amout.text.toString()

            if (indexSelected == 0) {
                showError("Select an option")
                return@setOnClickListener
            }

            if (amout.isNullOrEmpty()) {
                showError("Please enter medal")
                return@setOnClickListener
            }

            if (amout.toInt() > 10) {
                showError("Please enter medal less than 10")
                return@setOnClickListener
            }


            var mListName: MutableList<String> = mutableListOf()

            mObject?.dishes?.forEach {
                if (it.availableMeals.contains(selectedValue)) {
                    mListName.add(it.restaurant)
                }
            }
            viewModel.setMedal(selectedValue)
            viewModel.setListName(mListName)
            viewModel.setAmout(amout.toInt())
            viewModel.setIndex(1)

        }


        binding.lifecycleOwner = requireActivity()
        return binding.root
    }

    private fun showError(err: String) {
        Toast.makeText(this.context, err, Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance() = FragmentOne().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}