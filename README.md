<p align="center">
  <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-yellow.svg" alt="License: MIT"></a>
  <img src="https://img.shields.io/badge/Java-24-ED8B00.svg?logo=openjdk" alt="Language: Java 24">
  <img src="https://img.shields.io/badge/Processing-4.4.1-5A4098.svg?logo=processingfoundation" alt="Uses: Processing">
  <img src="https://img.shields.io/badge/Maven-3.9-C71A36.svg?logo=apachemaven" alt="Build: Maven">
</p>

<p align="center">
  <img src="./docs/img/logo.png" alt="ViperVision Logo" width="600"/>
</p>

> A modern take on the classic Snake game, engineered with a focus on clean, decoupled architecture, classic design patterns, and multiple AI-driven gameplay modes.

ViperVision is a comprehensive implementation of the Snake game built from the ground up using Java and the Processing library for rendering. More than just a game, it serves as a practical case study for applying robust software design principles to build a flexible, maintainable, and extensible application.

---

## ✨ Features

* **Classic Gameplay:** Smooth, interpolated movement, food consumption, and score tracking.
* **Multiple Game Modes:**
    * **Human Player:** Take control with the arrow keys in a responsive input-buffered system.
    * **Pathfinding AI (WIP):** Watch the snake autonomously navigate using algorithms like A* to find the optimal and safest path to the food.
    * **Genetic Algorithm AI (Planned):** Observe an AI that evolves and learns to play through generations of simulated evolution.
* **Polished UI/UX:** Interactive menus with custom fonts, graphics, and a fluid game loop.
* **Clean, Decoupled Architecture:** Built to be easily understood, modified, and extended.

---

## 🏗️ Architecture & Design
ViperVision is engineered with a modern, decoupled architecture based on the Model-View-Presenter (MVP) pattern. This clean separation of concerns ensures the codebase is scalable, maintainable, and highly testable. The core of the architecture is a central contracts package that uses interfaces to prevent cyclical dependencies, adhering to the Dependency Inversion Principle.

### Architectural Layers

* **contracts** (The Core API): This package contains only interfaces and data transfer objects (like events). It is the central pillar that all other layers depend on, but it depends on nothing. This guarantees a unidirectional dependency flow.
* **model** (The Data and Business Logic): This layer contains the pure game state and rules. It is completely independent of the UI.
* **view** (The Presentation Layer): This layer is responsible for everything the user sees and hears. It is "dumb" and only acts on the commands of the Presenter.
* **presenter** (The Application Logic): This layer acts as the middleman. It responds to user input from the View, manipulates the Model, and tells the View what to display.

### Design Patterns in Use

* **Model-View-Presenter (MVP)**: The core architectural pattern separating data, presentation, and logic.
* **State**: Manages the game's high-level flow (e.g., MenuState, PlayingState).
* **Strategy**: Decouples the snake's control algorithm (HumanControlStrategy, FollowFoodStrategy) from the game logic.
* **Observer (Event Bus)**: A global EventManager allows decoupled communication between different parts of the system (e.g., game logic notifying the sound system).
* **Factory**: ComponentFactory centralizes the creation of UI components from configuration data. ApplicationFactory encapsulates the initial setup of the game.
* **Singleton**: Used for stateless, global services like renderers and the EventManager.
* **Composite**: The UI is built as a composite structure of Layouts and IUiComponents, managed by a UiManager.
* **Command**: UI button actions are encapsulated as Command objects, decoupling the button from the action it performs.

### Key Diagrams

Here are some diagrams illustrating the project's structure and flow.

#### Package Structure

![Package Structure](./docs/diagrams/packages.jpg)

#### State Machine

![State Diagram](./docs/diagrams/states.jpg)


#### Class Diagram

You can access all class diagrams in the file [`docs/diagrams/diagrams.mdj`](docs/diagrams/diagrams.mdj).

---

## 🛠️ Technologies Used

* **Language:** Java 24
* **Graphics & Interaction:** [Processing](https://processing.org/) (v4.4.1)
* **Build Tool:** [Apache Maven](https://maven.apache.org/)
* **Diagramming:** StarUML / Mermaid

---

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine.

### Prerequisites

* **JDK 24** (or the version specified in `pom.xml`). Make sure `JAVA_HOME` is set correctly.
* **Apache Maven** installed and configured in your system's PATH.

### Installation & Running

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/SebAs-man/ViperVision.git
    cd ViperVision
    ```
2.  **Build and run the project using Maven:**
    ```bash
    mvn clean package exec:java
    ```

---

## 🎨 A Note on Design

This project's primary focus is on software architecture and clean, maintainable code. While efforts were made to create a pleasant user interface, the visual design choices reflect an engineer's perspective. Contributions, ideas, and pull requests from design-savvy individuals are most welcome!

---

## 📜 License

This project is distributed under the MIT License. See the [`LICENSE`](LICENSE) file for more information.

## 🙏 Acknowledgements

* The [Processing Foundation](https://processingfoundation.org/) for their amazing creative coding environment.
* You, for exploring this project!