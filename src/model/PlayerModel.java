package model;

//Player class - very basic for now
public class PlayerModel {
    String name;
    public int points;
    char color;
    public boolean successfulPlay = true;

    //Constructor for player
    public PlayerModel(String name,int points, char color) {
        this.name = name;
        this.points = points;
        this.color = color;
    }

    //Returns name
    public String getName() {
        return name;
    }

    //Returns points
    public int getPoints() {
        return points;
    }

    //Returns color - has to be either 'B' or 'W'
    public char getColor() {
        return color;
    }

    //Returns succesfulplay
    public boolean getSuccessfulPlay() {
        return successfulPlay;
    }

}