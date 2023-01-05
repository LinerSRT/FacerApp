package ru.liner.facerapp.engine;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public enum Shape {
    CIRCLE,
    LINE,
    TRIANGLE,
    RECTANGLE,
    PENTAGON,
    HEXAGON,
    SEPTAGON,
    OCTAGON;

    public int getNumSides() {
        switch (this) {
            case CIRCLE:
                return 0;
            case LINE:
                return 2;
            case TRIANGLE:
            default:
                return 3;
            case RECTANGLE:
                return 4;
            case PENTAGON:
                return 5;
            case HEXAGON:
                return 6;
            case SEPTAGON:
                return 7;
            case OCTAGON:
                return 8;
        }
    }
}