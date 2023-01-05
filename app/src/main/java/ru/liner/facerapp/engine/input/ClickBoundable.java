package ru.liner.facerapp.engine.input;

import ru.liner.facerapp.engine.bounds.Bound2D;

public interface ClickBoundable {
    Bound2D getClickBound();

    boolean isClickable();

    boolean onClicked();

}
