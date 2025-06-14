package com.github.sebasman.core.interfaces.ui;

import java.util.List;

/**
 * UiProvider is an interface that defines a contract for classes that provide UI components.
 */
public interface UiProvider {
    /**
     * Returns a list of UI components that should be displayed in the side panel.
     * @return A list of UI components to be displayed in the side panel.
     */
    List<UiComponent> getSidePanelComponents();
}
