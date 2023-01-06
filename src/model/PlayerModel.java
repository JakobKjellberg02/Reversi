package model;

//Player class - very basic for now
public class PlayerModel {
    String name;
    public int points;
    char color;
    public boolean successfulPlay = true;

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

    public boolean getSuccessfulPlay() {
        return successfulPlay;
    }

}