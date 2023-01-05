package ru.liner.facerapp.engine.state;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public final class AlwaysUpdateController implements IUpdateController {
    @Override // com.jeremysteckling.facerrel.lib.sync.local.util.IUpdateController
    public boolean canUpdate() {
        return true;
    }

    @Override // com.jeremysteckling.facerrel.lib.sync.local.util.IUpdateController
    public void onUpdateSuccess() {
    }

    @Override // com.jeremysteckling.facerrel.lib.sync.local.util.IUpdateController
    public void onUpdateFailed() {
    }
}