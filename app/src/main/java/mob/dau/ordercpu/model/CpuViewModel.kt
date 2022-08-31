package mob.dau.ordercpu.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CpuViewModel : ViewModel() {
    private val _cpuManufacturer = MutableLiveData<String>()
    val cpuManufacturer: LiveData<String>
        get() = _cpuManufacturer

    private val _cpuModel = MutableLiveData<String>()
    val cpuModel: LiveData<String>
        get() = _cpuModel

    private val _pricePerUnit = MutableLiveData<Int>()
    val pricePerUnit: LiveData<Int>
        get() = _pricePerUnit

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int>
        get() = _quantity

    private val _totalPrice = MutableLiveData<Int>()
    val totalPrice: LiveData<Int>
            get() = _totalPrice

    init {
        resetOrder()
    }

    fun setManufacturer(manufacturer: String) {
        _cpuManufacturer.value = manufacturer
    }

    fun setModelAndPricePerUnit(model: String, price: Int) {
        _cpuModel.value = model
        _pricePerUnit.value = price
    }

    fun hasNoModel(): Boolean {
        return _cpuModel.value.isNullOrEmpty()
    }

    fun setQuantity(quantity: Int) {
        _quantity.value = quantity
    }

    fun getTotalPrice() {
        _totalPrice.value = pricePerUnit.value!! * quantity.value!!
    }

    fun resetOrder() {
        _cpuManufacturer.value = ""
        _cpuModel.value = ""
        _pricePerUnit.value = 0
        _quantity.value = 1
    }
}