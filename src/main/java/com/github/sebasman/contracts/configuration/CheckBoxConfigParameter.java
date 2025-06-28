package com.github.sebasman.contracts.configuration;

import com.github.sebasman.contracts.view.IComponentFactoryVisitor;
import com.github.sebasman.contracts.view.IUiComponent;

/**
 * Describes a parameter best configured with a Checkbox.
 * @param key The key to the checkbox
 * @param label The label to display in the checkbox
 * @param initialValue The initial value
 */
public record CheckBoxConfigParameter
        (String key, String label, boolean initialValue) implements IConfigParameter {
    @Override
    public IUiComponent accept(IComponentFactoryVisitor factory) {
        return factory.visit(this);
    }
}
