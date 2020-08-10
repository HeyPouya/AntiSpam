package ir.apptune.antispam.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.apptune.antispam.repository.Repository
import kotlinx.coroutines.launch

/**
 * ViewModel to fetch searched phone number
 *
 * @property repository
 */
class SearchPhoneViewModel(private val repository: Repository) : ViewModel() {

    private val liveData = MutableLiveData<String>()

    fun searchLocation(number: String) {
        viewModelScope.launch {
            val data = repository.getAddress(number)
            liveData.postValue(data)
        }
    }

    fun getPhoneNumberDetailsLiveData(): LiveData<String> = liveData
}