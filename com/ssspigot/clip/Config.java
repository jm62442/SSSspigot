/*    */ package com.ssspigot.clip;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Paths;
/*    */ import java.nio.file.attribute.FileAttribute;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public final class Config {
/* 13 */   private final Properties props = new Properties();
/*    */   
/*    */   public final boolean enableAutoUpdate;
/*    */   public final String license;
/*    */   public final boolean updateOnly;
/*    */   public final boolean enableBackup;
/*    */   
/*    */   public Config() throws IOException {
/* 21 */     FileReader reader = null;
/*    */     try {
/*    */       try {
/* 24 */         reader = new FileReader("ssspigot-launcher.conf");
/* 25 */       } catch (FileNotFoundException ex) {
/* 26 */         Files.createFile(Paths.get("ssspigot-launcher.conf", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
/* 27 */         reader = new FileReader("ssspigot-launcher.conf");
/*    */       } 
/* 29 */       this.props.load(reader);
/* 30 */       this.updateOnly = Boolean.getBoolean("ssspigot.updateonly");
/* 31 */       this.enableBackup = getProperty("enable-backup", true);
/* 32 */       this.enableAutoUpdate = (getProperty("enable-auto-update", false) || Boolean.getBoolean("ssspigot.update") || this.updateOnly);
/* 33 */       this.license = getProperty("license", "");
/* 34 */       String overrideTempDir = getProperty("override-temp-dir", "");
/* 35 */       if (!overrideTempDir.equals("")) {
/* 36 */         System.setProperty("java.io.tmpdir", overrideTempDir);
/*    */       }
/*    */     } finally {
/* 39 */       if (reader != null) {
/* 40 */         reader.close();
/*    */       }
/*    */     } 
/*    */     
/* 44 */     try (OutputStream output = new FileOutputStream("ssspigot-launcher.conf")) {
/* 45 */       this.props.store(output, "SSSpigot2 Launcher Config. Note that we use the same update mechanism as paper, ie all versions are published without fully testing due to technical limitations. Please test locally and then manually update SSSpigot on the server or upload the binary (ssspigot2-1165.bin) to the server with automatic updating turned off if possible.");
/*    */     } 
/*    */   }
/*    */   
/*    */   private String getProperty(String key, String defaultValue) {
/* 50 */     String result = this.props.getProperty(key, defaultValue);
/* 51 */     this.props.setProperty(key, result);
/* 52 */     return result;
/*    */   }
/*    */   
/*    */   private boolean getProperty(String key, boolean defaultValue) {
/* 56 */     boolean result = this.props.getProperty(key, defaultValue ? "true" : "false").equals("true");
/* 57 */     this.props.setProperty(key, result ? "true" : "false");
/* 58 */     return result;
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\com\ssspigot\clip\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */