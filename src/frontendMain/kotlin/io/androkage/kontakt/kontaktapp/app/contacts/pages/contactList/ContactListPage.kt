package io.androkage.kontakt.kontaktapp.app.contacts.pages.contactList

import io.androkage.kontakt.kontaktapp.app.layout.mainLayout.mainLayout
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.state.bind
import io.kvision.tabulator.ColumnDefinition
import io.kvision.tabulator.Layout
import io.kvision.tabulator.TabulatorOptions
import io.kvision.tabulator.tabulator
import io.kvision.utils.px
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun Container.contactListPage() : KoinComponent = object : KoinComponent {
    val vm by inject<ContactListPageViewModel>()
    
    init {
        vm.trySend(ContactListPageContract.Inputs.RefreshContacts)

        mainLayout("Contact List") {
            div {
                button("Add Contact") {
                    onClick {
                        vm.trySend(ContactListPageContract.Inputs.NavigateToContactAddPage)
                    }
                }

                br {  }

                div().bind(vm) { state ->
                    if (state.loading) {
                        h4 { +"Loading Information..." }
                    }

                    div {
                        tabulator(
                            state.contacts,
                            options = TabulatorOptions(
                                layout = Layout.FITCOLUMNS,
                                columns = listOf(
                                    ColumnDefinition("Name", "name"),
                                    ColumnDefinition(
                                        title = "Phone Number(s)",
                                        formatterComponentFunction = { _, _, contact ->
                                            Span {
                                                +contact.phoneNumbers.joinToString(", ") { phoneNumber -> phoneNumber.phoneNumber }
                                            }
                                        }
                                    ),
                                    ColumnDefinition(
                                        title = "Email Address(es)",
                                        formatterComponentFunction = { _, _, contact ->
                                            Span {
                                                +contact.emails.joinToString(", ") { email -> email.email }
                                            }
                                        }
                                    ),
                                    ColumnDefinition(
                                        title = "Action(s)",
                                        formatterComponentFunction = { _, _, contact ->
                                            button("View", className = "fa-regular fa-eye") {
                                                onClick {
                                                    vm.trySend(ContactListPageContract.Inputs.NavigateToContactDetailPage(contact.uid))
                                                }
                                            }
                                        }
                                    ),
                                )
                            )
                        ) {
                            height = 400.px
                        }
                    }
                }
            }
        }
    }
}