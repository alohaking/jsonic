package org.kevin;

public class SonicSettings {
    public static final long DEFAULT_CONNECT_TIMEOUT = 3000;
    public static final long DEFAULT_READ_TIMEOUT = 60000;
    public static final long DEFAULT_IDLE_TIMEOUT = 60000;

    public static final int DEFAULT_MAX_THREADS = 0;
    public static final int DEFAULT_MAX_CONN_PER_HOST = 100;


    private long connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    private long readTimeout = DEFAULT_READ_TIMEOUT;
    private long idleTimeout = DEFAULT_IDLE_TIMEOUT;
    private int maxThreads = DEFAULT_MAX_THREADS;
    private int maxConnPerHost = DEFAULT_MAX_CONN_PER_HOST;

    private String host = "localhost";
    private int port = 1491;
    private String password = "SecretPassword";

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public long getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public int getMaxConnPerHost() {
        return maxConnPerHost;
    }

    public void setMaxConnPerHost(int maxConnPerHost) {
        this.maxConnPerHost = maxConnPerHost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
