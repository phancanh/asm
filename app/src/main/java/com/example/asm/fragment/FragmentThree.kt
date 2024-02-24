package com.example.asm.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asm.R
import com.example.asm.adapter.KeyValueAdapter
import com.example.asm.databinding.FragmentThreeBinding
import com.example.asm.model.KeyValue
import com.example.asm.vmodel.MainVM

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentThree.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentThree : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentThreeBinding
    private lateinit var viewModel: MainVM
    lateinit var mAdapter: KeyValueAdapter
    var mListKeyValue: MutableList<KeyValue> = mutableListOf()

    private var items = ArrayList<String>()
    private var indexSelected: Int = 0
    private var amoutTotal: Int = 0
    private var selectedValue: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[MainVM::class.java]
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_three, container, false)



        mAdapter = KeyValueAdapter(mListKeyValue)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        layoutManager.stackFromEnd = true
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.setHasFixedSize(true)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

        viewModel.nameDish.observe(requireActivity()) {
            items.clear()
            binding.selectView.setText("", false)
            mAdapter.insertNull()
            items.addAll(it.toSet())

        }
        viewModel.amout.observe(requireActivity()) {
            amoutTotal = it
        }


        binding.selectView.setAdapter(adapter)

        binding.selectView.setOnItemClickListener { _, _, i, _ ->
            indexSelected = i
            selectedValue = adapter.getItem(i) ?: ""

        }
        binding.next.setOnClickListener {
            var totalDishes = 0
            for (dish in mAdapter.getListItem()) {
                totalDishes += dish.key
            }


            val maxDishes = totalDishes >= amoutTotal
            val max = totalDishes <= 10

            if (!max || !maxDishes) {
                Toast.makeText(
                    context,
                    "The total number of dishes (i.e Number of dishes  respective serving) should be greater or equal to the number of people ",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            viewModel.setListTotal(mAdapter.getListItem())
            viewModel.setIndex(3)

        }
        binding.add.setOnClickListener {
            var amout: String = binding.amout.text.toString()

            if (selectedValue.isNullOrEmpty()) {
                showError("Select an option")
                return@setOnClickListener
            }
            val total = (amout.toIntOrNull() ?: 1)
            /*    if (total >= amoutTotal) {
                    showError("Please enter medal less than $amoutTotal")
                    return@setOnClickListener
                }*/
            mAdapter.getListItem().forEach {
                if (it.value == selectedValue) {
                    Toast.makeText(
                        context,
                        "The total number of dishes (i.e Number of dishes  respective serving) should be greater or equal to the number of people ",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }


            }


            mAdapter.clickItem(KeyValue(total, selectedValue))
            binding.amout.setText("")
        }

        binding.back.setOnClickListener {
            viewModel.setIndex(1)
        }
        binding.lifecycleOwner = requireActivity()
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentThree.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = FragmentThree().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

    private fun showError(err: String) {
        Toast.makeText(this.context, err, Toast.LENGTH_SHORT).show()
    }

}