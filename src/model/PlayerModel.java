package model;

public class PlayerModel {
    String name;
    int points;
    char color;

    public PlayerModel(String name,int points, char color) {
        this.name = name;
        this.points = points;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public char getColor() {
        return color;
    }

}