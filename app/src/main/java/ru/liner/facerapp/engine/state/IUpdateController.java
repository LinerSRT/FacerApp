package ru.liner.facerapp.engine.state;

public interface IUpdateController {
    boolean canUpdate();

    void onUpdateFailed();

    void onUpdateSuccess();

}
