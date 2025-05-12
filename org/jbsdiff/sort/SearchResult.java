/*    */ package org.jbsdiff.sort;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchResult
/*    */ {
/*    */   private int length;
/*    */   private int position;
/*    */   
/*    */   public SearchResult(int length, int position) {
/* 43 */     this.length = length;
/* 44 */     this.position = position;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return new String("length = " + this.length + ", position = " + this.position);
/*    */   }
/*    */   
/*    */   public int getLength() {
/* 53 */     return this.length;
/*    */   }
/*    */   
/*    */   public int getPosition() {
/* 57 */     return this.position;
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdiff\sort\SearchResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */