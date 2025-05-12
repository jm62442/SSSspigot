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
/*     */ class Offset
/*     */ {
/*     */   public static final int OFFSET_SIZE = 8;
/*     */   
/*     */   public static int readOffset(InputStream in) throws IOException {
/*  52 */     byte[] buf = new byte[8];
/*  53 */     int bytesRead = in.read(buf);
/*  54 */     if (bytesRead < 8) {
/*  55 */       throw new IOException("Could not read offset.");
/*     */     }
/*     */     
/*  58 */     int y = 0;
/*  59 */     y = buf[7] & Byte.MAX_VALUE;
/*  60 */     y *= 256;
/*  61 */     y += buf[6] & 0xFF;
/*  62 */     y *= 256;
/*  63 */     y += buf[5] & 0xFF;
/*  64 */     y *= 256;
/*  65 */     y += buf[4] & 0xFF;
/*  66 */     y *= 256;
/*  67 */     y += buf[3] & 0xFF;
/*  68 */     y *= 256;
/*  69 */     y += buf[2] & 0xFF;
/*  70 */     y *= 256;
/*  71 */     y += buf[1] & 0xFF;
/*  72 */     y *= 256;
/*  73 */     y += buf[0] & 0xFF;
/*     */ 
/*     */     
/*  76 */     if (y < 0) {
/*  77 */       throw new IOException("Integer overflow: 64-bit offsets not supported.");
/*     */     }
/*     */ 
/*     */     
/*  81 */     if ((buf[7] & 0x80) != 0) {
/*  82 */       y = -y;
/*     */     }
/*     */     
/*  85 */     return y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeOffset(int value, OutputStream out) throws IOException {
/*  95 */     byte[] buf = new byte[8];
/*  96 */     int y = 0;
/*     */     
/*  98 */     if (value < 0) {
/*  99 */       y = -value;
/*     */       
/* 101 */       buf[7] = (byte)(buf[7] | 0x80);
/*     */     } else {
/* 103 */       y = value;
/*     */     } 
/*     */     
/* 106 */     buf[0] = (byte)(buf[0] | y % 256);
/* 107 */     y -= buf[0] & 0xFF;
/* 108 */     y /= 256;
/* 109 */     buf[1] = (byte)(buf[1] | y % 256);
/* 110 */     y -= buf[1] & 0xFF;
/* 111 */     y /= 256;
/* 112 */     buf[2] = (byte)(buf[2] | y % 256);
/* 113 */     y -= buf[2] & 0xFF;
/* 114 */     y /= 256;
/* 115 */     buf[3] = (byte)(buf[3] | y % 256);
/* 116 */     y -= buf[3] & 0xFF;
/* 117 */     y /= 256;
/* 118 */     buf[4] = (byte)(buf[4] | y % 256);
/* 119 */     y -= buf[4] & 0xFF;
/* 120 */     y /= 256;
/* 121 */     buf[5] = (byte)(buf[5] | y % 256);
/* 122 */     y -= buf[5] & 0xFF;
/* 123 */     y /= 256;
/* 124 */     buf[6] = (byte)(buf[6] | y % 256);
/* 125 */     y -= buf[6] & 0xFF;
/* 126 */     y /= 256;
/* 127 */     buf[7] = (byte)(buf[7] | y % 256);
/*     */     
/* 129 */     out.write(buf);
/*     */   }
/*     */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdiff\Offset.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */