package io.maintenancevehicle.views.customer.customer_edit

import android.R
import android.net.Uri
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import io.maintenancevehicle.bases.BaseFragment
import io.maintenancevehicle.data.model.Customer
import io.maintenancevehicle.databinding.FragmentCustomerEditBinding
import io.maintenancevehicle.utils.DateFunction
import io.maintenancevehicle.utils.LoadingDialog
import io.maintenancevehicle.views.customer.customer_detail.CustomerDetailFragmentArgs
import java.util.Date

@AndroidEntryPoint
class CustomerEditFragment : BaseFragment<FragmentCustomerEditBinding>(
    FragmentCustomerEditBinding::inflate
) {
    private val customerEditViewModel by viewModels<CustomerEditViewModel>()
    private val safeVarargs by navArgs<CustomerDetailFragmentArgs>()

    private var customer = Customer()

    private val genderList = mutableListOf("Nam", "Nữ", "Khác")
    private var uri: Uri? = null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { galleryUri ->
        if (galleryUri != null) {
            uri = galleryUri
            binding.avatar.setImageURI(galleryUri)
        }
    }

    override fun initView() {
        super.initView()

        binding.gender.adapter =
            ArrayAdapter(requireContext(), R.layout.simple_spinner_item, genderList)

        customerEditViewModel.getCustomerDetail(
            customerId = safeVarargs.customerId
        )
    }

    override fun handleEvent() {
        super.handleEvent()
        binding.btnBack.setOnClickListener {
            CustomerEditRoute.backScreen(this)
        }

        binding.avatar.setOnClickListener {
            galleryLauncher.launch("image/*")
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
        binding.btnEdit.setOnClickListener {
            val customer = Customer(
                customerId = this.customer.customerId,
                imageUrl = (uri ?: this.customer.imageUrl).toString(),
                birthday = binding.birthday.text.toString(),
                name = binding.name.text.toString(),
                createdAt = this.customer.createdAt,
                updatedAt = DateFunction.formatDate(
                    Date(),
                    "dd/MM/yyyy HH:mm"
                ),
                gender = genderList[binding.gender.selectedItemPosition],
                homeTown = binding.address.text.toString(),
                phoneNumber = binding.phone.text.toString(),
                note = binding.note.text.toString()
            )
            customerEditViewModel.editCustomer(
                requireContext(),
                customer
            )
        }
    }

    override fun observerData() {
        super.observerData()

        customerEditViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                LoadingDialog.showLoading(requireContext())
            } else {
                LoadingDialog.hide()
            }
        }

        customerEditViewModel.isEditSuccess.observe(viewLifecycleOwner) {
            if (it) {
                CustomerEditRoute.backScreen(this)
            }
        }

        customerEditViewModel.customer.observe(viewLifecycleOwner) { customer ->
            this.customer = customer
            binding.name.setText(customer.name ?: "")
            binding.gender.setSelection(
                when (customer.gender) {
                    "Nam" -> {
                        0
                    }

                    "Nữ" -> {
                        1
                    }

                    else -> {
                        2
                    }
                }
            )
            binding.phone.setText(customer.phoneNumber ?: "")
            binding.address.setText(customer.homeTown ?: "")
            binding.birthday.setText(customer.birthday ?: "")
            binding.note.setText(customer.note ?: "")
            Glide.with(binding.avatar.context)
                .load(customer.imageUrl)
                .into(binding.avatar)
        }
    }

}