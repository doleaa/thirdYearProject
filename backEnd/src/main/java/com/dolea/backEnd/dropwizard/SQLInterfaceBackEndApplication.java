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
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
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
    public void initialize(Bootstrap<Configuration> bootstrap) {}

    @Override
    public void run(Configuration configuration,
                    final Environment environment) {
        configureCors(environment);
        Injector injector = Guice.createInjector(new SQLInterfaceBackEndModule());

        List<Class<?>> resourceClasses = PackageScanner.getClassesInPackage("com.dolea.backEnd.resources");

        resourceClasses.stream()
                .filter(resourceClass -> resourceClass.getSuperclass().equals(BaseBackEndResource.class))
                .forEach(resourceClass -> environment.jersey().register(injector.getInstance(Key.get(resourceClass))));
    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

}
