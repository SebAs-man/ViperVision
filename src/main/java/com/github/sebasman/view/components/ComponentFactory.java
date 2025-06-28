package com.github.sebasman.view.components;

import com.github.sebasman.contracts.configuration.CheckBoxConfigParameter;
import com.github.sebasman.contracts.configuration.SliderConfigParameter;
import com.github.sebasman.contracts.view.IComponentFactoryVisitor;
import com.github.sebasman.contracts.view.IUiComponent;

/**
 * Factory responsible for creating instances of UI components
 * from configuration descriptors.
 */
public final class ComponentFactory implements IComponentFactoryVisitor {
    @Override
    public IUiComponent visit(SliderConfigParameter config){
        return new Slider(
                config.label(),
                config.key(),
                config.min(),
                config.max(),
                config.initialValue()
        );
    }

    @Override
    public IUiComponent visit(CheckBoxConfigParameter config){
        return new CheckBox(
                config.label(),
                config.key(),
                config.initialValue()
        );
    }
}
