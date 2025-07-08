package com.github.sebasman.contracts.presenter;

import com.github.sebasman.contracts.configuration.IConfigParameter;

import java.util.List;

/**
 * UiProvider is an interface that defines a contract for classes that provide UI components.
 */
public interface IUiProvider {
    /**
     * Returns a list of the parameters that this strategy needs to configure.
     * Presenter will use this list to build the UI dynamically.
     * @return a list of configuration descriptors.
     */
    List<IConfigParameter> getConfigurationParameters();

    /**
     * Subscribes this instance of the strategy to the configuration events.
     * It must be called by the state that activates it.
     */
    void subscribeToEvents();

    /**
     * Describes this instance of the event strategy.
     * It is crucial to call this method to prevent “zombie listeners.”
     */
    void unsubscribeFromEvents();
}
