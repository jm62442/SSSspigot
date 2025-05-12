/*     */ package org.jbsdiff.sort;
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
/*     */ public class SuffixSort
/*     */ {
/*     */   public static void qsufsort(int[] I, int[] V, byte[] data) {
/*  35 */     int[] buckets = new int[256];
/*     */     
/*     */     int i;
/*  38 */     for (i = 0; i < data.length; i++) {
/*  39 */       buckets[data[i] & 0xFF] = buckets[data[i] & 0xFF] + 1;
/*     */     }
/*     */     
/*  42 */     for (i = 1; i < 256; i++) {
/*  43 */       buckets[i] = buckets[i] + buckets[i - 1];
/*     */     }
/*     */     
/*  46 */     for (i = 255; i > 0; i--) {
/*  47 */       buckets[i] = buckets[i - 1];
/*     */     }
/*     */     
/*  50 */     buckets[0] = 0;
/*     */     
/*  52 */     for (i = 0; i < data.length; i++) {
/*  53 */       buckets[data[i] & 0xFF] = buckets[data[i] & 0xFF] + 1; I[buckets[data[i] & 0xFF] + 1] = i;
/*     */     } 
/*     */     
/*  56 */     I[0] = data.length;
/*     */     
/*  58 */     for (i = 0; i < data.length; i++) {
/*  59 */       V[i] = buckets[data[i] & 0xFF];
/*     */     }
/*     */     
/*  62 */     V[data.length] = 0;
/*     */     
/*  64 */     for (i = 1; i < 256; i++) {
/*  65 */       if (buckets[i] == buckets[i - 1] + 1) {
/*  66 */         I[buckets[i]] = -1;
/*     */       }
/*     */     } 
/*     */     
/*  70 */     I[0] = -1;
/*     */     int h;
/*  72 */     for (h = 1; I[0] != -(data.length + 1); h += h) {
/*  73 */       int len = 0;
/*  74 */       for (i = 0; i < data.length + 1; ) {
/*  75 */         if (I[i] < 0) {
/*  76 */           len -= I[i];
/*  77 */           i -= I[i]; continue;
/*     */         } 
/*  79 */         if (len != 0) {
/*  80 */           I[i - len] = -len;
/*     */         }
/*     */         
/*  83 */         len = V[I[i]] + 1 - i;
/*  84 */         split(I, V, i, len, h);
/*  85 */         i += len;
/*  86 */         len = 0;
/*     */       } 
/*     */       
/*  89 */       if (len != 0) {
/*  90 */         I[i - len] = -len;
/*     */       }
/*     */     } 
/*     */     
/*  94 */     for (i = 0; i < data.length + 1; i++) {
/*  95 */       I[V[i]] = i;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void split(int[] I, int[] V, int start, int len, int h) {
/* 102 */     if (len < 16) {
/* 103 */       int m; for (m = start; m < start + len; m += i1) {
/* 104 */         int i1 = 1;
/* 105 */         int i2 = V[I[m] + h];
/*     */         int n;
/* 107 */         for (n = 1; m + n < start + len; n++) {
/* 108 */           if (V[I[m + n] + h] < i2) {
/* 109 */             i2 = V[I[m + n] + h];
/* 110 */             i1 = 0;
/*     */           } 
/* 112 */           if (V[I[m + n] + h] == i2) {
/* 113 */             int tmp = I[m + i1];
/* 114 */             I[m + i1] = I[m + n];
/* 115 */             I[m + n] = tmp;
/* 116 */             i1++;
/*     */           } 
/*     */         } 
/* 119 */         for (n = 0; n < i1; n++) {
/* 120 */           V[I[m + n]] = m + i1 - 1;
/*     */         }
/* 122 */         if (i1 == 1) {
/* 123 */           I[m] = -1;
/*     */         }
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 129 */     int x = V[I[start + len / 2] + h];
/* 130 */     int jj = 0;
/* 131 */     int kk = 0; int i;
/* 132 */     for (i = start; i < start + len; i++) {
/* 133 */       if (V[I[i] + h] < x) {
/* 134 */         jj++;
/*     */       }
/*     */       
/* 137 */       if (V[I[i] + h] == x) {
/* 138 */         kk++;
/*     */       }
/*     */     } 
/* 141 */     jj += start;
/* 142 */     kk += jj;
/*     */     
/* 144 */     i = start;
/* 145 */     int j = 0;
/* 146 */     int k = 0;
/* 147 */     while (i < jj) {
/* 148 */       if (V[I[i] + h] < x) {
/* 149 */         i++; continue;
/* 150 */       }  if (V[I[i] + h] == x) {
/* 151 */         int m = I[i];
/* 152 */         I[i] = I[jj + j];
/* 153 */         I[jj + j] = m;
/* 154 */         j++; continue;
/*     */       } 
/* 156 */       int tmp = I[i];
/* 157 */       I[i] = I[kk + k];
/* 158 */       I[kk + k] = tmp;
/* 159 */       k++;
/*     */     } 
/*     */ 
/*     */     
/* 163 */     while (jj + j < kk) {
/* 164 */       if (V[I[jj + j] + h] == x) {
/* 165 */         j++; continue;
/*     */       } 
/* 167 */       int tmp = I[jj + j];
/* 168 */       I[jj + j] = I[kk + k];
/* 169 */       I[kk + k] = tmp;
/* 170 */       k++;
/*     */     } 
/*     */ 
/*     */     
/* 174 */     if (jj > start) {
/* 175 */       split(I, V, start, jj - start, h);
/*     */     }
/*     */     
/* 178 */     for (i = 0; i < kk - jj; i++) {
/* 179 */       V[I[jj + i]] = kk - 1;
/*     */     }
/*     */     
/* 182 */     if (jj == kk - 1) {
/* 183 */       I[jj] = -1;
/*     */     }
/*     */     
/* 186 */     if (start + len > kk) {
/* 187 */       split(I, V, kk, start + len - kk, h);
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
/*     */   
/*     */   private static int matchLength(byte[] bytesA, int offsetA, byte[] bytesB, int offsetB) {
/* 205 */     int oldLimit = bytesA.length - offsetA;
/* 206 */     int newLimit = bytesB.length - offsetB;
/*     */     
/*     */     int i;
/* 209 */     for (i = 0; i < oldLimit && i < newLimit && 
/* 210 */       bytesA[i + offsetA] == bytesB[i + offsetB]; i++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 215 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SearchResult search(int[] I, byte[] oldBytes, int oldOffset, byte[] newBytes, int newOffset, int start, int end) {
/* 224 */     if (end - start < 2) {
/*     */       
/* 226 */       int x = matchLength(oldBytes, I[start], newBytes, newOffset);
/* 227 */       int y = matchLength(oldBytes, I[end], newBytes, newOffset);
/*     */       
/* 229 */       if (x > y) {
/* 230 */         return new SearchResult(x, I[start]);
/*     */       }
/* 232 */       return new SearchResult(y, I[end]);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 237 */     int center = start + (end - start) / 2;
/* 238 */     if (compareBytes(oldBytes, I[center], newBytes, newOffset) < 0) {
/* 239 */       return search(I, oldBytes, 0, newBytes, newOffset, center, end);
/*     */     }
/* 241 */     return search(I, oldBytes, 0, newBytes, newOffset, start, center);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int compareBytes(byte[] bytesA, int offsetA, byte[] bytesB, int offsetB) {
/* 266 */     int length = Math.min(bytesA.length - offsetA, bytesB.length - offsetB);
/*     */     
/* 268 */     int valA = 0, valB = 0;
/* 269 */     for (int i = 0; i < length; i++) {
/* 270 */       valA = bytesA[i + offsetA] & 0xFF;
/* 271 */       valB = bytesB[i + offsetB] & 0xFF;
/*     */       
/* 273 */       if (valA != valB) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 278 */     return valA - valB;
/*     */   }
/*     */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdiff\sort\SuffixSort.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */