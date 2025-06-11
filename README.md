<p align="center">
  <a href="[https://opensource.org/licenses/MIT](https://opensource.org/licenses/MIT)"><img src="[https://img.shields.io/badge/License-MIT-yellow.svg](https://img.shields.io/badge/License-MIT-yellow.svg)" alt="License: MIT"></a>
  <img src="[https://img.shields.io/badge/Java-24-ED8B00.svg?logo=openjdk](https://img.shields.io/badge/Java-24-ED8B00.svg?logo=openjdk)" alt="Language: Java 24">
  <img src="[https://img.shields.io/badge/Processing-4.4.1-5A4098.svg?logo=processingfoundation](https://img.shields.io/badge/Processing-4.4.1-5A4098.svg?logo=processingfoundation)" alt="Uses: Processing">
  <img src="[https://img.shields.io/badge/Maven-3.9-C71A36.svg?logo=apachemaven](https://img.shields.io/badge/Maven-3.9-C71A36.svg?logo=apachemaven)" alt="Build: Maven">
</p>

<p align="center">
  <img src="./docs/img/logo.png" alt="ViperVision Logo" width="600"/>
</p>

> A modern take on the classic Snake game, engineered with a focus on clean architecture, design patterns, and multiple AI-driven gameplay modes.

ViperVision is a comprehensive implementation of the Snake game built from the ground up using Java and the Processing library for rendering. More than just a game, it serves as a practical case study for applying robust software design principles like the State and Strategy patterns, resulting in a flexible, maintainable, and extensible codebase.

<p align="center">
  <img src="./docs/img/gameplay.gif" alt="ViperVision Gameplay Demo" width="600"/>
</p>

---

## ✨ Features

* **Classic Gameplay:** Smooth, grid-based movement, food consumption, and score tracking.
* **Multiple Game Modes:**
    * **Human Player:** Take control with the arrow keys in a responsive input-buffered system.
    * **Pathfinding AI:** Watch the snake autonomously navigate using algorithms like A* or Dijkstra to find the optimal path to the food.
    * **Genetic Algorithm AI (WIP):** Observe an AI that evolves and learns to play through generations of simulated evolution.
* **Polished UI:** Interactive menus, custom fonts, and improved game aesthetics.
* **Robust Architecture:** Built to be easily understood, modified, and extended.

---

## 🏗️ Architecture & Design

This project is engineered with a strong focus on applying proven software design principles:

* **Separation of Concerns:** Core logic is decoupled from rendering, input, and UI.
* **State Pattern:** Manages the game's states (Menu, Playing, Paused, GameOver) by encapsulating state-specific behaviors into their own classes. This makes adding or changing states clean and simple.
* **Strategy Pattern:** Dynamically switches the snake's control mechanism between different "strategies" (Human, Pathfinding AI, etc.), allowing for modular and interchangeable gameplay modes.
* **Singleton Pattern:** Used for managing stateless objects like game states, strategies, and asset loaders to ensure efficiency and a single point of access.
* **Composition over Inheritance:** Game objects are built with a focus on what they *do* (interfaces like `Drawable`, `Updatable`) rather than what they *are*, promoting flexibility.

### Key Diagrams

Here are some diagrams illustrating the project's structure and flow.

#### Package Structure

```mermaid
package "com.seb-asman.vipervision" {
    package "core" {
        class Game
        class Assets
        class GameConfig
        class ColorPalette
    }

    package "entities" {
        class Snake
        class Food
        class Board
    }

    package "states" {
        class State
        class PlayingState
        class MenuState
        class PausedState
        class GameOverState
    }

    package "strategies" {
        class ControlStrategy
        class HumanControlStrategy
        class FollowFoodStrategy
    }

    package "ui" {
        class MenuButton
    }

    states --> core : "uses"
    strategies --> core : "uses"
    entities --> core : "uses"
    ui --> core : "uses"
    states --> entities : "manages"
    states --> strategies : "uses"
    states --> ui : "creates"
}
```

#### State Machine

![State Diagram](./docs/diagrams/states.png)

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

### Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/SebAs-man/ViperVision.git
    cd ViperVision
    ```
2.  **Build the project using Maven:** This will compile the code and package it into an executable JAR in the `target/` directory.
    ```bash
    mvn clean package
    ```

---

## ▶️ How to Run

After a successful build, you can run the game from the command line:

```bash
java -jar target/ViperVision-1.0-SNAPSHOT.jar
```
*(Note: The JAR file name may vary slightly based on the version in your `pom.xml`)*

---

## 🎨 A Note on Design

This project's primary focus is on software architecture and clean, maintainable code. While efforts were made to create a pleasant user interface, the visual design choices reflect an engineer's perspective. Contributions, ideas, and pull requests from design-savvy individuals are most welcome!

---

## 📜 License

This project is distributed under the MIT License. See the [`LICENSE`](LICENSE) file for more information.

## 🙏 Acknowledgements

* The [Processing Foundation](https://processingfoundation.org/) for their amazing creative coding environment.
* You, for exploring this project!