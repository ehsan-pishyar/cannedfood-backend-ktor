package com.example.routings.user

import com.example.models.User
import com.example.usecases.InsertUserUseCase
import com.example.utils.Routes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

/**
 * اینجا اولین مرحله از طرف کاربر هستش که یه کاربری قراره ساخته بشه.
 * خوب وقتی کاربر اطلاعات خودشو وارد کرد و دکمه ثبت نام رو زد، یه آبجکتی از جنس User ساخته میشه
 * و توی usecase ایجاد کاربر جدید قرار داده میشه تا قبل از ثبت کاربر توی دیتابیس بررسی هایی روی این آبجکت انجام بشه.
 * مثلا ایمیل کاربر چک بشه که طبق ساختار درست باشه و همینطور تاریخ ثبت نام کاربر توی همون usecase اضافه بشه به دیتابیس.
 * وقتی کاربر رو به usecase پاس میدیم، یه آبجکتی به نام UserResponse برامون برمیگردونه تا اونو بعد از ثبت نام کاربر نمایش بده.
 */
fun Route.insertNewUser(insertUserUseCase: InsertUserUseCase) {
    route(Routes.USERS_ROUTE) {
        post(Routes.CREATE_ROUTE) {

            val request = call.receiveOrNull<User>() ?: kotlin.run {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Error! Check your json file"
                )
                return@post
            }

            insertUserUseCase(request).let {
                val httpStatus = if (it.errors.isEmpty()) HttpStatusCode.Created else HttpStatusCode.BadRequest

                call.respond(
                    status = httpStatus,
                    message = it
                )
            }
            // val userResponse = insertUserUseCase(ServiceResult.Success(request))
        }
    }
}