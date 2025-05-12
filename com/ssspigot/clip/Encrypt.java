/*    */ package com.ssspigot.clip;
/*    */ 
/*    */ import java.security.GeneralSecurityException;
/*    */ import java.security.Key;
/*    */ import java.security.SecureRandom;
/*    */ import java.util.Arrays;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ 
/*    */ 
/*    */ public final class Encrypt
/*    */ {
/*    */   private static byte[] makeKey(byte[] key) {
/* 14 */     if (key.length > 32) {
/* 15 */       throw new IllegalArgumentException("key too long");
/*    */     }
/* 17 */     return Arrays.copyOf(key, 32);
/*    */   }
/*    */   
/*    */   public static byte[] genRandomKey() {
/* 21 */     byte[] key = new byte[32];
/* 22 */     SecureRandom random = new SecureRandom();
/* 23 */     random.nextBytes(key);
/* 24 */     return key;
/*    */   }
/*    */   
/*    */   public static byte[] encrypt(byte[] rawkey, byte[] data) throws GeneralSecurityException {
/* 28 */     Key key = new SecretKeySpec(makeKey(rawkey), "AES");
/* 29 */     Cipher cipher = Cipher.getInstance("AES");
/* 30 */     cipher.init(1, key);
/* 31 */     return cipher.doFinal(data);
/*    */   }
/*    */   
/*    */   public static byte[] decrypt(byte[] rawkey, byte[] data) throws GeneralSecurityException {
/* 35 */     Key key = new SecretKeySpec(makeKey(rawkey), "AES");
/* 36 */     Cipher cipher = Cipher.getInstance("AES");
/* 37 */     cipher.init(2, key);
/* 38 */     return cipher.doFinal(data);
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\com\ssspigot\clip\Encrypt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */