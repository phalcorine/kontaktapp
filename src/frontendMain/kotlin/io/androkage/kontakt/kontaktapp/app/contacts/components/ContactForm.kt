package io.androkage.kontakt.kontaktapp.app.contacts.components

import io.androkage.kontakt.kontaktapp.app.contacts.pages.contactAdd.ContactAddPageContract
import io.androkage.kontakt.kontaktapp.dto.CreateContactDto
import io.kvision.core.Container
import io.kvision.form.formPanel
import io.kvision.form.text.Text
import io.kvision.form.text.TextArea
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.toolbar.buttonGroup
import kotlinx.serialization.Serializable

@Serializable
data class ContactFormDto(
    val name: String = "",
    val note: String? = null,
    val phoneNumbers: String = "",
    val emails: String = ""
)

fun Container.contactForm(
    formDto: ContactFormDto = ContactFormDto(),
    onSave: (ContactFormDto) -> Unit,
    onError: (String) -> Unit
) {
    val contactForm = formPanel<ContactFormDto> {
        add(
            ContactFormDto::name,
            Text(label = "Contact Name") {
                placeholder = "Name of the Contact"
            },
            required = true
        )
        add(
            ContactFormDto::phoneNumbers,
            Text(label = "Phone Numbers: ") {
                placeholder = "e.g 07011223344,09022334455"
            },
            required = true
        )
        add(
            ContactFormDto::emails,
            Text(label = "Emails: ") {
                placeholder = "e.g one@mail.com,two@mail.com"
            },
            required = true,
        )
        add(
            ContactFormDto::note,
            TextArea(label = "Note: ") {
                placeholder = "Any other information..."
            },
            required = false
        )
    }
    buttonGroup {
        button("Save Contact") {
            onClick {
                if (!contactForm.validate()) {
                    onError("One or more fields needs your attention!")
                    return@onClick
                }

                val formData = contactForm.getData()
                onSave(formData)
            }
        }
        button("Reset", style = ButtonStyle.DANGER) {
            onClick {
                contactForm.reset()
            }
        }
    }

    // addAfterInsertHook {
        contactForm.setData(formDto)
    // }
}