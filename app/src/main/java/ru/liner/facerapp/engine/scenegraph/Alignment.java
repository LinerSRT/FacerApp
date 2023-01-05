package ru.liner.facerapp.engine.scenegraph;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 03.01.2023, вторник
 **/
public enum Alignment {
    TOP_LEFT,
    TOP,
    TOP_RIGHT,
    LEFT,
    CENTER,
    RIGHT,
    BOTTOM_LEFT,
    BOTTOM,
    BOTTOM_RIGHT;

    public float getAlignScalarX(Alignment defaultAlignment) {
        final boolean b = CENTER.equals(defaultAlignment) || TOP.equals(defaultAlignment) || BOTTOM.equals(defaultAlignment);
        final boolean b1 = TOP_LEFT.equals(defaultAlignment) || LEFT.equals(defaultAlignment) || BOTTOM_LEFT.equals(defaultAlignment);
        switch (this) {
            case TOP_LEFT:
            case LEFT:
            case BOTTOM_LEFT:
                if (b) {
                    return 0.5f;
                }
                if (RIGHT.equals(defaultAlignment) || TOP_RIGHT.equals(defaultAlignment) || BOTTOM_RIGHT.equals(defaultAlignment)) {
                    return 1.0f;
                }
                break;
            case CENTER:
            case TOP:
            case BOTTOM:
                if (b1) {
                    return -0.5f;
                }
                if (TOP_RIGHT.equals(defaultAlignment) || RIGHT.equals(defaultAlignment) || BOTTOM_RIGHT.equals(defaultAlignment)) {
                    return 0.5f;
                }
                break;
            case RIGHT:
            case TOP_RIGHT:
            case BOTTOM_RIGHT:
                if (b1) {
                    return -1.0f;
                }
                if (b) {
                    return -0.5f;
                }
                break;
        }
        return 0.0f;
    }

    public float getAlignScalarY(Alignment defaultAlignment) {
        final boolean b = CENTER.equals(defaultAlignment) || LEFT.equals(defaultAlignment) || RIGHT.equals(defaultAlignment);
        final boolean b1 = BOTTOM_RIGHT.equals(defaultAlignment) || BOTTOM.equals(defaultAlignment) || BOTTOM_LEFT.equals(defaultAlignment);
        final boolean b2 = TOP.equals(defaultAlignment) || TOP_RIGHT.equals(defaultAlignment) || TOP_LEFT.equals(defaultAlignment);
        switch (this) {
            case TOP_LEFT:
            case TOP:
            case TOP_RIGHT:
                if (b) {
                    return 0.5f;
                }
                if (b1) {
                    return 1.0f;
                }
                break;
            case LEFT:
            case CENTER:
            case RIGHT:
                if (b2) {
                    return -0.5f;
                }
                if (b1) {
                    return 0.5f;
                }
                break;
            case BOTTOM_LEFT:
            case BOTTOM:
            case BOTTOM_RIGHT:
                if (b2) {
                    return -1.0f;
                }
                if (b) {
                    return -0.5f;
                }
                break;
        }
        return 0.0f;
    }

}
