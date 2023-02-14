package io.androkage.kontakt.kontaktapp.routes.contact

import arrow.core.continuations.either
import io.androkage.kontakt.kontaktapp.dto.CreateContactDto
import io.androkage.kontakt.kontaktapp.dto.UpdateContactDto
import io.androkage.kontakt.kontaktapp.endpoints.ContactEndpointService
import io.androkage.kontakt.kontaktapp.error.ApiError
import io.androkage.kontakt.kontaktapp.routes.handleError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.contactRoutes() {

    val contactEndpointService by inject<ContactEndpointService>()

    route("/contacts") {

        get("/list") {
            either {
                val apiResponse = contactEndpointService.list().bind()
                call.respond(
                    status = HttpStatusCode.OK,
                    apiResponse
                )
            }.mapLeft {
                handleError(it)
            }
        }

        get("/find/uid/{uid}") {
            either {
                val contactUid = call.parameters["uid"] ?: return@either shift(ApiError.BadRequestError("The request payload is invalid!"))

                val apiResponse = contactEndpointService.findByUid(contactUid).bind()
                call.respond(
                    status = HttpStatusCode.OK,
                    apiResponse
                )
            }.mapLeft {
                handleError(it)
            }
        }

        post("/create") {
            either {
                val requestBody = runCatching {
                    call.receive<CreateContactDto>()
                }.getOrNull() ?: return@either shift(ApiError.BadRequestError("The request payload is invalid!"))

                val apiResponse = contactEndpointService.create(requestBody).bind()
                call.respond(
                    status = HttpStatusCode.Created,
                    apiResponse
                )
            }.mapLeft {
                handleError(it)
            }
        }

        patch("/update/{uid}") {
            either {
                val contactUid = call.parameters["uid"] ?: return@either shift(ApiError.BadRequestError("The request payload is invalid!"))

                val requestBody = runCatching {
                    call.receive<UpdateContactDto>()
                }.getOrNull() ?: return@either shift(ApiError.BadRequestError("The request payload is invalid!"))

                val apiResponse = contactEndpointService.update(contactUid, requestBody).bind()
                call.respond(
                    status = HttpStatusCode.OK,
                    apiResponse
                )
            }.mapLeft {
                handleError(it)
            }
        }

        delete("/delete/{uid}") {
            either {
                val contactUid = call.parameters["uid"] ?: return@either shift(ApiError.BadRequestError("The request payload is invalid!"))

                val apiResponse = contactEndpointService.delete(contactUid).bind()
                call.respond(
                    status = HttpStatusCode.NoContent,
                    apiResponse
                )
            }.mapLeft {
                handleError(it)
            }
        }
    }
}