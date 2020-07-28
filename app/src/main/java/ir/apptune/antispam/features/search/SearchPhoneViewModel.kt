package ir.apptune.antispam.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.apptune.antispam.repository.Repository
import kotlinx.coroutines.launch

class SearchPhoneViewModel(private val repository: Repository) : ViewModel() {

    fun searchLocation(number: String) {
        viewModelScope.launch {
            val data = repository.getAddress(number)
        }
    }
}