package av.debugger.codegen.debug;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.xdebugger.impl.ui.tree.actions.XDebuggerTreeActionBase;
import com.intellij.xdebugger.impl.ui.tree.nodes.XValueNodeImpl;
import org.jetbrains.annotations.NotNull;
import av.debugger.codegen.CodeGenerator;
import av.debugger.codegen.JdiObjectAnalyzer;
import av.debugger.codegen.codemodel.ClassModel;

import java.awt.datatransfer.StringSelection;

public class DebugAction extends XDebuggerTreeActionBase {
    @Override
    protected void perform(XValueNodeImpl node, @NotNull String nodeName, AnActionEvent e) {
        final ClassModel classModel = new JdiObjectAnalyzer().createClassModel(
                DebugSharedState.getInstance().get(nodeName)
        );
        final String generatedCode = CodeGenerator.generate(classModel);
        if (generatedCode != null) {
            CopyPasteManager.getInstance().setContents(new StringSelection(generatedCode));
        }
    }
}
