/*    */ package org.apache.commons.compress.archivers.zip;
/*    */ 
/*    */ import java.util.zip.ZipException;
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
/*    */ public class Zip64RequiredException
/*    */   extends ZipException
/*    */ {
/*    */   private static final long serialVersionUID = 20110809L;
/*    */   static final String ARCHIVE_TOO_BIG_MESSAGE = "archive's size exceeds the limit of 4GByte.";
/*    */   static final String TOO_MANY_ENTRIES_MESSAGE = "archive contains more than 65535 entries.";
/*    */   
/*    */   static String getEntryTooBigMessage(ZipArchiveEntry ze) {
/* 37 */     return ze.getName() + "'s size exceeds the limit of 4GByte.";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Zip64RequiredException(String reason) {
/* 47 */     super(reason);
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\apache\commons\compress\archivers\zip\Zip64RequiredException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */