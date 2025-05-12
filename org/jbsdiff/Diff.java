/*     */ package org.jbsdiff;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.compress.compressors.CompressorException;
/*     */ import org.apache.commons.compress.compressors.CompressorOutputStream;
/*     */ import org.apache.commons.compress.compressors.CompressorStreamFactory;
/*     */ import org.jbsdiff.sort.SearchResult;
/*     */ import org.jbsdiff.sort.SuffixSort;
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
/*     */ public class Diff
/*     */ {
/*     */   public static void diff(byte[] oldBytes, byte[] newBytes, OutputStream out) throws CompressorException, InvalidHeaderException, IOException {
/*  54 */     diff(oldBytes, newBytes, out, new DefaultDiffSettings());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void diff(byte[] oldBytes, byte[] newBytes, OutputStream out, DiffSettings settings) throws CompressorException, InvalidHeaderException, IOException {
/*  64 */     CompressorStreamFactory compressor = new CompressorStreamFactory();
/*  65 */     String compression = settings.getCompression();
/*     */     
/*  67 */     int[] I = settings.sort(oldBytes);
/*     */     
/*  69 */     ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
/*  70 */     CompressorOutputStream compressorOutputStream = compressor.createCompressorOutputStream(compression, byteOut);
/*     */ 
/*     */     
/*  73 */     SearchResult result = null;
/*  74 */     int scan = 0, len = 0, position = 0;
/*  75 */     int lastScan = 0, lastPos = 0, lastOffset = 0;
/*  76 */     int oldScore = 0, scsc = 0;
/*     */ 
/*     */ 
/*     */     
/*  80 */     byte[] db = new byte[newBytes.length + 1];
/*  81 */     byte[] eb = new byte[newBytes.length + 1];
/*  82 */     int dblen = 0, eblen = 0;
/*     */     
/*  84 */     while (scan < newBytes.length) {
/*  85 */       oldScore = 0;
/*     */       
/*  87 */       for (scsc = scan += len; scan < newBytes.length; scan++) {
/*  88 */         result = SuffixSort.search(I, oldBytes, 0, newBytes, scan, 0, oldBytes.length);
/*     */ 
/*     */ 
/*     */         
/*  92 */         len = result.getLength();
/*  93 */         position = result.getPosition();
/*     */         
/*  95 */         for (; scsc < scan + len; scsc++) {
/*  96 */           if (scsc + lastOffset < oldBytes.length && oldBytes[scsc + lastOffset] == newBytes[scsc])
/*     */           {
/*  98 */             oldScore++;
/*     */           }
/*     */         } 
/* 101 */         if ((len == oldScore && len != 0) || len > oldScore + 8) {
/*     */           break;
/*     */         }
/*     */         
/* 105 */         if (scan + lastOffset < oldBytes.length && oldBytes[scan + lastOffset] == newBytes[scan])
/*     */         {
/* 107 */           oldScore--;
/*     */         }
/*     */       } 
/* 110 */       if (len != oldScore || scan == newBytes.length) {
/* 111 */         int s = 0;
/* 112 */         int Sf = 0;
/* 113 */         int lenf = 0; int i;
/* 114 */         for (i = 0; lastScan + i < scan && lastPos + i < oldBytes.length; ) {
/*     */           
/* 116 */           if (oldBytes[lastPos + i] == newBytes[lastScan + i]) {
/* 117 */             s++;
/*     */           }
/*     */           
/* 120 */           i++;
/* 121 */           if (s * 2 - i > Sf * 2 - lenf) {
/* 122 */             Sf = s;
/* 123 */             lenf = i;
/*     */           } 
/*     */         } 
/*     */         
/* 127 */         int lenb = 0;
/* 128 */         if (scan < newBytes.length) {
/* 129 */           s = 0;
/* 130 */           int Sb = 0;
/* 131 */           for (i = 1; scan >= lastScan + i && position >= i; 
/* 132 */             i++) {
/* 133 */             if (oldBytes[position - i] == newBytes[scan - i])
/*     */             {
/* 135 */               s++;
/*     */             }
/* 137 */             if (s * 2 - i > Sb * 2 - lenb) {
/* 138 */               Sb = s;
/* 139 */               lenb = i;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 144 */         if (lastScan + lenf > scan - lenb) {
/* 145 */           int overlap = lastScan + lenf - scan - lenb;
/* 146 */           s = 0;
/* 147 */           int Ss = 0;
/* 148 */           int lens = 0;
/* 149 */           for (i = 0; i < overlap; i++) {
/* 150 */             if (newBytes[lastScan + lenf - overlap + i] == oldBytes[lastPos + lenf - overlap + i])
/*     */             {
/* 152 */               s++;
/*     */             }
/* 154 */             if (newBytes[scan - lenb + i] == oldBytes[position - lenb + i])
/*     */             {
/* 156 */               s--;
/*     */             }
/* 158 */             if (s > Ss) {
/* 159 */               Ss = s;
/* 160 */               lens = i + 1;
/*     */             } 
/*     */           } 
/* 163 */           lenf += lens - overlap;
/* 164 */           lenb -= lens;
/*     */         } 
/*     */         
/* 167 */         for (i = 0; i < lenf; i++) {
/* 168 */           db[dblen + i] = (byte)(db[dblen + i] | newBytes[lastScan + i] - oldBytes[lastPos + i]);
/*     */         }
/*     */ 
/*     */         
/* 172 */         for (i = 0; i < scan - lenb - lastScan + lenf; i++) {
/* 173 */           eb[eblen + i] = newBytes[lastScan + lenf + i];
/*     */         }
/*     */         
/* 176 */         dblen += lenf;
/* 177 */         eblen += scan - lenb - lastScan + lenf;
/*     */         
/* 179 */         ControlBlock control = new ControlBlock();
/* 180 */         control.setDiffLength(lenf);
/* 181 */         control.setExtraLength(scan - lenb - lastScan + lenf);
/* 182 */         control.setSeekLength(position - lenb - lastPos + lenf);
/*     */         
/* 184 */         control.write((OutputStream)compressorOutputStream);
/*     */         
/* 186 */         lastScan = scan - lenb;
/* 187 */         lastPos = position - lenb;
/* 188 */         lastOffset = position - scan;
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     compressorOutputStream.close();
/*     */     
/* 194 */     Header header = new Header();
/* 195 */     header.setControlLength(byteOut.size());
/*     */     
/* 197 */     compressorOutputStream = compressor.createCompressorOutputStream(compression, byteOut);
/*     */     
/* 199 */     compressorOutputStream.write(db);
/* 200 */     compressorOutputStream.close();
/* 201 */     header.setDiffLength(byteOut.size() - header.getControlLength());
/*     */     
/* 203 */     compressorOutputStream = compressor.createCompressorOutputStream(compression, byteOut);
/*     */     
/* 205 */     compressorOutputStream.write(eb);
/* 206 */     compressorOutputStream.close();
/*     */     
/* 208 */     header.setOutputLength(newBytes.length);
/*     */     
/* 210 */     header.write(out);
/* 211 */     out.write(byteOut.toByteArray());
/*     */   }
/*     */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdiff\Diff.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */