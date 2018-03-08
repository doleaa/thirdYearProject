package com.dolea.backEnd.guice;

import com.dolea.backEnd.service.*;
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

    @Provides
    @Singleton
    ActionManager getActionManager(AuditService auditService, ScriptService scripService) {
        return new ActionManager(auditService, scripService);
    }

    @Provides
    @Singleton
    AuditService getAuditService() {
        return new AuditServiceImpl();
    }

    @Provides
    @Singleton
    ScriptService getScriptService() { return new ScriptServiceImpl(); }

    @Provides
    @Singleton
    DashboardService getDashboardService() {
        return new DashboardServiceImpl();
    }
}
