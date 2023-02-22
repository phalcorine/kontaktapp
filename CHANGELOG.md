## [Version 0.2]

### Features:
- [API] Implemented JWT-based authentication
- [API] Implemented endpoints for authentication
- [API] Replaced Ktor Routes implementation with KVision Server-Side interfaces
- [Frontend] Replaced API endpoints with KVision Server-Side interfaces
- [Frontend] Implemented navigation guard for protected routes
- [Frontend] Implemented global state management for authentication
- [Frontend] Implemented User Login and Signup pages

### Bug Fixes:
- [API] Fixed bug where deleting a contact seems to delete other contacts instead
- [Frontend] Fixed bug where contact information wasn't updated after an edit

### Chore / Refactoring:
- [API] Refactored database schema and facades
- [API] Removed redundant exception handling for in-memory data access facades
- [API] Made improvements API Error to HTTP Response handling
- [Frontend] Made improvements HTTP Response to API Error handling
- [Frontend] Replaced `singleOf` with `factoryOf` for Ballast UI ViewModels and associated dependencies
- [Frontend] Adjusted file names for UI components
- [Frontend] Moved `appHeader` UI component to a better position in the UI tree

### Dependencies:
- Added `BCrypt` library for password hashing (API)
- Added `Ballast Repository` for application layer data management (Frontend)



## [Version 0.1]
### Changes:
- Initial commit...
- Contacts Management