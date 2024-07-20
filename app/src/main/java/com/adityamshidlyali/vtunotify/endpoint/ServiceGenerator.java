package com.adityamshidlyali.vtunotify.endpoint;

import android.content.Context;

import com.adityamshidlyali.vtunotify.R;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ServiceGenerator {

    private static OkHttpClient generateSecureOkHttpClient(Context context) {
        // Create a simple builder for our http client, this is only por example purposes
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        // Here you may wanna add some headers or custom setting for your builder
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream cert = context.getResources().openRawResource(R.raw.vtu_cer);
            Certificate ca = cf.generateCertificate(cert);
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgo = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgo);
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, tmf.getTrustManagers(), null);

            client.sslSocketFactory(sslContext.getSocketFactory(),
                    (X509TrustManager) tmf.getTrustManagers()[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return client.build();
    }

    public static Retrofit generateService(Context context, String VTU_URL) {
        return new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(VTU_URL)
                .client(ServiceGenerator.generateSecureOkHttpClient(context)).build();
    }
}
