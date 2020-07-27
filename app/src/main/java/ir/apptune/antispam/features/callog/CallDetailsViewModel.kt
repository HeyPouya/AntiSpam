package ir.apptune.antispam.features.callog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.apptune.antispam.pojos.CallModel
import ir.apptune.antispam.repository.Repository
import ir.apptune.antispam.utils.getCallDetails
import kotlinx.coroutines.launch

class CallDetailsViewModel(application: Application, private val repository: Repository) : AndroidViewModel(application) {

    private val liveDataResponse = MutableLiveData<ArrayList<CallModel>>()

    init {
        viewModelScope.launch {
            val list = getCallDetails(application, repository)
            liveDataResponse.postValue(list)
        }
    }

    fun getLiveDataResponse(): LiveData<ArrayList<CallModel>> = liveDataResponse
}