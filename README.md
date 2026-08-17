# JetTrivia-KT 🧠

JetTrivia-KT is a modern, responsive, and robust Android application built using the latest Android development tools and best practices. It's a trivia quiz application that fetches data from a remote API and presents it in a beautiful, interactive UI.

## 🚀 Features

- **Dynamic Trivia:** Fetches a wide range of questions from a remote JSON source.
- **Interactive UI:** Smooth transitions and interactive elements built with Jetpack Compose.
- **Progress Tracking:** Visual progress bars and question counters to keep the user engaged.
- **Robust Error Handling:** Uses a custom `DataOrException` wrapper to handle loading states, data success, and exceptions gracefully.
- **Clean Architecture:** Separated into layers (Data, Repository, UI, DI) for better maintainability and scalability.

## 🛠 Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Networking:** [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson)
- **Database:** [Room](https://developer.android.com/training/data-storage/room) (Local persistence support)
- **Concurrency:** [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Architecture:** MVVM (Model-View-ViewModel)

## 🏗 Architecture

The project follows the **MVVM (Model-View-ViewModel)** architectural pattern along with **Repository Pattern** to ensure a clean separation of concerns:

- **Model:** Defines the data structure (e.g., `QuestionModel`, `Question`).
- **Data Layer:** Handles API calls via Retrofit (`QuestionAPI`) and local database operations via Room.
- **Repository:** Acts as a mediator between the data sources and the ViewModels (`QuestionRepository`).
- **ViewModel:** Manages UI-related data and communicates with the repository (`QuestionsViewModel`).
- **UI Layer:** Composable functions that define the user interface based on the state provided by the ViewModel (`TriviaHome`).

## 📁 Project Structure

```text
com.tamara.jettrivia
├── component/      # Reusable UI components
├── data/           # Data wrappers and sources
├── di/             # Dependency Injection modules
├── model/          # Data models
├── network/        # Retrofit API definitions
├── repository/     # Data repositories
├── screens/        # UI Screens and ViewModels
├── ui/             # Theme and styling
├── util/           # Constants and utility classes
└── widgets/        # Custom UI widgets
```

## 🚦 Getting Started

### Prerequisites

- Android Studio Ladybug or newer.
- JDK 17 or higher.
- Basic knowledge of Kotlin and Jetpack Compose.

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/JetTrivia-KT.git
   ```
2. Open the project in Android Studio.
3. Sync Gradle and build the project.
4. Run the app on an emulator or a physical device.

## 🌐 API Reference

The app uses trivia questions hosted on GitHub:
- **Base URL:** `https://raw.githubusercontent.com/itmmckernan/triviaJSON/master/`
- **Endpoint:** `general.json`

---
Developed with ❤️ by [Tamara](https://github.com/your-username)
