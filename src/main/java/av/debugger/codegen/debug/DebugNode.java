package av.debugger.codegen.debug;

import com.intellij.debugger.engine.JavaValue;
import com.intellij.debugger.engine.evaluation.EvaluateException;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.xdebugger.frame.XCompositeNode;
import com.intellij.xdebugger.frame.XDebuggerTreeNodeHyperlink;
import com.intellij.xdebugger.frame.XValueChildrenList;
import com.sun.jdi.ObjectReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Optional;

public class DebugNode implements XCompositeNode {
    private static final Logger log = Logger.getInstance(DebugNode.class);

    @Override
    public void addChildren(@NotNull XValueChildrenList children, boolean last) {
        for (int i = 0; i < children.size(); i++) {
            var jVal = (JavaValue)children.getValue(i);
            var objRef = tryGetObjectReference(jVal);
            objRef.ifPresent(ref -> DebugSharedState.getInstance().putRef(jVal.getName(), ref));
        }
    }

    private Optional<ObjectReference> tryGetObjectReference(@NotNull JavaValue jVal) {
        try {
            var ref = jVal.getDescriptor().calcValue(jVal.getEvaluationContext());
            return (ref instanceof ObjectReference) ? Optional.of((ObjectReference)ref) : Optional.empty();
        } catch (EvaluateException e) {
            log.error(e);
            return Optional.empty();
        }
    }

    @Override
    public void tooManyChildren(int remaining) {
        // NOOP
    }

    @Override
    public void setAlreadySorted(boolean alreadySorted) {
        // NOOP
    }

    @Override
    public void setErrorMessage(@NotNull String errorMessage) {
        // NOOP
    }

    @Override
    public void setErrorMessage(@NotNull String errorMessage, @Nullable XDebuggerTreeNodeHyperlink link) {
        // NOOP
    }

    @Override
    public void setMessage(@NotNull String message, @Nullable Icon icon, @NotNull SimpleTextAttributes attributes, @Nullable XDebuggerTreeNodeHyperlink link) {
        // NOOP
    }
}
