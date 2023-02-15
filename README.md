# KontaktApp - Self Management Platform

### Frameworks Used:
* Ktor - Web API
* KVision (Kotlin/JS) - Frontend Web Application

### Libraries Used:
* Arrow (Core) - Error Handling and Monads
* Ballast (Core & Navigation) - Frontend State Management and Routing
* JetBrains Exposed - Database ORM Framework
* JDBC Drivers (H2 / MySQL) - Database Access
* Koin - Dependency Injection + Ktor Bindings
* kotlinx-datetime - Multiplatform Datetime Library
* Ktor - Http Client
* KVision Modules - Rich Integrations and Libraries for Frontend Development

### Features:
* [ ] Authentication
* [x] Contacts Management
* [x] Task Management
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