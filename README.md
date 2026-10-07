# NIT3213 Final Assignment - Android Application Development

## Overview
This Android application is developed as part of the NIT3213 Final Assignment. It demonstrates proficiency in modern Android application development practices, including:
- **MVVM Architecture** (Model-View-ViewModel)
- **Dependency Injection** using **Hilt**
- **Asynchronous Operations** with **Kotlin Coroutines & Flow**
- **Networking** with **Retrofit & OkHttp Logging Interceptor**
- **UI Design** using **ConstraintLayout, RecyclerView, and ViewBinding**
- **Unit Testing** for ViewModels (`LoginViewModelTest`, `DashboardViewModelTest`)

---

## App Screens
1. **Login Screen (`LoginActivity`)**:
   - Allows users to enter their first name (username) and student ID (password).
   - Pre-fills details (`Ankit`, `s8117545`) based on student credentials.
   - Makes a `POST` request to `/footscray/auth`.
   - Upon successful authentication (200 OK), retrieves a `keypass` and navigates to the Dashboard screen.

2. **Dashboard Screen (`DashboardActivity`)**:
   - Uses the received `keypass` to make a `GET` request to `/dashboard/{keypass}`.
   - Displays entities in a high-performance `RecyclerView` using `ListAdapter` and `DiffUtil`.
   - Clicking any item navigates to the Details screen.

3. **Details Screen (`DetailsActivity`)**:
   - Displays comprehensive information about the selected entity, including `property1`, `property2`, and the detailed `description`.

---

## API Details
- **Base URL:** `https://nit3213api.onrender.com/`
- **Auth Endpoint:** `/footscray/auth` (`POST`)
- **Dashboard Endpoint:** `/dashboard/{keypass}` (`GET`)

---

## How to Build and Run
1. Open the project in **Android Studio** (Koala / Ladybug or newer).
2. Sync the project with Gradle files (`File > Sync Project with Gradle Files`).
3. Run unit tests via Gradle: `./gradlew testDebugUnitTest`.
4. Run the app on an Android Emulator or physical device (API level 26+).
