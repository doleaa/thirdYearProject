package com.dolea.backEnd.dropwizard;

import com.dolea.backEnd.guice.SQLInterfaceBackEndModule;
import com.dolea.backEnd.resources.BaseBackEndResource;
import com.dolea.backEnd.util.PackageScanner;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.List;

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

    @Override
    public void run(Configuration configuration,
                    final Environment environment) {
        Injector injector = Guice.createInjector(new SQLInterfaceBackEndModule());

        List<Class<?>> resourceClasses = PackageScanner.getClassesInPackage("com.dolea.backEnd.resources");

        resourceClasses.stream()
                .filter(resourceClass -> resourceClass.getSuperclass().equals(BaseBackEndResource.class))
                .forEach(resourceClass -> environment.jersey().register(injector.getInstance(Key.get(resourceClass))));
    }
}
