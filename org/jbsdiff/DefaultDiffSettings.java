/*    */ package org.jbsdiff;
/*    */ 
/*    */ import org.jbsdiff.sort.SuffixSort;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultDiffSettings
/*    */   implements DiffSettings
/*    */ {
/*    */   private String compression;
/*    */   
/*    */   public DefaultDiffSettings() {
/* 42 */     this.compression = "bzip2";
/*    */   }
/*    */   
/*    */   public DefaultDiffSettings(String compression) {
/* 46 */     this.compression = compression;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCompression() {
/* 51 */     return this.compression;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] sort(byte[] input) {
/* 57 */     int[] I = new int[input.length + 1];
/* 58 */     int[] V = new int[input.length + 1];
/* 59 */     SuffixSort.qsufsort(I, V, input);
/*    */     
/* 61 */     return I;
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdiff\DefaultDiffSettings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */