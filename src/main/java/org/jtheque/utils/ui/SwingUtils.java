package org.jtheque.utils.ui;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.utils.io.SimpleFilter;
import org.jtheque.utils.ui.edt.SimpleTask;
import org.jtheque.utils.ui.edt.Task;

import org.slf4j.LoggerFactory;

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
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

/**
 * Provide utility methods for Swing Components.
 *
 * @author Baptiste Wicht
 */
public final class SwingUtils {
    private static final DisplayMode DISPLAY_MODE;
    private static final GraphicsDevice GRAPHICS_DEVICE;

    static {
        GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GRAPHICS_DEVICE = gEnv.getDefaultScreenDevice();
        DISPLAY_MODE = GRAPHICS_DEVICE.getDisplayMode();
    }

    private static Font defaultFont;
    private static JFileChooser chooser;

    /**
     * Private constructor, this class isn't instanciable.
     */
    private SwingUtils() {
        throw new AssertionError();
    }

    /**
     * Return the insets of the screen.
     *
     * @return The insets
     */
    private static Insets getInsets() {
        return Toolkit.getDefaultToolkit().getScreenInsets(GRAPHICS_DEVICE.getDefaultConfiguration());
    }

    /**
     * Center a frame on the screen.
     *
     * @param frame The frame to be centered
     */
    public static void centerFrame(Window frame) {
        frame.setLocation((getWidth() - frame.getWidth()) / 2,
                (getHeight() - frame.getHeight()) / 2);
    }

    /**
     * Return the height of the screen.
     *
     * @return The height
     */
    private static int getHeight() {
        return DISPLAY_MODE.getHeight() - getInsets().bottom - getInsets().top;
    }

    /**
     * Return the width of the screen.
     *
     * @return The width
     */
    private static int getWidth() {
        return DISPLAY_MODE.getWidth() - getInsets().left - getInsets().right;
    }

    /**
     * Return the JOptionPane parent.
     *
     * @param c The component.
     *
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
        JOptionPane optionPane = getOptionPane(c);
        if (optionPane != null) {
            optionPane.setValue(value);
        }
    }

    /**
     * Create a button bar for actions.
     *
     * @param actions The actions to create the bar for.
     *
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
     *
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
     * Add an action to execute when the validation key (Enter) is pressed on all the given components.
     *
     * @param action     The action to execute on validate.
     * @param components The components to apply the action on.
     */
    public static void addFieldsValidateAction(Action action, JComponent... components) {
        for (JComponent component : components) {
            addFieldValidateAction(component, action);
        }
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

    /**
     * Execute the specified runnable in the EDT.
     *
     * @param runnable The runnable to run in EDT.
     */
    public static void inEdt(Runnable runnable) {
        if (isEDT()) {
            runnable.run();
        } else {
            EventQueue.invokeLater(runnable);
        }
    }

    /**
     * Execute the specified runnable in the EDT and wait for its end.
     *
     * @param runnable The runnable to run in EDT.
     */
    public static void inEdtSync(Runnable runnable) {
        if (isEDT()) {
            runnable.run();
        } else {
            try {
                EventQueue.invokeAndWait(runnable);
            } catch (InterruptedException e) {
                LoggerFactory.getLogger(SwingUtils.class).error(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                LoggerFactory.getLogger(SwingUtils.class).error(e.getMessage(), e);
            }
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

    /**
     * Execute the given task in EDT.
     *
     * @param task The task to execute.
     */
    public static void execute(SimpleTask task) {
        inEdt(task.asRunnable());
    }

    /**
     * Execute the given task in EDT and return its result.
     *
     * @param task The task to execute.
     * @param <T>  The return type of the task.
     *
     * @return The return of the task.
     */
    public static <T> T execute(Task<T> task) {
        inEdtSync(task.asRunnable());

        return task.getResult();
    }

    /**
     * Ask the user of a file.
     *
     * @param filter The filter to use to filter the file chooser view.
     *
     * @return The File chosen by the user or null if the user has canceled the view.
     */
    public static File chooseFile(SimpleFilter filter) {
        if (chooser == null) {
            chooser = new JFileChooser();
        }

        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (filter == null) {
            chooser.setAcceptAllFileFilterUsed(true);
        } else {
            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setFileFilter(new SwingFileFilter(filter));
        }

        int answer = chooser.showOpenDialog(new JFrame());

        if (answer == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }

        return null;
    }

    /**
     * Ask the user of a directory.
     *
     * @return The directory chosen by the user or null if the user has canceled the view.
     */
    public static File chooseDirectory() {
        if (chooser == null) {
            chooser = new JFileChooser();
        }

        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnCode = chooser.showOpenDialog(new JFrame());

        if (returnCode == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }

        return null;
    }

    public static void assertNotEDT(String point){
        if(isEDT()){
            LoggerFactory.getLogger(SwingUtils.class).error("EDT Violation : {} must not be called in EDT", point);
        }
    }

    public static boolean isEDT() {
        return EventQueue.isDispatchThread();
    }
}