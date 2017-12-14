package com.mattmcnew.plugin;


import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class CfDeploy implements Plugin<Project> {

    static final String TASK_NAME = "setupServices";

    @Override
    public void apply(Project target) {
        target.getTasks().create(TASK_NAME, SetupServices.class);
    }
}
