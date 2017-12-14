package com.mattmcnew.plugin;

import org.cloudfoundry.client.CloudFoundryClient;
import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.DefaultCloudFoundryOperations;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public class SetupServices extends DefaultTask {

    private String username;
    private String password;
    private String apiHost;

    @Input
    public String getUsername() {
        return username;
    }

    @Input
    public String getPassword() {
        return password;
    }

    @Input
    public String getApiHost() {
        return apiHost;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    @TaskAction
    public void letsGo() throws Exception {

        DefaultConnectionContext connectionContext = DefaultConnectionContext.builder()
                .apiHost(apiHost)
                .build();

        PasswordGrantTokenProvider passwordGrantTokenProvider = PasswordGrantTokenProvider.builder()
                .password(password)
                .username(username)
                .build();

        CloudFoundryClient cloudFoundryClient = ReactorCloudFoundryClient.builder()
                .connectionContext(connectionContext)
                .tokenProvider(passwordGrantTokenProvider)
                .build();



        CloudFoundryOperations build = DefaultCloudFoundryOperations.builder()
                .cloudFoundryClient(cloudFoundryClient)
                .organization("mcnew")
                .space("bocce")
                .build();

        Provision provision = new Provision(build);
        provision.go();

//        System.out.printf("%nApplications:%n");
//        for (CloudApplication application : client.getApplications()) {
//            System.out.printf("  %s%n", application.getName());
//        }
//
//        System.out.printf("%nServices%n");
//        for (CloudService service : client.getServices()) {
//            System.out.printf("  %s\t(%s)%n", service.getName(), service.getLabel());
//        }
    }

}
