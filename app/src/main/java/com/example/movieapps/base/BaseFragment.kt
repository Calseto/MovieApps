package com.example.dompekid.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.example.movieapps.base.ErrorPageFragment
import com.example.movieapps.base.LoadingPageFragment

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!

    private lateinit var loadingPageFragment: LoadingPageFragment
    private lateinit var errorPageFragment: ErrorPageFragment

    private lateinit var errorFrameLayout: FrameLayout
    private lateinit var loadingFrameLayout: FrameLayout



    abstract fun inflateBinding(): T
    abstract fun setupView()
    abstract fun getStatusFramelayout():Pair<FrameLayout,FrameLayout>

    fun reload(){
        setupView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding()
        createUtilitiesFragment()
        setupView()
        setupStatusFramelayout()

        return binding.root
    }

    private fun setupStatusFramelayout(){
        val (loading,error) = getStatusFramelayout()
        loadingFrameLayout=loading
        errorFrameLayout=error
        setupErrorFragmentTransaction()
    }

    private fun createUtilitiesFragment() {
        loadingPageFragment = LoadingPageFragment()
        errorPageFragment = ErrorPageFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    protected fun openLoadingFragment() {
        loadingFrameLayout.visibility = View.VISIBLE
        setupLoadingFragmentTransaction()
    }

    protected fun closeLoadingFragment() {
        requireActivity().supportFragmentManager.popBackStack(
            "loading",
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        Handler().postDelayed({
            loadingFrameLayout.visibility = View.GONE
        }, 200)
    }

    private fun setupLoadingFragmentTransaction() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            replace(loadingFrameLayout.id, loadingPageFragment)
            addToBackStack("loading")
            commit()
        }
    }

    protected fun openErrorFragment(errorMessage:String) {
        errorPageFragment.setErrorMessage(errorMessage.uppercase())
        errorFrameLayout.visibility = View.VISIBLE
    }

    protected fun closeErrorFragment() {
        Handler().postDelayed({
            errorFrameLayout.visibility = View.GONE
        }, 300)
    }

    private fun setupErrorFragmentTransaction() {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(errorFrameLayout.id, errorPageFragment)
            commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun makeToast(string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

}