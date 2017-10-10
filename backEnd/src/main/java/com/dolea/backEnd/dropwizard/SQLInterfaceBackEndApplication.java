package com.dolea.backEnd.dropwizard;

import com.dolea.backEnd.guice.SQLInterfaceBackEndModule;
import com.dolea.backEnd.resources.BaseBackEndResource;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.util.Types;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.Collection;

/**
 * Created by alex on 27/09/2017.
 */
public class SQLInterfaceBackEndApplication extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new SQLInterfaceBackEndApplication().run(args);
    }

    @Override
    public String getName() {
        return "SQLInterfaceBackEndApplication";
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {

    }

//    @Override
//    public void run(Configuration configuration,
//                    final Environment environment) {
//        Injector injector = Guice.createInjector(new SQLInterfaceBackEndModule());
//        Collection<BaseBackEndResource> resources = (Collection<BaseBackEndResource>)
//                injector.getInstance(Key.get(Types.newParameterizedType(Collection.class, BaseBackEndResource.class)));
//
//        resources.forEach(resource -> environment.jersey().register(resource));
//    }

    @Override
    public void run(Configuration configuration,
                    final Environment environment) {
        Injector injector = Guice.createInjector(new SQLInterfaceBackEndModule());

//        Reflections reflections

        Collection<BaseBackEndResource> resources = (Collection<BaseBackEndResource>)
                injector.getInstance(Key.get(Types.newParameterizedType(Collection.class, BaseBackEndResource.class)));

        resources.forEach(resource -> environment.jersey().register(resource));
    }
}
