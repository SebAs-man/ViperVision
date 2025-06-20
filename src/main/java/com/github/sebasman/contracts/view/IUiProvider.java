package com.github.sebasman.contracts.view;

import java.util.List;

/**
 * UiProvider is an interface that defines a contract for classes that provide UI components.
 */
public interface IUiProvider {
    /**
     * Returns the components to be rendered in the side panel.
     * @return A list of UI components to be rendered in the side panel.
     */
    List<IUiComponent> getUiComponents();
}
