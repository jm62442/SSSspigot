/*    */ package com.ssspigot.clip;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.URL;
/*    */ import java.nio.file.Path;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Agent
/*    */ {
/*    */   static void addToClassPath(Path paperJar) {
/* 22 */     ClassLoader loader = ClassLoader.getSystemClassLoader();
/* 23 */     if (!(loader instanceof java.net.URLClassLoader)) {
/* 24 */       throw new RuntimeException("System ClassLoader is not URLClassLoader");
/*    */     }
/*    */     try {
/* 27 */       Method addURL = getAddMethod(loader);
/* 28 */       if (addURL == null) {
/* 29 */         System.err.println("Unable to find method to add Paper jar to System ClassLoader");
/* 30 */         System.exit(1);
/*    */       } 
/* 32 */       addURL.setAccessible(true);
/* 33 */       addURL.invoke(loader, new Object[] { paperJar.toUri().toURL() });
/* 34 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException|java.net.MalformedURLException e) {
/* 35 */       System.err.println("Unable to add Paper Jar to System ClassLoader");
/* 36 */       e.printStackTrace();
/* 37 */       System.exit(1);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static Method getAddMethod(Object o) {
/* 42 */     Class<?> clazz = o.getClass();
/* 43 */     Method m = null;
/* 44 */     while (m == null) {
/*    */       try {
/* 46 */         m = clazz.getDeclaredMethod("addURL", new Class[] { URL.class });
/* 47 */       } catch (NoSuchMethodException ignored) {
/* 48 */         clazz = clazz.getSuperclass();
/* 49 */         if (clazz == null) {
/* 50 */           return null;
/*    */         }
/*    */       } 
/*    */     } 
/* 54 */     return m;
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\com\ssspigot\clip\Agent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */