package com.example.dompekid.base

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T:ViewBinding> : Fragment() {
    private var _binding :T? = null
    protected val binding get() = _binding!!

//    private lateinit var loadingPageFragment:LoadingPageFragment

    abstract fun inflateBinding() : T
    abstract fun setupView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding()
//        loadingPageFragment=LoadingPageFragment()
        setupView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    protected fun openLoadingFragment(loadingPage:FrameLayout) {
        loadingPage.visibility=View.VISIBLE
        setupFragmentTransaction(loadingPage)
    }

    protected fun closeLoadingFragment(loadingPage: FrameLayout){
        requireActivity().supportFragmentManager.popBackStack(
            "loading",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        Handler().postDelayed({
            loadingPage.visibility=View.GONE
        },500)
    }

    private fun setupFragmentTransaction(loadingPage: FrameLayout){
        requireActivity().supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
//            replace(loadingPage.id, loadingPageFragment)
            addToBackStack("loading")
            commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    protected fun makeToast(string:String){
        Toast.makeText(context,string, Toast.LENGTH_SHORT).show()
    }

}