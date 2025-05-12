/*     */ package org.jbsdiff;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.compress.compressors.CompressorException;
/*     */ import org.apache.commons.compress.compressors.CompressorInputStream;
/*     */ import org.apache.commons.compress.compressors.CompressorStreamFactory;
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
/*     */ public class Patch
/*     */ {
/*     */   public static void patch(byte[] old, byte[] patch, OutputStream out) throws CompressorException, InvalidHeaderException, IOException {
/*     */     CompressorInputStream compressorInputStream1, compressorInputStream2, compressorInputStream3;
/*  51 */     InputStream headerIn = new ByteArrayInputStream(patch);
/*  52 */     Header header = new Header(headerIn);
/*  53 */     headerIn.close();
/*     */ 
/*     */ 
/*     */     
/*  57 */     InputStream controlIn = new ByteArrayInputStream(patch);
/*  58 */     InputStream dataIn = new ByteArrayInputStream(patch);
/*  59 */     InputStream extraIn = new ByteArrayInputStream(patch);
/*     */ 
/*     */     
/*     */     try {
/*  63 */       controlIn.skip(32L);
/*  64 */       dataIn.skip((32 + header.getControlLength()));
/*  65 */       extraIn.skip((32 + header.getControlLength() + header.getDiffLength()));
/*     */ 
/*     */ 
/*     */       
/*  69 */       CompressorStreamFactory compressor = new CompressorStreamFactory();
/*  70 */       compressorInputStream1 = compressor.createCompressorInputStream(controlIn);
/*  71 */       compressorInputStream2 = compressor.createCompressorInputStream(dataIn);
/*  72 */       compressorInputStream3 = compressor.createCompressorInputStream(extraIn);
/*     */ 
/*     */       
/*  75 */       int newPointer = 0, oldPointer = 0;
/*  76 */       byte[] output = new byte[header.getOutputLength()];
/*  77 */       while (newPointer < output.length) {
/*     */         
/*  79 */         ControlBlock control = new ControlBlock((InputStream)compressorInputStream1);
/*     */ 
/*     */         
/*  82 */         read((InputStream)compressorInputStream2, output, newPointer, control.getDiffLength());
/*     */ 
/*     */         
/*  85 */         for (int i = 0; i < control.getDiffLength(); i++) {
/*  86 */           if (oldPointer + i >= 0 && oldPointer + i < old.length) {
/*  87 */             output[newPointer + i] = (byte)(output[newPointer + i] + old[oldPointer + i]);
/*     */           }
/*     */         } 
/*     */         
/*  91 */         newPointer += control.getDiffLength();
/*  92 */         oldPointer += control.getDiffLength();
/*     */ 
/*     */         
/*  95 */         read((InputStream)compressorInputStream3, output, newPointer, control.getExtraLength());
/*     */         
/*  97 */         newPointer += control.getExtraLength();
/*  98 */         oldPointer += control.getSeekLength();
/*     */       } 
/*     */       
/* 101 */       out.write(output);
/*     */     }
/* 103 */     catch (Exception e) {
/* 104 */       throw e;
/*     */     } finally {
/* 106 */       compressorInputStream1.close();
/* 107 */       compressorInputStream2.close();
/* 108 */       compressorInputStream3.close();
/*     */     } 
/*     */   }
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
/*     */   private static void read(InputStream in, byte[] dest, int off, int len) throws IOException {
/* 125 */     if (len == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 130 */     int read = in.read(dest, off, len);
/* 131 */     if (read < len)
/* 132 */       throw new IOException("Corrupt patch; bytes expected = " + len + " bytes read = " + read); 
/*     */   }
/*     */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdiff\Patch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */