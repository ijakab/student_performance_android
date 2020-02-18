package com.perisic.luka.studentperformance.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    val data = arrayListOf<T>()

    fun setData(dataList: List<T>) {
        data.clear()
        data.addAll(dataList)
        notifyDataSetChanged()
    }

    final override fun getItemCount(): Int = data.size

    final override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) =
        holder.bindItem(data[position])
}

abstract class BasePagedAdapter<T>(
    diffUtil: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, BaseViewHolder<T>>(diffUtil) {

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindItem(getItem(position))
    }

}

open class BaseViewHolder<T>(
    binding: ViewDataBinding,
    private val block: (item: T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(item: T?) {
        item?.let { block(item) }
    }

}

fun <T : ViewDataBinding> ViewGroup.inflate(binder: (inflater: LayoutInflater, root: ViewGroup, attachToRoot: Boolean) -> T): T {

    val inflater = LayoutInflater.from(context)

    return binder(inflater, this, false)

}