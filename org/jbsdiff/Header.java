/*     */ package org.jbsdiff;
/*     */ 
/*     */ import java.io.DataInputStream;
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
/*     */ class Header
/*     */ {
/*     */   public static final int HEADER_SIZE = 32;
/*     */   public static final String HEADER_MAGIC = "BSDIFF40";
/*     */   private String magic;
/*     */   private int controlLength;
/*     */   private int diffLength;
/*     */   private int outLength;
/*     */   
/*     */   public Header() {}
/*     */   
/*     */   public Header(InputStream in) throws IOException, InvalidHeaderException {
/*  69 */     InputStream headerIn = new DataInputStream(in);
/*  70 */     byte[] buf = new byte[8];
/*     */     
/*  72 */     headerIn.read(buf);
/*  73 */     this.magic = new String(buf);
/*  74 */     if (!this.magic.equals("BSDIFF40")) {
/*  75 */       throw new InvalidHeaderException("Header missing magic number");
/*     */     }
/*     */     
/*  78 */     this.controlLength = Offset.readOffset(headerIn);
/*  79 */     this.diffLength = Offset.readOffset(headerIn);
/*  80 */     this.outLength = Offset.readOffset(headerIn);
/*     */     
/*  82 */     verify();
/*     */   }
/*     */ 
/*     */   
/*     */   public Header(int controlLength, int diffLength, int outLength) throws InvalidHeaderException {
/*  87 */     this.controlLength = controlLength;
/*  88 */     this.diffLength = diffLength;
/*  89 */     this.outLength = outLength;
/*     */     
/*  91 */     verify();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(OutputStream out) throws IOException {
/*  98 */     out.write("BSDIFF40".getBytes());
/*  99 */     Offset.writeOffset(this.controlLength, out);
/* 100 */     Offset.writeOffset(this.diffLength, out);
/* 101 */     Offset.writeOffset(this.outLength, out);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void verify() throws InvalidHeaderException {
/* 108 */     if (this.controlLength < 0) {
/* 109 */       throw new InvalidHeaderException("control block length", this.controlLength);
/*     */     }
/*     */ 
/*     */     
/* 113 */     if (this.diffLength < 0) {
/* 114 */       throw new InvalidHeaderException("diff block length", this.diffLength);
/*     */     }
/*     */     
/* 117 */     if (this.outLength < 0) {
/* 118 */       throw new InvalidHeaderException("output file length", this.outLength);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     String s = "";
/*     */     
/* 126 */     s = s + this.magic + "\n";
/* 127 */     s = s + "control bytes = " + this.controlLength + "\n";
/* 128 */     s = s + "diff bytes = " + this.diffLength + "\n";
/* 129 */     s = s + "output size = " + this.outLength;
/*     */     
/* 131 */     return s;
/*     */   }
/*     */   
/*     */   public int getControlLength() {
/* 135 */     return this.controlLength;
/*     */   }
/*     */   
/*     */   public void setControlLength(int length) throws InvalidHeaderException {
/* 139 */     this.controlLength = length;
/* 140 */     verify();
/*     */   }
/*     */   
/*     */   public int getDiffLength() {
/* 144 */     return this.diffLength;
/*     */   }
/*     */   
/*     */   public void setDiffLength(int length) throws InvalidHeaderException {
/* 148 */     this.diffLength = length;
/* 149 */     verify();
/*     */   }
/*     */   
/*     */   public int getOutputLength() {
/* 153 */     return this.outLength;
/*     */   }
/*     */   
/*     */   public void setOutputLength(int length) throws InvalidHeaderException {
/* 157 */     this.outLength = length;
/* 158 */     verify();
/*     */   }
/*     */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdiff\Header.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */