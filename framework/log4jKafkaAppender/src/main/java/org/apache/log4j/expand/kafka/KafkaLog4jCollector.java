package org.apache.log4j.expand.kafka;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.config.ConfigException;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by shanshouchen@weibangong.com.
 *
 * @author shanshouchen
 * @date 16/9/1
 */
public class KafkaLog4jCollector extends AppenderSkeleton {
    private static final String BOOTSTRAP_SERVERS_CONFIG = "bootstrap.servers";
    private static final String COMPRESSION_TYPE_CONFIG = "compression.type";
    private static final String ACKS_CONFIG = "acks";
    private static final String RETRIES_CONFIG = "retries";
    private static final String KEY_SERIALIZER_CLASS_CONFIG = "key.serializer";
    private static final String VALUE_SERIALIZER_CLASS_CONFIG = "value.serializer";
    private static final String SECURITY_PROTOCOL = "security.protocol";
    private static final String SSL_TRUSTSTORE_LOCATION = "ssl.truststore.location";
    private static final String SSL_TRUSTSTORE_PASSWORD = "ssl.truststore.password";
    private static final String SSL_KEYSTORE_TYPE = "ssl.keystore.type";
    private static final String SSL_KEYSTORE_LOCATION = "ssl.keystore.location";
    private static final String SSL_KEYSTORE_PASSWORD = "ssl.keystore.password";
    private static final String SASL_KERBEROS_SERVICE_NAME = "sasl.kerberos.service.name";
    private String serviceV = null;
    private String serviceG = null;
    private String serviceA = null;
    private String brokerList = null;
    private String topic = null;
    private String compressionType = null;
    private String securityProtocol = null;
    private String sslTruststoreLocation = null;
    private String sslTruststorePassword = null;
    private String sslKeystoreType = null;
    private String sslKeystoreLocation = null;
    private String sslKeystorePassword = null;
    private String saslKerberosServiceName = null;
    private String clientJaasConfPath = null;
    private String kerb5ConfPath = null;
    private int retries = 0;
    private int requiredNumAcks = 2147483647;
    private boolean syncSend = false;
    private boolean demand = false;
    private Producer<byte[], byte[]> producer = null;

    public Producer<byte[], byte[]> getProducer() {
        return this.producer;
    }

    public String getBrokerList() {
        return this.brokerList;
    }

    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }

    public int getRequiredNumAcks() {
        return this.requiredNumAcks;
    }

    public void setRequiredNumAcks(int requiredNumAcks) {
        this.requiredNumAcks = requiredNumAcks;
    }

    public int getRetries() {
        return this.retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public String getCompressionType() {
        return this.compressionType;
    }

    public void setCompressionType(String compressionType) {
        this.compressionType = compressionType;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean getSyncSend() {
        return this.syncSend;
    }

    public void setSyncSend(boolean syncSend) {
        this.syncSend = syncSend;
    }

    public String getSslTruststorePassword() {
        return this.sslTruststorePassword;
    }

    public String getSslTruststoreLocation() {
        return this.sslTruststoreLocation;
    }

    public String getSecurityProtocol() {
        return this.securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

    public void setSslTruststoreLocation(String sslTruststoreLocation) {
        this.sslTruststoreLocation = sslTruststoreLocation;
    }

    public void setSslTruststorePassword(String sslTruststorePassword) {
        this.sslTruststorePassword = sslTruststorePassword;
    }

    public void setSslKeystorePassword(String sslKeystorePassword) {
        this.sslKeystorePassword = sslKeystorePassword;
    }

    public void setSslKeystoreType(String sslKeystoreType) {
        this.sslKeystoreType = sslKeystoreType;
    }

    public void setSslKeystoreLocation(String sslKeystoreLocation) {
        this.sslKeystoreLocation = sslKeystoreLocation;
    }

    public void setSaslKerberosServiceName(String saslKerberosServiceName) {
        this.saslKerberosServiceName = saslKerberosServiceName;
    }

    public void setClientJaasConfPath(String clientJaasConfPath) {
        this.clientJaasConfPath = clientJaasConfPath;
    }

    public void setKerb5ConfPath(String kerb5ConfPath) {
        this.kerb5ConfPath = kerb5ConfPath;
    }

    public String getSslKeystoreLocation() {
        return this.sslKeystoreLocation;
    }

    public String getSslKeystoreType() {
        return this.sslKeystoreType;
    }

    public String getSslKeystorePassword() {
        return this.sslKeystorePassword;
    }

    public String getSaslKerberosServiceName() {
        return this.saslKerberosServiceName;
    }

    public String getClientJaasConfPath() {
        return this.clientJaasConfPath;
    }

    public String getKerb5ConfPath() {
        return this.kerb5ConfPath;
    }

    public String getServiceV() {
        return serviceV;
    }

    public void setServiceV(String serviceV) {
        this.serviceV = serviceV;
    }

    public String getServiceG() {
        return serviceG;
    }

    public void setServiceG(String serviceG) {
        this.serviceG = serviceG;
    }

    public String getServiceA() {
        return serviceA;
    }

    public void setServiceA(String serviceA) {
        this.serviceA = serviceA;
    }

    public KafkaLog4jCollector() {

    }

    public void activateOptions() {
        Properties props = new Properties();
        if (this.brokerList != null) {
            props.put("bootstrap.servers", this.brokerList);
            demand = true;
        } else {
            return;
        }

        if (props.isEmpty()) {
            throw new ConfigException("The bootstrap servers property should be specified");
        } else if (this.topic == null) {
            throw new ConfigException("Topic must be specified by the Kafka log4j appender");
        } else {
            if (this.compressionType != null) {
                props.put("compression.type", this.compressionType);
            }

            if (this.requiredNumAcks != 2147483647) {
                props.put("acks", Integer.toString(this.requiredNumAcks));
            }

            if (this.retries > 0) {
                props.put("retries", Integer.valueOf(this.retries));
            }

            if (this.securityProtocol != null) {
                props.put("security.protocol", this.securityProtocol);
            }

            if (this.securityProtocol != null && this.securityProtocol.contains("SSL") && this.sslTruststoreLocation != null && this.sslTruststorePassword != null) {
                props.put("ssl.truststore.location", this.sslTruststoreLocation);
                props.put("ssl.truststore.password", this.sslTruststorePassword);
                if (this.sslKeystoreType != null && this.sslKeystoreLocation != null && this.sslKeystorePassword != null) {
                    props.put("ssl.keystore.type", this.sslKeystoreType);
                    props.put("ssl.keystore.location", this.sslKeystoreLocation);
                    props.put("ssl.keystore.password", this.sslKeystorePassword);
                }
            }

            if (this.securityProtocol != null && this.securityProtocol.contains("SASL") && this.saslKerberosServiceName != null && this.clientJaasConfPath != null) {
                props.put("sasl.kerberos.service.name", this.saslKerberosServiceName);
                System.setProperty("java.security.auth.login.config", this.clientJaasConfPath);
                if (this.kerb5ConfPath != null) {
                    System.setProperty("java.security.krb5.conf", this.kerb5ConfPath);
                }
            }

            props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
            this.producer = this.getKafkaProducer(props);
            LogLog.debug("Kafka producer connected to " + this.brokerList);
            LogLog.debug("Logging for topic: " + this.topic);
        }
    }

    protected Producer<byte[], byte[]> getKafkaProducer(Properties props) {
        return new KafkaProducer(props);
    }

    @Override
    protected void append(LoggingEvent event) {
        if (!demand) return;
        String message = this.subAppend(event);
        LogLog.debug("[" + new Date(event.getTimeStamp()) + "]" + message);
        LogEntry entry = new LogEntry(key(), message);
        entry.setLevel(event.getLevel().toString());
        entry.setHost(System.getProperty("host", "unKnow"));
        String body = ToStringBuilder.reflectionToString(entry, ToStringStyle.JSON_STYLE);
        Future response = this.producer.send(new ProducerRecord(this.topic, key().getBytes(), body.getBytes()));
        if (this.syncSend) {
            try {
                response.get();
            } catch (InterruptedException var5) {
                throw new RuntimeException(var5);
            } catch (ExecutionException var6) {
                throw new RuntimeException(var6);
            }
        }
    }

    private String subAppend(LoggingEvent event) {
        return this.layout == null ? event.getRenderedMessage() : this.layout.format(event);
    }

    private String key() {
        if (serviceG == null || "".equals(serviceG)) serviceG = "unKnowGroup";
        if (serviceV == null || "".equals(serviceV)) serviceV = "unKnowVersion";
        if (serviceA == null || "".equals(serviceA)) serviceA = "unKnowArtifact";
        String group = serviceG.replace(" ", ".").replace("-", ".").replace("_", ".");
        String version = serviceV.replace(" ", ".").replace("-", ".").replace("_", ".");
        String artifact = serviceA.replace(" ", ".").replace("-", ".").replace("_", ".");

        return group + "_" + artifact + "_" + version;

    }

    @Override
    public void close() {
        if (!demand) return;
        if (!this.closed) {
            this.closed = true;
            this.producer.close();
        }

    }

    @Override
    public boolean requiresLayout() {
        return true;
    }
}

