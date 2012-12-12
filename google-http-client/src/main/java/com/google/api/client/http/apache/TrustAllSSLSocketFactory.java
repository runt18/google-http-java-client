/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.api.client.http.apache;

import com.google.api.client.http.javanet.NetHttpUtils;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

/**
 * SSL socket factory in which all X.509 certificates are trusted.
 *
 * <p>
 * Be careful! Using this is dangerous and should only be done in testing environments.
 * </p>
 *
 * @author Yaniv Inbar
 * @since 1.13
 */
class TrustAllSSLSocketFactory extends SSLSocketFactory {

  /** Wrapped Java SSL socket factory. */
  private final javax.net.ssl.SSLSocketFactory socketFactory =
      NetHttpUtils.trustAllSSLContext().getSocketFactory();

  TrustAllSSLSocketFactory() throws GeneralSecurityException {
    super(KeyStore.getInstance(KeyStore.getDefaultType()));
    setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
  }

  @Override
  public Socket createSocket() throws IOException {
    return socketFactory.createSocket();
  }

  @Override
  public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
      throws IOException, UnknownHostException {
    return socketFactory.createSocket(socket, host, port, autoClose);
  }
}
