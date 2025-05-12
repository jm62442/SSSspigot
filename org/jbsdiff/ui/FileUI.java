/*    */ package org.jbsdiff.ui;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.compress.compressors.CompressorException;
/*    */ import org.jbsdiff.DefaultDiffSettings;
/*    */ import org.jbsdiff.Diff;
/*    */ import org.jbsdiff.DiffSettings;
/*    */ import org.jbsdiff.InvalidHeaderException;
/*    */ import org.jbsdiff.Patch;
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
/*    */ public class FileUI
/*    */ {
/*    */   public static void diff(File oldFile, File newFile, File patchFile) throws CompressorException, FileNotFoundException, InvalidHeaderException, IOException {
/* 44 */     diff(oldFile, newFile, patchFile, "bzip2");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void diff(File oldFile, File newFile, File patchFile, String compression) throws CompressorException, FileNotFoundException, InvalidHeaderException, IOException {
/* 51 */     FileInputStream oldIn = new FileInputStream(oldFile);
/* 52 */     byte[] oldBytes = new byte[(int)oldFile.length()];
/* 53 */     oldIn.read(oldBytes);
/* 54 */     oldIn.close();
/*    */     
/* 56 */     FileInputStream newIn = new FileInputStream(newFile);
/* 57 */     byte[] newBytes = new byte[(int)newFile.length()];
/* 58 */     newIn.read(newBytes);
/* 59 */     newIn.close();
/*    */     
/* 61 */     FileOutputStream out = new FileOutputStream(patchFile);
/* 62 */     DefaultDiffSettings defaultDiffSettings = new DefaultDiffSettings(compression);
/* 63 */     Diff.diff(oldBytes, newBytes, out, (DiffSettings)defaultDiffSettings);
/* 64 */     out.close();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void patch(File oldFile, File newFile, File patchFile) throws CompressorException, FileNotFoundException, InvalidHeaderException, IOException {
/* 70 */     FileInputStream oldIn = new FileInputStream(oldFile);
/* 71 */     byte[] oldBytes = new byte[(int)oldFile.length()];
/* 72 */     oldIn.read(oldBytes);
/* 73 */     oldIn.close();
/*    */     
/* 75 */     FileInputStream patchIn = new FileInputStream(patchFile);
/* 76 */     byte[] patchBytes = new byte[(int)patchFile.length()];
/* 77 */     patchIn.read(patchBytes);
/* 78 */     patchIn.close();
/*    */     
/* 80 */     FileOutputStream out = new FileOutputStream(newFile);
/* 81 */     Patch.patch(oldBytes, patchBytes, out);
/* 82 */     out.close();
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdif\\ui\FileUI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */