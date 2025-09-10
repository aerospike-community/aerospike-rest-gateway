# Installation and Configuration

The Aerospike REST Gateway is a Spring Boot Application. It can either be run directly from the git repo with `./gradlew bootRun`, or built into a `.jar` file or Docker image.

:::note
The REST Gateway was previously referred to as the REST Client.
:::

## 1 - Dependencies

- Java 17 for Gateway 2.0.1 and newer.  Java 8 for Gateway 1.11.0 and older.
- An Aerospike Server to be installed and reachable.

## 2 - Installation

### Downloading a JAR file

The latest version of the REST Gateway `.jar` file can be found at: [REST Gateway Releases](https://download.aerospike.com/download/client/rest)

### Running From Source

For development purposes it is possible to

- Clone the [Aerospike REST Gateway](https://github.com/aerospike/aerospike-rest-gateway)
- From the root directory run `./gradlew bootRun`

### Building a JAR file

To build a copy of the `.jar` file for the REST Gateway:

- Clone the [Aerospike REST Gateway](https://github.com/aerospike/aerospike-rest-gateway)
- From the root directory run `./gradlew build`

- The `.jar` will be located in the `build/libs` directory

### Running from an executable JAR

```txt
java -jar build/libs/aerospike-rest-gateway-<VERSION>.jar
```

The fully executable jar contains an extra script at the front of the file which allows you to symlink your Spring Boot jar to init.d or use a systemd script. 
For more information, click the following links:
* [Installation as an init.d service](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#deployment-service)
* [Installation as a systemd service](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#deployment-systemd-service)

#### Complete example of running the REST gateway using a jar

```txt
java -server -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=8082 -Dcom.sun.management.jmxremote.rmi.port=8082 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -XX:+UseG1GC -Xms2048m -Xmx2048m -jar ./as-rest-client-X.X.X.jar --aerospike.restclient.hostname=localhost --aerospike.restclient.clientpolicy.user=*** --aerospike.restclient.clientpolicy.password=*** --logging.file.name=/var/log/restclient/asproxy.log
```

For more JVM command-line options, see the [documentation](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html).

### Building a Docker image

- Clone the [Aerospike REST Gateway](https://github.com/aerospike/aerospike-rest-gateway)
- Install [Docker](https://docs.docker.com/install/)
- From the root directory run

```txt
docker build -t aerospike-rest-gateway .
```

## 3 - Configuration

### REST server configuration

The REST gateway is a Spring Boot application and the container can be configured using, for example, path arguments at startup. 

By default, the server will start at port `8080` and will have no prefix other than the version, so the base URL would be `https://localhost:8080/v1/`. If, for example, your application used port `5150` and had the basename `party` that could be configured at startup using the command `java -jar /path/to/jar --server.servlet.context-path=/party --server.port=5150`

There are a large number of properties that may be set. For the common set (where you might change the log level for example) [see here](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#core-properties) and for the server properties (where you might [as in the above example] change the port number) [see here](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#server-properties).

### Aerospike server configuration

By default, the REST Gateway looks for an Aerospike Server available at `localhost:3000` . The following environment variables allow specification of a different host/port.

* `server.port` Change the port the REST Gateway is listening on (default: 8080)
* `aerospike.restclient.hostname` The IP address or Hostname of a seed node in the cluster (default: `localhost`)
**Note:** If TLS is being utilized, `aerospike.restclient.hostlist` should be used instead of this variable.
* `aerospike.restclient.port` The port to communicate with the Aerospike cluster over. (default: `3000`)
* `aerospike.restclient.hostlist` A comma separated list of cluster hostnames, (optional TLS names) and ports. If this is specified, it overrides the previous two environment variables. The format is described below:

```txt
    The string format is : hostname1[:tlsname1][:port1],...
    * Hostname may also be an IP address in the following formats.
    *
    * IPv4: xxx.xxx.xxx.xxx
    * IPv6: [xxxx:xxxx:xxxx:xxxx:xxxx:xxxx:xxxx:xxxx]
    * IPv6: [xxxx::xxxx]
    *
    * IPv6 addresses must be enclosed by brackets.
    * tlsname and port are optional.
    */
```
Example:

```txt
java -jar as-rest-client-*.jar --aerospike.restclient.hostname=172.17.0.3 --server.port=9876
```
### Authentication

The REST Gateway also allows authentication to an Aerospike Enterprise edition server with security enabled. The following environment variables are used to find authentication information.

* `aerospike.restclient.clientpolicy.user` This is the name of a user registered with the Aerospike database. This variable is only needed when the Aerospike cluster is running with security enabled.
* `aerospike.restclient.clientpolicy.password` This is the password for the previously specified user. This variable is only needed when the Aerospike cluster is running with security enabled.
* `aerospike.restclient.clientpolicy.authMode` This is the authentication mode. Use it when user/password is defined. Supported modes are INTERNAL, EXTERNAL and EXTERNAL_INSECURE. Default is INTERNAL.

To utilize the multi-tenancy capability within the REST Gateway, send Aerospike login credentials using the [Basic access authentication](https://en.wikipedia.org/wiki/Basic_access_authentication).  
Set custom multi-user authentication configuration variables if needed:
* `aerospike.restclient.requireAuthentication` Set this boolean flag to true to require the Basic Authentication on each request.
* `aerospike.restclient.pool.size` Represents the max size of the authenticated clients LRU cache (default value: 16).
Please note that an oversized client cache will consume a lot of resources and affect the performance.

### TLS Configuration

Beginning with version `1.1.0` the Aerospike REST Gateway supports TLS communication between the client and the Aerospike Server. (This feature requires an Enterprise Edition Aerospike Server).
If utilizing TLS, the `aerospike.restclient.hostlist` variable should be set to include appropriate TLS Names for each of the Aerospike Nodes. For example: `localhost:cluster-tls-name:4333` The following environment variables allow configuration of this connection:

* `aerospike.restclient.ssl.enabled` boolean, set to `true` to enable a TLS connection with the Aerospike Server. If no other SSL environment variables are provided, the REST gateway will attempt to establish a secure connection utilizing the default Java SSL trust and keystore settings. Default: `false`
* `aerospike.restclient.ssl.keystorepath` The path to a Java KeyStore to be used to interact with the Aerospike Server. If omitted the default Java KeyStore location and password will be used.
* `aerospike.restclient.ssl.keystorepassword` The password to the keystore. If a keystore path is specified, this must be specified as well.
* `aerospike.restclient.ssl.keypassword` The password for the key to be used when communicating with Aerospike. If omitted, and `aerospike.restclient.ssl.keystorepassword` is provided,  the value of `aerospike.restclient.ssl.keystorepassword` will be used as the key password.
* `aerospike.restclient.ssl.truststorepath` The path to a Java TrustStore to be used to interact with the Aerospike Server. If omitted the default Java TrustStore location and password will be used.
* `aerospike.restclient.ssl.truststorepassword` The password for the truststore. May be omitted if the TrustStore is not password protected.
* `aerospike.restclient.ssl.forloginonly` Boolean indicating that SSL should only be used for the initial login connection to Aerospike. Default: `false`
* `aerospike.restclient.ssl.allowedciphers` An optional comma separated list of ciphers that are permitted to be used in communication with Aerospike. Available cipher names can be obtained by `SSLSocket.getSupportedCipherSuites()`.
* `aerospike.restclient.ssl.allowedprotocols` An optional comma separated list of protocols that are permitted to be used in communication with Aerospike. Available values can be acquired using `SSLSocket.getSupportedProtocols()`. By Default only `TLSv1.2` is allowed.

## Verifying installation

*Note:* The following steps assume REST Gateway's base path is `http://localhost:8080/`.
If this is not the case, the provided URLs should be modified accordingly.

To test that the REST Gateway is up and running and connected to the Aerospike database you can run:

```bash
curl http://localhost:8080/v1/cluster
```

This returns basic information about the cluster.

Interactive API documentation may be found at `http://localhost:8080/swagger-ui.html`. This allows you to test out various commands in your browser.

The Swagger specification, in JSON format, can be found at `http://localhost:8080/v3/api-docs`.