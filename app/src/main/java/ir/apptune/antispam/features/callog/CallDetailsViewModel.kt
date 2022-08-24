package ir.apptune.antispam.features.callog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.apptune.antispam.pojos.CallModel
import ir.apptune.antispam.pojos.LiveDataResource
import ir.apptune.antispam.utils.CallDetailClass
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

/**
 * Fetches cal logs of the user
 *
 * @property callDetails is a class that provides call logs via ContentProvider
 */
class CallDetailsViewModel(private val callDetails: CallDetailClass) : ViewModel() {

    private val liveDataResponse = MutableLiveData<LiveDataResource>()
    private val list = arrayListOf<CallModel>()

    fun getCallLog(hasCallLogPermission: Boolean) {
        if (hasCallLogPermission) {
            viewModelScope.launch {
                liveDataResponse.postValue(LiveDataResource.Loading())
                callDetails.getCallDetails()
                    .onCompletion {
                        liveDataResponse.postValue(LiveDataResource.Completed(list.toList()))
                    }.collect {
                        list.add(it)
                        liveDataResponse.postValue(LiveDataResource.Success(list.toList()))
                    }
            }
        } else
            liveDataResponse.postValue(LiveDataResource.NoCallLogPermission())
    }

    fun getLiveDataResponse(): LiveData<LiveDataResource> = liveDataResponse

}