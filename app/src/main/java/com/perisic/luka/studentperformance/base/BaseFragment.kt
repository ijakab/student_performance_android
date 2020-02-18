package com.perisic.luka.studentperformance.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.perisic.luka.data.remote.util.BaseResponse
import com.perisic.luka.studentperformance.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseFragment(
    @LayoutRes private val layoutRes: Int
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    protected fun <V, T : BaseResponse<V>> LiveData<T>.observe(
        success: (V) -> Unit,
        error: (T) -> Unit
    ) {
        this.observe(this@BaseFragment, Observer {
            when (it.status) {
                BaseResponse.STATUS.SUCCESS -> it.data?.let { data ->
                    success(data)
                }
                BaseResponse.STATUS.LOADING -> {
                    (requireActivity() as MainActivity).progressBar.visibility = View.VISIBLE
                    return@Observer
                }
                else -> error(it)
            }
            (requireActivity() as MainActivity).progressBar.visibility = View.INVISIBLE
        })
    }

    protected fun <T : BaseResponse.STATUS> LiveData<T>.observeStatus(
        success: () -> Unit = {},
        error: () -> Unit = {}
    ) {
        this.observe(this@BaseFragment, Observer {
            when (it) {
                BaseResponse.STATUS.SUCCESS -> success()
                BaseResponse.STATUS.LOADING -> {
                    (requireActivity() as MainActivity).progressBar.visibility = View.VISIBLE
                    return@Observer
                }
                else -> error()
            }
            (requireActivity() as MainActivity).progressBar.visibility = View.INVISIBLE
        })
    }

}