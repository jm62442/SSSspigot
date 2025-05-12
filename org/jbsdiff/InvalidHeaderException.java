/*    */ package org.jbsdiff;
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
/*    */ public class InvalidHeaderException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = -3712364093810940826L;
/*    */   
/*    */   public InvalidHeaderException() {}
/*    */   
/*    */   public InvalidHeaderException(String detail) {
/* 43 */     super(detail);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidHeaderException(String fieldName, int value) {
/* 51 */     super("Invalid header field; " + fieldName + " = " + value);
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdiff\InvalidHeaderException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */