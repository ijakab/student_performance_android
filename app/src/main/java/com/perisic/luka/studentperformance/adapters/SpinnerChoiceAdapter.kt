package com.perisic.luka.studentperformance.adapters

import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.perisic.luka.data.model.SpinnerChoice
import com.perisic.luka.studentperformance.R
import com.perisic.luka.studentperformance.base.BaseAdapter
import com.perisic.luka.studentperformance.base.BaseViewHolder
import com.perisic.luka.studentperformance.base.inflate
import com.perisic.luka.studentperformance.databinding.ItemSpinnerChoiceBinding

class SpinnerChoiceAdapter : BaseAdapter<SpinnerChoice>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinnerChoiceViewHolder =
        SpinnerChoiceViewHolder(
            parent.inflate(ItemSpinnerChoiceBinding::inflate)
        )

    class SpinnerChoiceViewHolder(
        binding: ItemSpinnerChoiceBinding
    ) : BaseViewHolder<SpinnerChoice>(
        binding,
        {
            binding.spinnerChoice = it
            val spinnerAdapter = ArrayAdapter<String>(
                binding.root.context,
                R.layout.item_dropdown_menu,
                it.choices
            )
            binding.spinnerChoiceNew.setAdapter(spinnerAdapter)
            if (it.selected != -1) it.selected?.let { selected ->
                binding.spinnerChoiceNew.setText(
                    spinnerAdapter.getItem(selected),
                    false
                )
            }
            binding.spinnerChoiceNew.setOnItemClickListener { parent, view, position, id ->
                println(it.choices[position])
                it.selected = position
            }
        })
}