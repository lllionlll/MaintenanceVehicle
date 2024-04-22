package io.maintenancevehicle.views.customer.customer_add

import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.databinding.FragmentCustomerAddBinding
import io.maintenancevehicle.utils.DateFunction


@AndroidEntryPoint
class CustomerAddFragment : BaseFragment<FragmentCustomerAddBinding>(
    FragmentCustomerAddBinding::inflate
) {

    private val customerAddViewModel by viewModels<CustomerAddViewModel>()

    override fun initData() {
        super.initData()
        binding.gender.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderList)
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerAddRoute.backScreen(this)
        }

        binding.birthday.setOnClickListener {
            DateFunction.showDatePicker(requireContext()) { date ->
                binding.birthday.setText(
                    DateFunction.formatDate(
                        date,
                        "dd/MM/yyyy"
                    )
                )
            }
        }
        binding.dayCreate.setOnClickListener {
            DateFunction.showDatePicker(requireContext()) { date ->
                binding.dayCreate.setText(
                    DateFunction.formatDate(
                        date,
                        "dd/MM/yyyy"
                    )
                )
            }
        }
        binding.dayUpdate.setOnClickListener {
            DateFunction.showDatePicker(requireContext()) { date ->
                binding.dayUpdate.setText(
                    DateFunction.formatDate(
                        date,
                        "dd/MM/yyyy"
                    )
                )
            }
        }
        binding.btnSave.setOnClickListener {

        }
        binding.avatar.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
    }

    fun check(): Boolean {
        var checkField = true;
        binding.apply {
            if (name.text.isEmpty()) {
                name.error = "Vui lòng nhập tên!"
                checkField = false
            }
            if (address.text.isEmpty()) {
                address.error = "Vui lòng nhập địa chỉ!"
                checkField = false
            }
            if (phone.text.isEmpty()) {
                phone.error = "Vui lòng điền số điện thoại!"
                checkField = false
            }
            if (birthday.text.isEmpty()) {
                birthday.error = "Vui lòng chọn ngày sinh!"
                return false
            }
            if (dayCreate.text.isEmpty()) {
                dayCreate.error = "Vui lòng chọn ngày tạo!"
                return false
            }
            if (dayUpdate.text.isEmpty()) {
                dayUpdate.error = "Vui lòng chọn ngày cập nhật!"
                return false
            }
        }
        return checkField
    }
}