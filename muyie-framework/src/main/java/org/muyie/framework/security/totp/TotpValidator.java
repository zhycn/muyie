package org.muyie.framework.security.totp;

public class TotpValidator {

  private final org.jboss.aerogear.security.otp.Totp delegate;

  public TotpValidator(String secret) {
    this.delegate = new org.jboss.aerogear.security.otp.Totp(secret);
  }

  public TotpValidator(Totp totp) {
    this(totp.getSecret());
  }

  public boolean verify(String otp) {
    return this.delegate.verify(otp);
  }

}
