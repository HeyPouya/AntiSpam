package ir.apptune.antispam.features.callog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.apptune.antispam.utils.getCallDetails
import ir.apptune.antispam.pojos.CallModel
import kotlinx.coroutines.launch

class CallDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val liveDataResponse = MutableLiveData<ArrayList<CallModel>>()

    init {
        viewModelScope.launch {
            val list = getCallDetails(application)
            liveDataResponse.postValue(list)
        }
    }

    fun getLiveDataResponse(): LiveData<ArrayList<CallModel>> = liveDataResponse
}