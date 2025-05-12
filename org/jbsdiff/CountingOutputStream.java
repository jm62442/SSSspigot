/*    */ package org.jbsdiff;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
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
/*    */ public class CountingOutputStream
/*    */   extends OutputStream
/*    */ {
/*    */   private OutputStream out;
/*    */   private int counter;
/*    */   
/*    */   public CountingOutputStream(OutputStream out) {
/* 43 */     this.out = out;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int b) throws IOException {
/* 48 */     this.counter++;
/* 49 */     this.out.write(b);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCount() {
/* 57 */     return this.counter;
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdiff\CountingOutputStream.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */