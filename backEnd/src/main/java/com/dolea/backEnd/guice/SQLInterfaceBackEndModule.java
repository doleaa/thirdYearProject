package com.dolea.backEnd.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * Created by alex on 27/09/2017.
 */
public class SQLInterfaceBackEndModule extends AbstractModule {
    protected void configure() {}

    @Provides
    @Singleton
    String getMyName() {
        return "Alex";
    }
}
