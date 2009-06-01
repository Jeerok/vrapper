package net.sourceforge.vrapper.vim.modes;

import net.sourceforge.vrapper.keymap.KeyStroke;
import net.sourceforge.vrapper.keymap.vim.ConstructorWrappers;
import net.sourceforge.vrapper.vim.EditorAdaptor;
import net.sourceforge.vrapper.vim.modes.commandline.AbstractCommandParser;

public abstract class AbstractCommandLineMode extends AbstractMode {

    private AbstractCommandParser parser;

    protected abstract char activationChar();

    protected abstract AbstractCommandParser createParser();

    public AbstractCommandLineMode(EditorAdaptor editorAdaptor) {
        super(editorAdaptor);
    }

    public void enterMode(Object... args) {
        isEnabled = true;
        parser = createParser();
        handleKey(ConstructorWrappers.key(activationChar()));
    }

    public void leaveMode() {
        isEnabled = false;
        parser = null;
    }

    public boolean handleKey(KeyStroke stroke) {
        parser.type(stroke);
        String buffer = isEnabled ? parser.getBuffer() : "";
        editorAdaptor.getUserInterfaceService().setCommandLine(buffer);
        return true;
    }

}