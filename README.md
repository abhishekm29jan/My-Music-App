# 🎧 MusicApp UI – Modern Music Streaming App Interface with Jetpack Compose

**MusicApp UI** is a fully responsive, modular, and elegant music streaming application prototype built entirely with **Jetpack Compose** and **Kotlin**. The project demonstrates the use of **modern Android development practices**, clean UI architecture, and intuitive navigation components, making it a strong reference for building scalable Compose-based apps.

---

### 📱 Key Features

* ✅ **Jetpack Compose UI** – Built with the latest declarative UI toolkit for Android
* ✅ **Navigation Architecture** – Includes a **drawer** and **bottom navigation** system for seamless screen transitions
* ✅ **Modular Screens** – Home, Library, Browse, Account, and Subscription
* ✅ **Modal Bottom Sheet** – Integrated for additional actions like Settings, Share, and Help
* ✅ **Dialog Implementation** – Custom dialog triggered from the drawer ("Add Account")
* ✅ **MVVM Architecture** – ViewModel-driven screen management and UI state handling
* ✅ **Responsive Layouts** – Designed for various screen sizes with attention to spacing and alignment

---

### 🛠️ Tech Stack

| Technology                | Purpose                               |
| ------------------------- | ------------------------------------- |
| **Kotlin**                | Core language for Android development |
| **Jetpack Compose**       | Modern UI toolkit for declarative UIs |
| **Navigation Component**  | Manages app navigation                |
| **ViewModel (Lifecycle)** | UI state and logic handling           |
| **Material Design 3**     | Clean and consistent UI components    |

---

### 📂 Project Structure

```
├── ui.theme/
│   ├── MainView.kt            → Main Scaffold with TopBar, BottomBar, Drawer
│   ├── Home.kt                → Home screen with categorized content
│   ├── AccountView.kt         → User account details
│   ├── Subscription.kt        → Subscription plan UI
│   ├── Navigation.kt          → NavHost with routes
├── model/
│   ├── Screen.kt              → Navigation routes and data classes
├── viewmodel/
│   ├── MainViewModel.kt       → Holds current screen state
```

---

### 🚀 How to Run the Project

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/MusicAppUI.git
   ```
2. Open the project in **Android Studio Hedgehog (or newer)**
3. Ensure **minimum SDK** is set to **API 26 or above**
4. Click **Run ▶️** on an emulator or physical device

---

### 📸 Screenshots *(Optional)*

*Add 2–3 images or GIFs here showing Home Screen, Navigation Drawer, Bottom Sheet.*

---

### 📌 Use Case

This project serves as:

* A **template** for building modern music or media apps
* A **learning resource** for mastering Jetpack Compose and architectural patterns
* A **portfolio piece** for Android developers focusing on UI/UX and clean architecture

---

### 🙌 Contributions

Feel free to fork, improve, and contribute! Pull requests and feedback are welcome.

