package org.muyie.framework.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class PersistentTokenCacheTest {

  @Test
  public void testConstructorThrows() {
    final Throwable caught = catchThrowable(() -> new PersistentTokenCache<String>(-1l));
    assertThat(caught).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void testAbsent() {
    final PersistentTokenCache<String> cache = new PersistentTokenCache<>(100l);
    assertThat(cache.get("key")).isNull();
  }

  @Test
  public void testAccess() {
    final PersistentTokenCache<String> cache = new PersistentTokenCache<>(100l);
    cache.put("key", "val");
    assertThat(cache.size()).isEqualTo(1);
    assertThat(cache.get("key")).isEqualTo("val");
  }

  @Test
  public void testReplace() {
    final PersistentTokenCache<String> cache = new PersistentTokenCache<>(100l);
    cache.put("key", "val");
    cache.put("key", "foo");
    assertThat(cache.get("key")).isEqualTo("foo");
  }

  @Test
  public void testExpires() {
    final PersistentTokenCache<String> cache = new PersistentTokenCache<>(1l);
    cache.put("key", "val");
    try {
      Thread.sleep(100L);
    } catch (final InterruptedException x) {
      // This should not happen
      throw new Error(x);
    }
    assertThat(cache.get("key")).isNull();
  }

  @Test
  public void testPurge() {
    final PersistentTokenCache<String> cache = new PersistentTokenCache<>(1l);
    cache.put("key", "val");
    try {
      Thread.sleep(100L);
    } catch (final InterruptedException x) {
      // This should not happen
      throw new Error(x);
    }
    assertThat(cache.size()).isEqualTo(1);
    cache.purge();
    assertThat(cache.size()).isEqualTo(0);
  }

}
