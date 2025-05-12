/*    */ package com.ssspigot.clip;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import java.util.Properties;
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
/*    */ final class PatchData
/*    */ {
/*    */   final URL originalUrl;
/*    */   final byte[] originalHash;
/*    */   final byte[] patchedHash;
/*    */   final String version;
/*    */   
/*    */   private PatchData(Properties prop) {
/*    */     try {
/* 27 */       this.originalUrl = new URL(prop.getProperty("sourceUrl"));
/* 28 */     } catch (MalformedURLException e) {
/* 29 */       throw new IllegalArgumentException("Invalid URL", e);
/*    */     } 
/* 31 */     this.originalHash = fromHex(prop.getProperty("originalHash"));
/* 32 */     this.patchedHash = fromHex(prop.getProperty("patchedHash"));
/* 33 */     this.version = prop.getProperty("version");
/*    */   }
/*    */   
/*    */   static PatchData parse(Reader defaults, Reader optional) throws IOException {
/*    */     try {
/* 38 */       Properties defaultProps = new Properties();
/* 39 */       defaultProps.load(defaults);
/* 40 */       Properties props = new Properties(defaultProps);
/* 41 */       if (optional != null) {
/* 42 */         props.load(optional);
/*    */       }
/* 44 */       return new PatchData(props);
/* 45 */     } catch (IOException e) {
/* 46 */       throw e;
/* 47 */     } catch (Exception e) {
/* 48 */       throw new IllegalArgumentException("Invalid properties file", e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static byte[] fromHex(String s) {
/* 53 */     if (s.length() % 2 != 0) {
/* 54 */       throw new IllegalArgumentException("Hex " + s + " must be divisible by two");
/*    */     }
/* 56 */     byte[] bytes = new byte[s.length() / 2];
/* 57 */     for (int i = 0; i < bytes.length; i++) {
/* 58 */       char left = s.charAt(i * 2);
/* 59 */       char right = s.charAt(i * 2 + 1);
/* 60 */       byte b = (byte)(getValue(left) << 4 | getValue(right) & 0xF);
/* 61 */       bytes[i] = b;
/*    */     } 
/* 63 */     return bytes;
/*    */   }
/*    */   
/*    */   private static int getValue(char c) {
/* 67 */     int i = Character.digit(c, 16);
/* 68 */     if (i < 0) {
/* 69 */       throw new IllegalArgumentException("Invalid hex char: " + c);
/*    */     }
/* 71 */     return i;
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\com\ssspigot\clip\PatchData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */