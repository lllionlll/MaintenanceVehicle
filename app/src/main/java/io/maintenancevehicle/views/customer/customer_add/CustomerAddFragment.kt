package io.maintenancevehicle.views.customer.customer_add

import android.net.Uri
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.databinding.FragmentCustomerAddBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import java.util.Date

@AndroidEntryPoint
class CustomerAddFragment : BaseFragment<FragmentCustomerAddBinding>(
    FragmentCustomerAddBinding::inflate
) {
    private val customerAddViewModel by viewModels<CustomerAddViewModel>()

    private val genderList = mutableListOf("Nam", "Nữ")
    private var uri: Uri? = null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { galleryUri ->
        if (galleryUri != null) {
            uri = galleryUri
            binding.avatar.setImageURI(galleryUri)
        }
    }

    override fun initData() {
        super.initData()

        binding.gender.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, genderList)
    }

    override fun initView() {
        super.initView()

        Glide.with(requireContext())
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRNuiBY5AGqRjC890IW9MQ5_p5NYSysbFSBfs8LIcl8DSYx7sTngU8xpzyHuwitNfUmV4&usqp=CAU")
            .into(binding.avatar)
    }

    override fun observerData() {
        super.observerData()
        customerAddViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }
        customerAddViewModel.isAddSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                CustomerAddRoute.backScreen(this)
            }
        }
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
        binding.btnSave.setOnClickListener {
            val customer = Customer(
                birthday = binding.birthday.text.toString(),
                name = binding.name.text.toString(),
                createdAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy"
                ),
                updatedAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy"
                ),
                gender = genderList[binding.gender.selectedItemPosition],
                homeTown = binding.address.text.toString(),
                phoneNumber = binding.phone.text.toString(),
                note = binding.note.text.toString()
            )
            customerAddViewModel.addCustomer(
                requireContext(),
                uri,
                customer
            )
        }

        binding.avatar.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
    }

}