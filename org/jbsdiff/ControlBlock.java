/*     */ package org.jbsdiff;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ControlBlock
/*     */ {
/*     */   private int diffLength;
/*     */   private int extraLength;
/*     */   private int seekLength;
/*     */   
/*     */   public ControlBlock() {}
/*     */   
/*     */   public ControlBlock(InputStream in) throws IOException {
/*  67 */     this.diffLength = Offset.readOffset(in);
/*  68 */     this.extraLength = Offset.readOffset(in);
/*  69 */     this.seekLength = Offset.readOffset(in);
/*     */   }
/*     */   
/*     */   public ControlBlock(int diffLength, int extraLength, int seekLength) {
/*  73 */     this.diffLength = diffLength;
/*  74 */     this.extraLength = extraLength;
/*  75 */     this.seekLength = seekLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(OutputStream out) throws IOException {
/*  82 */     Offset.writeOffset(this.diffLength, out);
/*  83 */     Offset.writeOffset(this.extraLength, out);
/*  84 */     Offset.writeOffset(this.seekLength, out);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  89 */     return this.diffLength + ", " + this.extraLength + ", " + this.seekLength;
/*     */   }
/*     */   
/*     */   public int getDiffLength() {
/*  93 */     return this.diffLength;
/*     */   }
/*     */   
/*     */   public void setDiffLength(int length) {
/*  97 */     this.diffLength = length;
/*     */   }
/*     */   
/*     */   public int getExtraLength() {
/* 101 */     return this.extraLength;
/*     */   }
/*     */   
/*     */   public void setExtraLength(int length) {
/* 105 */     this.extraLength = length;
/*     */   }
/*     */   
/*     */   public int getSeekLength() {
/* 109 */     return this.seekLength;
/*     */   }
/*     */   
/*     */   public void setSeekLength(int length) {
/* 113 */     this.seekLength = length;
/*     */   }
/*     */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdiff\ControlBlock.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */