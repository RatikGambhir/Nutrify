package com.nutrify.lib.prompt_extensions

import com.nutrify.dto.RegisterUserRequest
import kotlin.reflect.full.createInstance
import kotlin.reflect.full.memberProperties

fun RegisterUserRequest.toPromptVariables(): Map<String, Any> {
    return mapOf(
        "userId" to userId,
        "age" to age,
        "gender" to gender,
        "height" to height,
        "weight" to weight,
        "ethnicity" to ethnicity,
        "activityLevel" to activityLevel,
        "primaryGoal" to primaryGoal
    )
}

fun RegisterUserRequest.toPromptTemplate(resultSet: java.sql.ResultSet): RegisterUserRequest {
    val props = RegisterUserRequest::class.memberProperties.forEach {
         resultSet.getObject(it.name)
    }
    return RegisterUserRequest::class.constructors.first().call(props)
}

//fun RegisterUserRequest._toPromptTemplate(resultSet: java.sql.ResultSet): RegisterUserRequest {
//    val props = RegisterUserRequest::class.memberProperties.associateWith { property -> resultSet.getObject(property.name) }
//    return RegisterUserRequest::class.constructors.first().call(props)
//
//}
