package com.example.myapp.fragments



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.R
import com.example.myapp.utils.RecycleViewAdapter
import com.example.myapp.utils.UserClickListener
import com.example.myapp.databinding.YellowFragmentBinding
import com.example.myapp.models.UserInfoElement
import com.example.myapp.viewModel.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class YellowFragment : Fragment(), UserClickListener {
    private lateinit var binding: YellowFragmentBinding
    private lateinit var recyclerAdapter: RecycleViewAdapter
    private val viewModel by lazy { ViewModelProvider(this).get(WelcomeViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate<YellowFragmentBinding>(
            inflater,
            R.layout.yellow_fragment, container, false
        )
        initView()
        initViewModel()
        return binding.root
    }

    private fun initView() {

        val yellowFragment = this
        recyclerAdapter = RecycleViewAdapter(yellowFragment)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RecycleViewAdapter(this)
        recyclerView.adapter = recyclerAdapter
    }

    private fun initViewModel() {
        viewModel.getRecyclerListObserver().observe(viewLifecycleOwner) {
            it?.let {
                recyclerAdapter.setUpdatedData(it)
            } ?: Toast.makeText(activity, "Error in getting data", Toast.LENGTH_SHORT)
        }
        viewModel.makeApiCall()
    }

    override fun onClick(User: UserInfoElement) {
        val action = BaseFragmentDirections.actionBaseFragmentToUserDetails(User)
        findNavController().navigate(action)
    }

    companion object {
        fun newInstance() = YellowFragment()
    }

}
