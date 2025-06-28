package com.github.sebasman.contracts.configuration;

import com.github.sebasman.contracts.view.IComponentFactoryVisitor;
import com.github.sebasman.contracts.view.IUiComponent;

/**
 * Describes a parameter best configured with a Slider.
 * @param key The key to the slider
 * @param label The label to display in the slider
 * @param min The minimum value
 * @param max The maximum value
 * @param initialValue The initial value
 */
public record SliderConfigParameter
        (String key, String label, float min, float max, float initialValue) implements IConfigParameter {
    @Override
    public IUiComponent accept(IComponentFactoryVisitor factory) {
        // Calls the specific method in the factory for sliders.
        return factory.visit(this);
    }
}
