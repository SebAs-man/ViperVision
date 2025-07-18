package com.github.sebasman.view.assets;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * Utility class for managing assets in the game.
 */
public final class Assets {
    // --- Flag ---

    private static boolean isLoaded = false;

    // --- Fonts ---

    public static PFont titleFont;
    public static PFont textFont;

    // --- Images ---

    // Food images
    public static PImage appleImage;
    public static PImage poisonImage;
    public static PImage goldenAppleImage;
    public static PImage capsuleImage;
    public static PImage roadBlockerImage;
    public static PImage speedSheetImage;
    public static PImage starImage;
    // HUD images
    public static PImage playImage;
    public static PImage searchAiImage;
    public static PImage geneticAiImage;
    public static PImage homeImage;
    public static PImage retryImage;
    public static PImage trophyImage;
    // Backgrounds images
    public static PImage backgroundPortalImage;
    // Components parts images
    public static PImage checkImage;
    public static PImage commandImage;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Assets() {}

    /**
     * Loads all game assets. Must be called only once
     * at startup, from the main class that extends PApplet.
     * @param p The instance of PApplet to use its load methods.
     */
    public static void load(PApplet p){
        if(isLoaded){
            System.err.println("Assets already loaded, skipping...");
            return; // Assets are already loaded, skip loading
        }
        // Set the flag to true to prevent reloading
        isLoaded = true;
        // Load fonts and images, handle exceptions if any
        System.out.println("Loading assets...");
        try{
            // Fonts
            titleFont = p.createFont("fonts/text-title.ttf", 40);
            textFont = p.createFont("fonts/text-base.ttf", 20);
            // Images
            appleImage = p.loadImage("images/apple.png");
            goldenAppleImage = p.loadImage("images/golden-apple.png");
            poisonImage = p.loadImage("images/poison.png");
            capsuleImage = p.loadImage("images/capsule.png");
            roadBlockerImage = p.loadImage("images/road-blocker.png");
            speedSheetImage = p.loadImage("images/speed-sheet.png");
            starImage = p.loadImage("images/star.png");
            homeImage = p.loadImage("images/home.png");
            playImage = p.loadImage("images/play.png");
            searchAiImage = p.loadImage("images/search.png");
            geneticAiImage = p.loadImage("images/genetic.png");
            retryImage = p.loadImage("images/retry.png");
            trophyImage = p.loadImage("images/trophy.png");
            backgroundPortalImage = p.loadImage("images/background-portal.png");
            checkImage = p.loadImage("images/check.png");
            commandImage = p.loadImage("images/command.png");
            System.out.println("Assets loaded successfully.");
        } catch (Exception e){
            System.err.println("Error loading resources: " + e.getMessage());
            p.exit(); // Exit the game if resources cannot be loaded
        }
    }
}
