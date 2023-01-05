package ru.liner.facerapp.engine.model;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public class ImmutableWifiState implements WifiState {
    private int level;

    public ImmutableWifiState(int level) {
        this.level = level;
    }

    @Override // com.jeremysteckling.facerrel.lib.model.WifiState
    public int getLevel() {
        return this.level;
    }
}