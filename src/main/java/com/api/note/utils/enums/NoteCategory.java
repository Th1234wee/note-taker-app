package com.api.note.utils.enums;

public enum NoteCategory {
    PERSONAL("Personal"),
    WORK("Work"),
    EDUCATIONAL("Educational"),
    TRAVEL("Travel"),
    HEALTH_FITNESS("Health & Fitness"),
    RECIPES_COOKING("Recipes & Cooking"),
    FINANCIAL("Financial"),
    HOBBIES_INTERESTS("Hobbies & Interests"),
    SHOPPING_WISHLIST("Shopping & Wishlist"),
    GENERAL("General");

    private final String displayName;

    NoteCategory(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
        return displayName;
    }
}
