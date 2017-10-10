package com.dolea.backEnd.server;

import com.dolea.backEnd.dropwizard.SQLInterfaceBackEndApplication;

import java.io.File;

public class SQLInterfaceBackEndRunner {

    public static void main(String[] args) throws Exception {
        SQLInterfaceBackEndApplication.main(
            new String[] {
                "server",
                new File("src/main/resources/Config.yaml").getAbsolutePath()
            }
        );
    }
}
