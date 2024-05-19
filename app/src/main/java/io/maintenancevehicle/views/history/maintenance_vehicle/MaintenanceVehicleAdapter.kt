package io.maintenancevehicle.views.history.maintenance_vehicle

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import io.maintenancevehicle.bases.BaseAdapter
import io.maintenancevehicle.data.model.History
import io.maintenancevehicle.databinding.ItemHistoryBinding
import io.maintenancevehicle.utils.Constants
import io.maintenancevehicle.utils.DateFunction

class MaintenanceVehicleAdapter(
    val isDelete: Boolean = false,
    val openSelected: () -> Unit = {},
    val updateSelected: (Int) -> Unit = {}
) : BaseAdapter<History, ItemHistoryBinding>() {

    private var isShowSelected = false
    var idDeleteList = mutableListOf<String>()

    inner class VehicleViewHolder(binding: ItemHistoryBinding) : BaseViewHolder(binding) {

        init {
            binding.root.setOnClickListener {
                if (isShowSelected) {
                    if (binding.checkbox.isChecked) {
                        idDeleteList.removeAll { id ->
                            itemList[layoutPosition].id == id
                        }
                    } else {
                        idDeleteList.add(itemList[layoutPosition].id)
                    }
                    updateSelected.invoke(idDeleteList.size)
                    binding.checkbox.isChecked = !binding.checkbox.isChecked
                } else {
                    binding.moreDetail.isVisible = !binding.moreDetail.isVisible
                    binding.timeCreatedAt.isVisible = !binding.moreDetail.isVisible
                }
            }

            if (isDelete) {
                binding.root.setOnLongClickListener {
                    if (!isShowSelected) showSelected()
                    true
                }
            }

            binding.checkbox.setOnClickListener {
                if (!binding.checkbox.isChecked) {
                    idDeleteList.removeAll { vehicleId ->
                        itemList[layoutPosition].id == vehicleId
                    }
                } else {
                    idDeleteList.add(itemList[layoutPosition].id)
                }
                updateSelected.invoke(idDeleteList.size)
            }

        }

        override fun setData(item: History) {
            binding.txtVehicleId.setTextColor(Color.parseColor("#004080"))
            if (item.status == "Bảo trì") {
                binding.txtVehicleId.setTextColor(Color.parseColor("#D50000"))
            } else {
                binding.txtVehicleId.setTextColor(Color.parseColor("#004080"))
            }

            binding.vehicleId.text = ""
            binding.vehicleId.text = item.vehicleId

            binding.status.text = ""
            binding.status.text = item.status

            binding.reason.text = ""
            binding.reason.text = item.reason

            binding.solution.text = ""
            binding.solution.text = item.solution

            binding.supervisor.text = ""
            binding.supervisor.text = item.supervisor

            binding.createdAt.text = ""
            binding.createdAt.text = item.createdAt ?: ""

            binding.timeCreatedAt.text = ""
            binding.timeCreatedAt.text = DateFunction.formatDate(
                dateString = item.createdAt ?: "",
                dateStringFormat = Constants.FORMAT1,
                formatType = Constants.FORMAT2
            )

            binding.note.text = ""
            binding.note.text = item.note

            binding.checkbox.visibility = View.INVISIBLE
            binding.checkbox.visibility = if (isShowSelected) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
            binding.checkbox.isChecked = idDeleteList.any { id ->
                item.id == id
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return VehicleViewHolder(binding)
    }

    fun showSelected() {
        isShowSelected = true
        idDeleteList = mutableListOf()
        openSelected.invoke()
        updateSelected.invoke(0)
        notifyItemRangeChanged(0, itemList.size)
    }

    fun hideSelected() {
        isShowSelected = false
        notifyItemRangeChanged(0, itemList.size)
    }

    fun selectedAll() {
        idDeleteList = itemList.map { vehicle ->
            vehicle.id
        }.toMutableList()
        updateSelected.invoke(idDeleteList.size)
        notifyItemRangeChanged(0, itemList.size)
    }

    fun unSelectedAll() {
        idDeleteList = mutableListOf()
        updateSelected.invoke(0)
        notifyItemRangeChanged(0, itemList.size)
    }

    fun removeVehicle() {
        itemList.removeAll { vehicle ->
            idDeleteList.any { idDelete ->
                idDelete == vehicle.id
            }
        }
        idDeleteList = mutableListOf()
        updateSelected.invoke(0)
        notifyDataSetChanged()
    }

    fun reverseList() {
        setItemList(itemList.reversed().toMutableList())
        notifyDataSetChanged()
    }

}