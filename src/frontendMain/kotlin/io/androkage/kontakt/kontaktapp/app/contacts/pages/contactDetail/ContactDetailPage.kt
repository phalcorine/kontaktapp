package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactDetail

import io.androkage.kontakt.kontaktapp.app.contacts.components.ContactFormDto
import io.androkage.kontakt.kontaktapp.app.contacts.components.contactForm
import io.androkage.kontakt.kontaktapp.app.contacts.pages.contactAdd.ContactAddPageContract
import io.androkage.kontakt.kontaktapp.app.layout.mainLayout.mainLayout
import io.androkage.kontakt.kontaktapp.dto.CreateContactDto
import io.androkage.kontakt.kontaktapp.dto.UpdateContactDto
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.modal.Confirm
import io.kvision.state.bind
import io.kvision.toolbar.buttonGroup
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun Container.contactDetailPage(contactUid: String) : KoinComponent = object : KoinComponent {

    private val vm by inject<ContactDetailPageViewModel>()

    init {
        vm.trySend(ContactDetailPageContract.Inputs.Initialize(contactUid))

        mainLayout("Contact Details") {
            div().bind(vm, {
                it.loading
            }) {
                if (it) {
                    p { +"Fetching Data..." }
                }
            }

            div().bind(vm) { pageState ->
                val contact = pageState.contact
                contact?.let {
                    h3 { +"Contact Details: ${contact.name}" }

                    if (pageState.isEditing) {
                        val contactFormDto = ContactFormDto(
                            name = contact.name,
                            note = contact.note,
                            phoneNumbers = contact.phoneNumbers.joinToString(",") { phone -> phone.phoneNumber },
                            emails = contact.emails.joinToString(",") { email -> email.email }
                        )
                        contactForm(
                            formDto = contactFormDto,
                            onError = {
                                vm.trySend(ContactDetailPageContract.Inputs.ErrorMessage("One or more fields needs your attention!"))
                            },
                            onSave = {
                                val requestPayload = UpdateContactDto(
                                    name = it.name,
                                    phoneNumbers = it.phoneNumbers.split(","),
                                    emails = it.emails.split(","),
                                    note = it.note
                                )

                                vm.trySend(ContactDetailPageContract.Inputs.UpdateContact(contactUid, requestPayload))
                            }
                        )
                    } else {
                        div {
                            p {
                                span { +"Name: " }
                                span { +contact.name }
                            }
                            p {
                                span { +"Emails: " }
                                span { +contact.emails.joinToString(",") { email -> email.email } }
                            }
                            p {
                                span { +"Phone Numbers: " }
                                span { +contact.phoneNumbers.joinToString(",") { phone -> phone.phoneNumber } }
                            }
                            p {
                                span { +"Note: " }
                                span { +(contact.note ?: "(not set)") }
                            }

                            buttonGroup {
                                button("Edit Contact", style = ButtonStyle.PRIMARY) {
                                    onClick {
                                        vm.trySend(ContactDetailPageContract.Inputs.EnableEditMode)
                                    }
                                }
                                button("Delete Contact", style = ButtonStyle.DANGER) {
                                    onClick {
                                        Confirm.show(
                                            caption = "Confirmation",
                                            text = "Are you sure you want to perform this action?",
                                            yesCallback = {
                                                vm.trySend(ContactDetailPageContract.Inputs.DeleteContact(contact.uid))
                                            },
                                            noCallback = {
                                                vm.trySend(ContactDetailPageContract.Inputs.ErrorMessage("Fear, fear!!! :)"))
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}