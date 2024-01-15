package com.digiduty.qurancounteradmin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;


@Configuration
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.client.certificate}")
    private String certificateBase64;


    @Override
    public ClientConfiguration clientConfiguration() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .usingSsl(this.getSSLConetxt())
                .withBasicAuth("elastic", "0Avth*Q41A1u5*r2k4K1")
                .build();
        return clientConfiguration;
    }

    private SSLContext getSSLConetxt() {
        try {

            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            Certificate ca;
            Resource resource = new ClassPathResource("http_ca.crt");

            try (InputStream certificateInputStream = new FileInputStream(resource.getFile())) {
                ca = cf.generateCertificate(certificateInputStream);
            }

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tmf.getTrustManagers(), null);
            return context;
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }


}
