/*    */ package com.ssspigot.clip;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ 
/*    */ public final class Utils {
/*    */   public static byte[] sha1sum(byte[] input) {
/*    */     try {
/*  9 */       return MessageDigest.getInstance("SHA-1").digest(input);
/* 10 */     } catch (NoSuchAlgorithmException e) {
/* 11 */       System.err.println("Could not create hashing instance");
/* 12 */       e.printStackTrace();
/* 13 */       System.exit(1);
/* 14 */       throw new InternalError();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 20 */   private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
/*    */   
/*    */   public static String bytesToHex(byte[] bytes) {
/* 23 */     char[] hexChars = new char[bytes.length * 2];
/* 24 */     for (int j = 0; j < bytes.length; j++) {
/* 25 */       int v = bytes[j] & 0xFF;
/* 26 */       hexChars[j * 2] = HEX_ARRAY[v >>> 4];
/* 27 */       hexChars[j * 2 + 1] = HEX_ARRAY[v & 0xF];
/*    */     } 
/* 29 */     return new String(hexChars);
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\com\ssspigot\clip\Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */