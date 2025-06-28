package com.github.sebasman.contracts.view;

import com.github.sebasman.contracts.configuration.CheckBoxConfigParameter;
import com.github.sebasman.contracts.configuration.SliderConfigParameter;

/**
 * Defines the contract for a Visitor which knows how to “visit” each type of
 * configuration parameter to create a UI component.
 */
public interface IComponentFactoryVisitor {
    /**
     * Creates a SliderComponent from its configuration descriptor.
     * @param config The data object describing the slider.
     * @return a new instance of SliderComponent.
     */
    IUiComponent visit(SliderConfigParameter config);

    /**
     * Creates a CheckboxComponent from its configuration descriptor.
     * @param config The data object describing the checkbox.
     * @return a new instance of CheckboxComponent.
     */
    IUiComponent visit(CheckBoxConfigParameter config);
}
