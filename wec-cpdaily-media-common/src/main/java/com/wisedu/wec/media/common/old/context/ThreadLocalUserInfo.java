package com.wisedu.wec.media.common.old.context;

public class ThreadLocalUserInfo {

  private static final ThreadLocal<UserInfoContext> contextHolder = new ThreadLocal<UserInfoContext>();

  public static void clearContext() {
    contextHolder.remove();
  }

  public static UserInfoContext getContext() {
    UserInfoContext ctx = contextHolder.get();

    if (ctx == null) {
      ctx = createEmptyContext();
      contextHolder.set(ctx);
    }

    return ctx;
  }

  public static void setContext(UserInfoContext context) {
    // Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
    contextHolder.set(context);
  }

  public static UserInfoContext createEmptyContext() {
    return new UserInfoContext();
  }
}
