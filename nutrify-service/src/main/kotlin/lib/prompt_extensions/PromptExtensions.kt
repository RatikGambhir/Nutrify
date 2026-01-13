package com.nutrify.lib.prompt_extensions

import com.nutrify.dto.RegisterUserProfileRequest

fun RegisterUserProfileRequest.toPromptVariables(): Map<String, Any> {
    return mapOf(
        "userId" to userId,
        "age" to basicDemographics.age,
        "gender" to (basicDemographics.gender ?: basicDemographics.sex ?: ""),
        "height" to basicDemographics.height,
        "weight" to basicDemographics.weight,
        "activityLevel" to activityLifestyle.activityLevel,
        "primaryGoal" to goals.primaryGoal
    )
}
