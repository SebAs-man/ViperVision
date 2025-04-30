# SnakeAI (Java with Processing)

## Description

This project is an implementation of the classic Snake game using Java and the Processing library for graphics and interaction. The primary goal is to serve as a learning platform for exploring Artificial Intelligence (AI) and Machine Learning (ML) concepts, specifically by developing an AI agent that learns to play the game autonomously. This project deliberately uses Java to explore AI/ML outside the more common Python ecosystem.

The visual style is inspired by Google's Snake game, aiming for a clean and fluid presentation.

## Current Features

* Classic Snake gameplay mechanics: grid-based movement, apple consumption, snake growth.
* Visual rendering using the Processing 4 core library.
* Game state management:
    * Main Menu (Player vs. AI selection)
    * Playing (Human-controlled)
    * Paused
    * Game Over screen with Retry/Menu options.
* Player control via keyboard arrow keys.
* Input buffering for responsive direction changes.
* Timer-based game speed, decoupled from frame rate.
* Smooth visual movement using frame interpolation [Opcional: si finalmente dejaste la interpolación] / Discrete cell-based movement rendered at high framerate [Opcional: si quitaste la interpolación].
* Collision detection (walls, self, apple).
* Score and high-score tracking (session-based).
* Interactive UI buttons with hover effects.
* Customizable assets (fonts, images) loaded from resources.
* Use of external classes (`Button`, `Apple`, `Snake`) for organization.

## Project Goal & Future Work

The main objective is to implement an AI player for the Snake game. This development path includes:

1.  **AI Agent:** Implementing the logic for an AI-controlled snake.
2.  **Decision Making:** Designing algorithms for the AI to decide its next move (e.g., pathfinding, heuristics).
3.  **Learning:** Exploring and implementing Machine Learning techniques, likely **Reinforcement Learning (RL)** (like Q-Learning or Deep Q-Networks), to enable the AI agent to learn optimal strategies through gameplay.
4.  **Training Environment:** Setting up ways to train the AI efficiently.
5.  **Evaluation:** Comparing AI performance against human play or other benchmarks.

## Technology Stack

* **Language:** Java (Developed using JDK 24)
* **Graphics/Interaction:** Processing 4 Core Library (`core.jar`, etc.)
* **IDE:** IntelliJ IDEA (Ultimate/Community)

## How to Run

1.  **Clone:** Clone this repository to your local machine.
2.  **JDK:** Ensure you have a compatible Java Development Kit (JDK 17 or later recommended) installed and configured in your system PATH.
3.  **Processing Library:** Download the Processing 4 Core library from [processing.org/download/](https://processing.org/download/) (choose the download for your OS, e.g., Linux `.tar.gz`, extract it).
4.  **IDE Setup (IntelliJ IDEA Example):**
    * Open the cloned project folder in IntelliJ IDEA.
    * Go to `File` -> `Project Structure...` -> `Libraries`.
    * Click the `+` icon and select `Java`.
    * Navigate to the extracted Processing folder and inside it, go to the `core/library` subfolder.
    * Select `core.jar`. It's also highly recommended to select `gluegen-rt.jar` and `jogl-all.jar` from the same folder for graphics compatibility. Click `OK`.
    * Apply the changes.
5.  **Run:** Locate the `SnakeGame.java` file (or your main class containing `public static void main(String[] args)`). Right-click on it and select "Run 'SnakeGame.main()'".

---
