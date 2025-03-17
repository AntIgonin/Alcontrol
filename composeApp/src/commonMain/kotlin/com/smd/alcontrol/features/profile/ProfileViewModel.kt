package com.smd.alcontrol.features.profile

import alcontrol.composeapp.generated.resources.Res
import alcontrol.composeapp.generated.resources.profile_age_label
import alcontrol.composeapp.generated.resources.profile_avg_alcohol_consumption_label
import alcontrol.composeapp.generated.resources.profile_days_of_consumption
import alcontrol.composeapp.generated.resources.profile_gender_label
import alcontrol.composeapp.generated.resources.profile_weight_label
import androidx.lifecycle.viewModelScope
import com.smd.alcontrol.base.viewmodel.BaseViewModel
import com.smd.alcontrol.database.repository.UserRepository
import com.smd.alcontrol.utils.getStringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    private val userRepository: UserRepository
) : BaseViewModel<ProfileViewState, ProfileAction, ProfileViewEvent>(
    initialState = ProfileViewState()
) {

    init {
        viewModelScope.launch {
            val user = userRepository.getUser()
            withContext(Dispatchers.Main) {
                viewState = viewState.copy(
                    name = user?.name,
                    age = user?.age,
                    ageLabel = getStringResource(Res.string.profile_age_label),
                    weight = user?.weight,
                    weightLabel = getStringResource(Res.string.profile_weight_label),
                    gender = user?.gender,
                    genderLabel = getStringResource(Res.string.profile_gender_label),
                    avgPureAlcoholConsumption = user?.avgPureAlcoholConsumption,
                    avgPureAlcoholConsumptionLabel = getStringResource(Res.string.profile_avg_alcohol_consumption_label),
                    daysOfConsumption = user?.daysOfConsumption ?: listOf(),
                    daysOfConsumptionLabel = getStringResource(Res.string.profile_days_of_consumption)
                )
            }
        }
    }

    override fun processEvent(viewEvent: ProfileViewEvent) {

    }
}