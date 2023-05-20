package av.debugger.codegen.debug;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.xdebugger.XDebugProcess;
import com.intellij.xdebugger.XDebuggerManagerListener;
import org.jetbrains.annotations.NotNull;

public class DebugManagerListener implements XDebuggerManagerListener {
    private static final Logger log = Logger.getInstance(DebugManagerListener.class);

    @Override
    public void processStarted(@NotNull XDebugProcess debugProcess) {
        final var debugSession = debugProcess.getSession();

        if (debugSession != null) {
            debugSession.addSessionListener(new DebugSessionListener(debugSession));
        } else {
            log.info("No debugging session active but plugin was invoked.");
        }
    }
}
