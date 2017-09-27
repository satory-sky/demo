package com.sso.flow.server.config;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.cert.X509Certificate;

/**
 * @author Original Author: Alexander Kontarero
 * @version 9/1/2014
 * @see © 2014 SSO - All Rights Reserved
 * See LICENSE file for further details
 */
public class WebClientDevWrapper {
    public static HttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs, String string)  {
                }

                public void checkServerTrusted(X509Certificate[] xcs, String string)  {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
// ASAP for 4.3 HttpComponents
/* remove:
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient(ccm, base.getParams());
/remove */
// add:
            Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", new MyConnectionSocketFactory())
                .build();
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(reg);
            CloseableHttpClient defaultHttpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
// /add

            /*BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("degiro", "d3g1r0"));
            defaultHttpClient.setCredentialsProvider(credentialsProvider);*/

            return defaultHttpClient;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

// ASAP for 4.3 HttpComponents
// add:
    static class MyConnectionSocketFactory implements ConnectionSocketFactory {
        @Override
        public Socket createSocket(final HttpContext context) throws IOException {
            InetSocketAddress socksaddr = (InetSocketAddress) context.getAttribute("socks.address");
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, socksaddr);
            return new Socket(proxy);
        }

        @Override
        public Socket connectSocket(
            final int connectTimeout,
            final Socket socket,
            final HttpHost host,
            final InetSocketAddress remoteAddress,
            final InetSocketAddress localAddress,
            final HttpContext context) throws IOException, ConnectTimeoutException {
            Socket sock;
            if (socket != null) {
                sock = socket;
            } else {
                sock = createSocket(context);
            }
            if (localAddress != null) {
                sock.bind(localAddress);
            }
            try {
                sock.connect(remoteAddress, connectTimeout);
            } catch (SocketTimeoutException ex) {
                throw new ConnectTimeoutException(ex, host, remoteAddress.getAddress());
            }
            return sock;
        }
    }
// /add
}
