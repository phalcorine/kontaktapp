# KontaktApp - Self Management Platform

### Frameworks Used:
* Ktor - Web API
* KVision (Kotlin/JS) - Frontend Web Application

### Libraries Used:
* <a href="https://arrow-kt.io">Arrow (Core)</a> - Error Handling and Monads
* <a href="https://copper-leaf.github.io/ballast">Ballast (Core & Navigation)</a> - Frontend State Management and Routing
* <a href="https://github.com/JetBrains/Exposed">JetBrains Exposed</a> - Database ORM Framework
* JDBC Drivers (<a href="https://www.h2database.com">H2</a> / <a href="https://dev.mysql.com/downloads/connector/j/">MySQL</a>) - Database Access
* <a href="https://insert-koin.io">Koin</a> - Dependency Injection + Ktor Bindings
* <a href="https://github.com/Kotlin/kotlinx-datetime">kotlinx-datetime</a> - Multiplatform Datetime Library
* <a href="https://ktor.io">Ktor</a> - Http Client
* <a href="https://kvision.gitbook.io/kvision-guide/1.-getting-started-1/modules">KVision Modules</a> - Rich Integrations and Libraries for Frontend Development

### Features:
* [x] Authentication
* [x] Contacts Management
* [ ] Task Management
* [ ] User Preferences
* [ ] UI Enhancements

### Note:
The project can be used with or without a database.
* To use without a database, set the `settings.useInMemoryStorage` property in `src/backendMain/resources/application.conf` to `true`
* To use with a database, configure the `db` section in `src/backendMain/resources/application.conf` accordingly. Don't forget to create a database for it too...

## Gradle Tasks

### Compiling
* compileKotlinFrontend - Compiles frontend sources.
* compileKotlinBackend - Compiles backend sources.
### Running
* frontendRun - Starts a webpack dev server on port 3000
* backendRun - Starts a dev server on port 8080
### Packaging
* frontendBrowserWebpack - Bundles the compiled js files into `build/distributions`
* frontendJar - Packages a standalone "web" frontend jar with all required files into `build/libs/*.jar`
* backendJar - Packages a backend jar with compiled source files into `build/libs/*.jar`
* jar - Packages a "fat" jar with all backend sources and dependencies while also embedding frontend resources into `build/libs/*.jar`