package com.muyie.oss.resource;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ExecutorService;

/**
 * @author larry
 * @since 2.7.13
 */
@Slf4j
public class OssStorageResource implements WritableResource {
  private static final String MESSAGE_KEY_NOT_EXIST = "The specified key does not exist.";
  private final OSS oss;
  private final String bucketName;
  private final String objectKey;
  private final URI location;
  private final boolean autoCreateFiles;
  private final ExecutorService ossTaskExecutor;
  private final ConfigurableListableBeanFactory beanFactory;

  public OssStorageResource(OSS oss, String location, ConfigurableListableBeanFactory beanFactory) {
    this(oss, location, beanFactory, false);
  }

  public OssStorageResource(OSS oss, String location, ConfigurableListableBeanFactory beanFactory, boolean autoCreateFiles) {
    Assert.notNull(oss, "Object Storage Service can not be null");
    Assert.isTrue(location.startsWith("oss://"), "Location must start with oss://");
    this.oss = oss;
    this.autoCreateFiles = autoCreateFiles;
    this.beanFactory = beanFactory;

    try {
      URI locationUri = new URI(StringUtils.trimAllWhitespace(location));
      this.bucketName = locationUri.getAuthority();
      if (locationUri.getPath() != null && locationUri.getPath().length() > 1) {
        this.objectKey = locationUri.getPath().substring(1);
      } else {
        this.objectKey = null;
      }

      this.location = locationUri;
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException("Invalid location: " + location, e);
    }

    this.ossTaskExecutor = this.beanFactory.getBean("ossTaskExecutor", ExecutorService.class);
  }

  public boolean isAutoCreateFiles() {
    return this.autoCreateFiles;
  }

  public boolean exists() {
    try {
      return this.isBucket() ? this.getBucket() != null : this.getOSSObject() != null;
    } catch (Exception e) {
      return false;
    }
  }

  public URL getURL() throws IOException {
    return this.location.toURL();
  }

  public URI getURI() throws IOException {
    return this.location;
  }

  public File getFile() throws IOException {
    throw new UnsupportedOperationException(this.getDescription() + " cannot be resolved to absolute file path");
  }

  public long contentLength() throws IOException {
    this.assertExisted();
    if (this.isBucket()) {
      throw new FileNotFoundException("OSSObject not existed.");
    } else {
      return this.getOSSObject().getObjectMetadata().getContentLength();
    }
  }

  public long lastModified() throws IOException {
    this.assertExisted();
    if (this.isBucket()) {
      throw new FileNotFoundException("OSSObject not existed.");
    } else {
      return this.getOSSObject().getObjectMetadata().getLastModified().getTime();
    }
  }

  public Resource createRelative(String relativePath) throws IOException {
    return new OssStorageResource(this.oss, this.location.resolve(relativePath).toString(), this.beanFactory);
  }

  public String getFilename() {
    return this.isBucket() ? this.bucketName : this.objectKey;
  }

  public String getDescription() {
    return this.location.toString();
  }

  public InputStream getInputStream() throws IOException {
    this.assertExisted();
    if (this.isBucket()) {
      throw new IllegalStateException("Cannot open an input stream to a bucket: '" + this.location + "'");
    } else {
      return this.getOSSObject().getObjectContent();
    }
  }

  public Bucket getBucket() {
    return this.oss.listBuckets().stream().filter((bucket) -> {
      return bucket.getName().equals(this.bucketName);
    }).findFirst().orElseGet(null);
  }

  public boolean bucketExists() {
    return this.getBucket() != null;
  }

  public OSSObject getOSSObject() {
    return this.oss.getObject(this.bucketName, this.objectKey);
  }

  public boolean isBucket() {
    return this.objectKey == null;
  }

  private void assertExisted() throws FileNotFoundException {
    if (!this.exists()) {
      throw new FileNotFoundException("Bucket or OSSObject not existed.");
    }
  }

  public Bucket createBucket() {
    return this.oss.createBucket(this.bucketName);
  }

  public boolean isWritable() {
    return !this.isBucket() && (this.autoCreateFiles || this.exists());
  }

  public OutputStream getOutputStream() throws IOException {
    if (this.isBucket()) {
      throw new IllegalStateException("Cannot open an output stream to a bucket: '" + this.getURI() + "'");
    } else {
      OSSObject ossObject;
      try {
        ossObject = this.getOSSObject();
      } catch (OSSException e) {
        if (e.getMessage() == null || !e.getMessage().startsWith(MESSAGE_KEY_NOT_EXIST)) {
          throw e;
        }
        ossObject = null;
      }

      if (ossObject == null && !this.autoCreateFiles) {
        throw new FileNotFoundException("The object was not found: " + this.getURI());
      } else {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        this.ossTaskExecutor.submit(() -> {
          try {
            this.oss.putObject(this.bucketName, this.objectKey, in);
          } catch (Exception e) {
            log.error("Failed to put object", e);
          }

        });
        return out;
      }
    }
  }
}
