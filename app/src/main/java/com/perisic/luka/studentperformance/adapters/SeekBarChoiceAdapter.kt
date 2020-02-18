package com.perisic.luka.studentperformance.adapters

import android.view.ViewGroup
import com.perisic.luka.data.model.SeekBarChoice
import com.perisic.luka.studentperformance.base.BaseAdapter
import com.perisic.luka.studentperformance.base.BaseViewHolder
import com.perisic.luka.studentperformance.base.inflate
import com.perisic.luka.studentperformance.databinding.ItemSliderChoiceBinding

class SeekBarChoiceAdapter : BaseAdapter<SeekBarChoice>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeekBarChoiceViewHolder {
        return SeekBarChoiceViewHolder(parent.inflate(ItemSliderChoiceBinding::inflate))
    }

    inner class SeekBarChoiceViewHolder(
        binding: ItemSliderChoiceBinding
    ) : BaseViewHolder<SeekBarChoice>(
        binding,
        {
            binding.seekbarChoice = it
            binding.seekBarChoice.setLabelFormatter { value ->
                (value.toInt()).toString()
            }
            binding.seekBarChoice.setOnChangeListener { _, value ->
                it.selected = value.toInt()
            }
        }
    )

}