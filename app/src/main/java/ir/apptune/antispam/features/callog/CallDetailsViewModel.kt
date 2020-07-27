package ir.apptune.antispam.features.callog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.apptune.antispam.pojos.CallModel
import ir.apptune.antispam.pojos.LiveDataResource
import ir.apptune.antispam.repository.Repository
import ir.apptune.antispam.utils.getCallDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CallDetailsViewModel(application: Application, private val repository: Repository) : AndroidViewModel(application) {

    private val liveDataResponse = MutableLiveData<LiveDataResource>()
    private val list = arrayListOf<CallModel>()

    init {
        viewModelScope.launch {
            liveDataResponse.postValue(LiveDataResource.Loading())
            getCallDetails(application, repository).onCompletion {
                liveDataResponse.postValue(LiveDataResource.Completed(list.toList()))
            }.collect {
                list.add(it)
                liveDataResponse.postValue(LiveDataResource.Success(list.toList()))
            }
        }
    }

    fun getLiveDataResponse(): LiveData<LiveDataResource> = liveDataResponse
}