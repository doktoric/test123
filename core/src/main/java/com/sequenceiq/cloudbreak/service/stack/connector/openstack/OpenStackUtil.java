package com.sequenceiq.cloudbreak.service.stack.connector.openstack;

import static org.apache.commons.lang3.StringUtils.deleteWhitespace;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.openstack4j.api.OSClient;
import org.openstack4j.core.transport.internal.HttpLoggingFilter;
import org.openstack4j.openstack.OSFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.sequenceiq.cloudbreak.EnvironmentVariableConfig;
import com.sequenceiq.cloudbreak.domain.OpenStackCredential;

@Component
@Lazy
public class OpenStackUtil {

    private static final String CB_KEYPAIR_NAME = "cb-keypair-";

    @Inject
    private PBEStringCleanablePasswordEncryptor encryptor;

    @Value("${cb.openstack.api.debug:" + EnvironmentVariableConfig.CB_OPENSTACK_API_DEBUG + "}")
    private boolean debug;

    @PostConstruct
    public void init() {
        System.getProperties().setProperty(HttpLoggingFilter.class.getName(), Boolean.toString(debug));
    }

    public OSClient createOSClient(OpenStackCredential credential) {
        return OSFactory.builder().endpoint(credential.getEndpoint())
                .credentials(encryptor.decrypt(credential.getUserName()), encryptor.decrypt(credential.getPassword()))
                .tenantName(credential.getTenantName())
                .authenticate();
    }

    public String getKeyPairName(OpenStackCredential credential) {
        return CB_KEYPAIR_NAME + deleteWhitespace(credential.getName().toLowerCase());
    }

}
