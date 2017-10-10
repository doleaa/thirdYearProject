package com.dolea.backEnd.guice;

import com.dolea.backEnd.resources.BaseBackEndResource;
import com.dolea.backEnd.resources.HelloWorldResource;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.Collection;
import java.util.List;

/**
 * Created by alex on 27/09/2017.
 */
public class SQLInterfaceBackEndModule extends AbstractModule {
    protected void configure() {}

    @Provides
    @Singleton
    String getMyName() {
        return "Ales";
    }

    @Provides
    @Singleton
    Collection<BaseBackEndResource> getResources() {
        List toReturn = Lists.newArrayList();

        toReturn.add(new HelloWorldResource("Alex"));

        return toReturn;
    }
}
