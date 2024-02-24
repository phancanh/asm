package com.example.asm.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asm.R
import com.example.asm.adapter.KeyValueAdapter
import com.example.asm.databinding.FragmentOneBinding
import com.example.asm.databinding.FragmentReviewBinding
import com.example.asm.model.KeyValue
import com.example.asm.vmodel.MainVM

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentReview.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentReview : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: MainVM
    private lateinit var binding: FragmentReviewBinding
    lateinit var mAdapter: KeyValueAdapter
    var mListKeyValue: MutableList<KeyValue> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainVM::class.java]
        binding.vm = viewModel

        mAdapter = KeyValueAdapter(mListKeyValue)
        mAdapter.setOff(true)
        viewModel.mList.observe(requireActivity()) {
            mListKeyValue.clear()
            mListKeyValue.addAll(it)
            mAdapter.notifyDataSetChanged()
        }

        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        layoutManager.stackFromEnd = true
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.setHasFixedSize(true)

        binding.next.setOnClickListener {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        binding.back.setOnClickListener {
            viewModel.setIndex(2)
        }
        binding.lifecycleOwner=requireActivity()
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentReview.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            FragmentReview().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}