package com.example.usecases

import com.example.models.ResponseErrors
import com.example.models.User
import com.example.models.responses.UserResponse
import com.example.repository.UserRepository
import com.example.utils.ServiceResult
import com.example.utils.ValidateUserEmail
import com.example.utils.toDatabaseString
import java.time.LocalDateTime

/**
 * خوب usecase ها رابط بین route و dao هستن. یعنی قبل از اینکه کاربر رو بفرستیم به dao واسه اضافه شدن توی دیتابیس، میایم از طریق usecase اون
 * مواردی که لازمه رو به اون کاربر اضافه میکنیم یا اینکه فیلدهای کاربر رو بررسی می کنیم تا مثلا ایمیلش رو درست وارد کرده باشه. اگه مواردی رو مثل کلاس چک کردن
 * ایمیل رو جدا ساخته باشیم میتونیم اون کلاس رو به عنوان پارامتر توی constructor این usecase اضافه کنیم تا بتونیم ازش استفاده کنیم.
 * مثلا ما اینجا کلاس بررسی ایمیل کاربر رو به صورت جدا ساختیم و قراره اینجا ازش استفاده کنیم. همینطور ما userRepository رو هم بهش پاس میدیم تا
 * بعد از اینکه تاریخ ثبت نام کاربر رو اینجا بهش اضافه کردیم، با استفاده از dao بتونیم اون کاربر رو توی دیتابیس اضافه کنیم.
 * البته بعد از چک کردن اینکه آیا کاربر ایمیل درستی وارد کرده یا نه.
 * پس میشه برای مثال، برای کاربر هم ایمیل و اس ام اس فرستاد با استفاده از usecase ها
 */
class InsertUserUseCase(
    private val validateUserEmail: ValidateUserEmail,
    private val userRepository: UserRepository
    ) {

    suspend operator fun invoke(user: User): UserResponse {
        return when (val result = validateUserEmail.invoke(user)) {
            is ServiceResult.Success -> {
                val response = userRepository.insertUser(user.copy(
                    date_created = LocalDateTime.now().toDatabaseString()
                ))
                when (response) {
                    is ServiceResult.Success -> UserResponse(response.data, emptyList())
                    is ServiceResult.Error -> UserResponse(user, listOf(ResponseErrors(response.errorCode, response.errorCode.message)))
                }
            }
            is ServiceResult.Error -> UserResponse(user, listOf(ResponseErrors(result.errorCode, result.errorCode.message)))
        }
    }

//    suspend operator fun invoke(user: ServiceResult<User>): UserResponse {
//        return when (user) {
//            is ServiceResult.Success -> {
//                val response = userRepository.insertUser(user.data.copy(
//                    date_created = LocalDateTime.now().toDatabaseString()
//                ))
//                when(response) {
//                    is ServiceResult.Success -> UserResponse(response.data!!, emptyList())
//                    is ServiceResult.Error -> UserResponse(user.data, listOf(ResponseErrors(response.errorCode, response.errorCode.message)))
//                }
//            }
//            is ServiceResult.Error -> UserResponse(null, listOf(ResponseErrors(user.errorCode, user.errorCode.message)))
//        }
//    }
}