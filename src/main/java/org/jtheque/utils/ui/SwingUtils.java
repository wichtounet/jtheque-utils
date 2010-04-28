package org.jtheque.utils.ui;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.utils.io.SimpleFilter;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import java.awt.Color;
import java.awt.Component;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

import org.jtheque.utils.ui.edt.Task;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.slf4j.LoggerFactory;

/**
 * Provide utility methods for Swing Components.
 *
 * @author Baptiste Wicht
 */
public final class SwingUtils {
    private static DisplayMode mode;
    private static GraphicsDevice device;

    private static Font defaultFont;

    private static JFileChooser chooser;

    /**
     * Private constructor, this class isn't instanciable.
     */
    private SwingUtils() {
        super();
    }

    /**
     * Load the display information for this computer.
     */
    private static void loadDisplayInfos() {
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = gEnv.getDefaultScreenDevice();
        mode = device.getDisplayMode();
    }

    /**
     * Return the insets of the screen.
     *
     * @return The insets
     */
    private static Insets getInsets() {
        return Toolkit.getDefaultToolkit().getScreenInsets(device.getDefaultConfiguration());
    }

    /**
     * Center a frame on the screen.
     *
     * @param frame The frame to be centered
     */
    public static void centerFrame(Window frame) {
        if (mode == null) {
            loadDisplayInfos();
        }

        frame.setLocation((getWidth() - frame.getWidth()) / 2,
                (getHeight() - frame.getHeight()) / 2);
    }

    /**
     * Return the height of the screen.
     *
     * @return The height
     */
    private static int getHeight() {
        if (mode == null) {
            loadDisplayInfos();
        }

        return mode.getHeight() - getInsets().bottom - getInsets().top;
    }

    /**
     * Return the width of the screen.
     *
     * @return The width
     */
    private static int getWidth() {
        if (mode == null) {
            loadDisplayInfos();
        }

        return mode.getWidth() - getInsets().left - getInsets().right;
    }

    /**
     * Return the JOptionPane parent.
     *
     * @param c The component.
     * @return The parent JOptionPane.
     */
    public static JOptionPane getOptionPane(Component c) {
        Component parent = c;

        while (parent != null) {
            if (parent instanceof JOptionPane) {
                return (JOptionPane) parent;
            }

            parent = parent.getParent();
        }

        return null;
    }

    /**
     * Set the option pane value.
     *
     * @param c     The component.
     * @param value The value to set to the JOptionPane.
     */
    public static void setOptionPaneValue(Component c, Object value) {
        JOptionPane optionPane = SwingUtils.getOptionPane(c);
        if (optionPane != null) {
            optionPane.setValue(value);
        }
    }

    /**
     * Create a button bar for actions.
     *
     * @param actions The actions to create the bar for.
     * @return A Bar containing a button for each action.
     */
    public static Component createButtonBar(Action... actions) {
        return createButtonBar(false, actions);
    }

    /**
     * Create a button bar.
     *
     * @param leftAligned A boolean flag indicating if we want a left to right alignment or not.
     * @param actions     The actions to add to the menu bar.
     * @return The builded button bar.
     */
    public static Component createButtonBar(boolean leftAligned, Action... actions) {
        ButtonBarBuilder builderButton = new ButtonBarBuilder();
        builderButton.getPanel().setBackground(Color.white);

        if (!leftAligned) {
            builderButton.addGlue();
        }

        builderButton.addActions(actions);

        if (leftAligned) {
            builderButton.addGlue();
        }

        return builderButton.getPanel();
    }

    /**
     * Add an action to execute when the validation key (Enter) is pressed.
     *
     * @param field  The field to validate.
     * @param action The action to execute on validate.
     */
    public static void addFieldValidateAction(JComponent field, Action action) {
        field.getActionMap().put("validate", action);
        field.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "validate");
    }

    /**
     * Limit the length of the field.
     *
     * @param field  The field to limit.
     * @param length The maximal length the field may contain.
     */
    public static void addFieldLengthLimit(JTextField field, int length) {
        DocumentFilter filterNom = new DocumentLengthFilterAvert(length, field);
        Document documentFieldNom = field.getDocument();
        ((AbstractDocument) documentFieldNom).setDocumentFilter(filterNom);
    }

    /**
     * Return the default font of the application.
     *
     * @return The default font.
     */
    public static Font getDefaultFont() {
        if (defaultFont == null) {
            defaultFont = new JLabel("Font").getFont();
        }

        return defaultFont;
    }

    public static void inEdtSync(Runnable runnable) {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(runnable);
            } catch (InterruptedException e) {
                LoggerFactory.getLogger(SwingUtils.class).error("inEdt sync interrupted", e);
            } catch (InvocationTargetException e) {
                LoggerFactory.getLogger(SwingUtils.class).error("inEdt sync interrupted", e);
            }
        }
    }

    /**
     * Executed the specified runnable in the EDT.
     *
     * @param runnable The runnable to run in EDT.
     */
    public static void inEdt(Runnable runnable) {
        if (SwingUtilities.isEventDispatchThread()) {
            runnable.run();
        } else {
            SwingUtilities.invokeLater(runnable);
        }
    }

    /**
     * Refresh the specified component.
     *
     * @param component The component to refresh.
     */
    public static void refresh(final Component component) {
        inEdt(new Runnable() {
            @Override
            public void run() {
                SwingUtilities.updateComponentTreeUI(component);
            }
        });
    }

    public static void execute(SimpleTask task) {
        inEdt(task.asRunnable());
    }

    public static <T> T execute(Task<T> task) {
        inEdt(task.asRunnable());

        return task.getResult();
    }

    public static String chooseFile(SimpleFilter filter) {
        File file = null;

        if (chooser == null) {
            chooser = new JFileChooser();
        }

        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (filter == null) {
            chooser.setAcceptAllFileFilterUsed(true);
        } else {
            chooser.addChoosableFileFilter(new SwingFileFilter(filter));
            chooser.setAcceptAllFileFilterUsed(false);
        }

        int answer = chooser.showOpenDialog(new JFrame());

        if (answer == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        return file == null ? null : file.getAbsolutePath();
    }
    
    public static String chooseDirectory() {
        File file = null;

        if (chooser == null) {
            chooser = new JFileChooser();
        }

        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnCode = chooser.showOpenDialog(new JFrame());

        if (returnCode == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }

        return file == null ? null : file.getAbsolutePath();
    }
}