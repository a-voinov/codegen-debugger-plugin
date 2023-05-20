package av.debugger.codegen.debug;

import com.intellij.xdebugger.XDebugSession;
import com.intellij.xdebugger.XDebugSessionListener;

import java.util.Objects;

public class DebugSessionListener implements XDebugSessionListener {
    private final XDebugSession debugSession;

    public DebugSessionListener(XDebugSession debugSession) {
        this.debugSession = debugSession;
    }

    @Override
    public void sessionPaused() {
        DebugSharedState.getInstance().cleanRefs();
        final var node = new DebugNode();
        final var currentStackFrame = this.debugSession.getCurrentStackFrame();
        Objects.requireNonNull(currentStackFrame, "Stack frame unexpectedly was null.");
        currentStackFrame.computeChildren(node);
    }

    @Override
    public void sessionResumed() {
        DebugSharedState.getInstance().cleanRefs();
    }
}
