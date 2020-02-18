package com.perisic.luka.studentperformance.adapters

import android.view.ViewGroup
import com.perisic.luka.data.model.SingleChoice
import com.perisic.luka.studentperformance.base.BaseAdapter
import com.perisic.luka.studentperformance.base.BaseViewHolder
import com.perisic.luka.studentperformance.base.inflate
import com.perisic.luka.studentperformance.databinding.ItemSingleChoiceBinding

class SingleChoiceAdapter : BaseAdapter<SingleChoice>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleChoiceViewHolder {
        return SingleChoiceViewHolder(parent.inflate(ItemSingleChoiceBinding::inflate))
    }

    inner class SingleChoiceViewHolder(
        binding: ItemSingleChoiceBinding
    ) : BaseViewHolder<SingleChoice>(
        binding,
        {
            binding.singleChoice = it
            binding.checkboxChoice.isChecked = it.checked
            binding.checkboxChoice.setOnCheckedChangeListener { _, isChecked ->
                it.checked = isChecked
            }
        }
    )
}