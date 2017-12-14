package com.mattmcnew.plugin;

import org.cloudfoundry.operations.CloudFoundryOperations;
import org.cloudfoundry.operations.services.*;

public class Provision {

    private CloudFoundryOperations cloudFoundryOperations;

    public Provision(CloudFoundryOperations cloudFoundryOperations) {
        this.cloudFoundryOperations = cloudFoundryOperations;
    }

    public void go() {

        cloudFoundryOperations.services()
                .createInstance(
                        CreateServiceInstanceRequest.builder()
                                .planName("turtle")
                                .serviceInstanceName("test-example")
                                .serviceName("elephantsql")
                                .build()
                ).block();

        cloudFoundryOperations.services()
                .createServiceKey(
                        CreateServiceKeyRequest.builder()
                                .serviceInstanceName("test-example")
                                .serviceKeyName("shared_key")
                                .build()
                ).block();


        ServiceKey sharedKey = cloudFoundryOperations.services()
                .getServiceKey(
                        GetServiceKeyRequest.builder()
                                .serviceInstanceName("test-example")
                                .serviceKeyName("shared_key")
                                .build()
                ).block();

        cloudFoundryOperations.services()
                .createUserProvidedInstance(
                        CreateUserProvidedServiceInstanceRequest.builder()
                                .name("cups-service")

                                .credentials(sharedKey.getCredentials())
                                .build()
                ).block();
    }
}
