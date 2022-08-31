package mob.dau.ordercpu.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import mob.dau.ordercpu.R
import mob.dau.ordercpu.databinding.FragmentTotalBinding
import mob.dau.ordercpu.model.CpuViewModel
import java.text.NumberFormat

class TotalFragment : Fragment() {
    private val sharedViewModel: CpuViewModel by activityViewModels()

    private var _binding: FragmentTotalBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTotalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = sharedViewModel
        sharedViewModel.getTotalPrice()
        sharedViewModel.totalPrice.observe(viewLifecycleOwner) { newTotalPrice ->
            val formattedPrice = NumberFormat.getCurrencyInstance().format(newTotalPrice)
            binding.totalPrice.text = getString(R.string.total_label, formattedPrice)
        }
        binding.cancelButton.setOnClickListener { cancelOrder() }
        binding.sendButton.setOnClickListener { sendOrder() }
    }

    private fun cancelOrder() {
        sharedViewModel.resetOrder()
        findNavController().navigate(R.id.action_totalFragment_to_manufacturerFragment)
    }

    fun sendOrder() {
        val numberOfCPUs = sharedViewModel.quantity.value ?: 0
        val orderSummary = getString(
            R.string.order_details,
            resources.getQuantityString(R.plurals.cpus, numberOfCPUs, numberOfCPUs),
            sharedViewModel.cpuManufacturer.value,
            sharedViewModel.cpuModel.value,
            sharedViewModel.totalPrice.value.toString(),
        )

        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_cpu_order))
            .putExtra(Intent.EXTRA_TEXT, orderSummary)
        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        }
    }
}