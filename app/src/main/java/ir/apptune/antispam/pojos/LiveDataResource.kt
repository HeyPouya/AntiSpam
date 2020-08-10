package ir.apptune.antispam.pojos

/**
 * A resource to show and hide loading and other states
 *
 * @property callModel
 */
sealed class LiveDataResource(val callModel: List<CallModel>) {
    class Loading : LiveDataResource(listOf())
    class Success(callModel: List<CallModel>) : LiveDataResource(callModel)
    class Completed(callModel: List<CallModel>) : LiveDataResource(callModel)
    class NoCallLogPermission : LiveDataResource(listOf())
}