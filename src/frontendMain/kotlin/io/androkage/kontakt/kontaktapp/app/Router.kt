package io.androkage.kontakt.kontaktapp.app

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.ExperimentalBallastApi
import com.copperleaf.ballast.build
import com.copperleaf.ballast.eventHandler
import com.copperleaf.ballast.navigation.browser.withBrowserHashRouter
import com.copperleaf.ballast.navigation.routing.*
import com.copperleaf.ballast.navigation.vm.BasicRouter
import com.copperleaf.ballast.navigation.vm.withRouter
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.Serializable
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Serializable
enum class AppRouter(
    routeFormat: String,
    override val annotations: Set<RouteAnnotation> = emptySet()
) : Route {
    Home("/home"),
    ContactList("/contact/list"),
    ContactAdd("/contact/add"),
    ContactDetail("/contact/detail/{uid}");

    override val matcher: RouteMatcher = RouteMatcher.create(routeFormat)
}

@OptIn(ExperimentalBallastApi::class)
class AppRouterViewModel(
    coroutineScope: CoroutineScope,
    config: BallastViewModelConfiguration.Builder
) : BasicRouter<AppRouter>(
    config = config
        .withBrowserHashRouter(RoutingTable.fromEnum(AppRouter.values()), AppRouter.Home)
        .build(),
    eventHandler = eventHandler {  },
    coroutineScope = coroutineScope
)

val routerModule = module {
    singleOf(::AppRouterViewModel)
}