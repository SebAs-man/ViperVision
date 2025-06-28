package com.github.sebasman.contracts.configuration;

import com.github.sebasman.contracts.view.IComponentFactoryVisitor;
import com.github.sebasman.contracts.view.IUiComponent;

/**
 * Contract for a configuration parameter.
 * The ‘accept’ method allows a ‘visitor’ (the factory) to create a component
 * for this parameter without the parameter knowing the concrete factory.
 */
public interface IConfigParameter {
    /**
     * Accepts a visitor to create a UI component.
     * @param visitor The visitor object (our factory) that will create the component.
     * @return The UI component built.
     */
    IUiComponent accept(IComponentFactoryVisitor visitor);
}
