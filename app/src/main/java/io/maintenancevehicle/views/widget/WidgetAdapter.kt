package io.maintenancevehicle.views.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.maintenancevehicle.R
import io.maintenancevehicle.bases.BaseAdapter
import io.maintenancevehicle.data.model.Widget
import io.maintenancevehicle.databinding.ItemWidgetBinding

class WidgetAdapter(
    val onClickWidgetDetail: (Widget) -> Unit
) : BaseAdapter<Widget, ItemWidgetBinding>() {

    inner class WidgetViewHolder(itemWidgetBinding: ItemWidgetBinding) :
        BaseViewHolder(itemWidgetBinding) {

        init {
            binding.root.setOnClickListener {
                onClickWidgetDetail.invoke(itemList[layoutPosition])
            }
        }

        override fun setData(item: Widget) {
            binding.widgetName.text = ""
            binding.widgetName.text = item.name

            Glide.with(binding.widgetImage.context)
                .load(R.mipmap.ic_launcher)
                .into(binding.widgetImage)

            Glide.with(binding.widgetImage.context)
                .load(item.image)
                .into(binding.widgetImage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWidgetBinding.inflate(inflater, parent, false)
        return WidgetViewHolder(binding)
    }

}