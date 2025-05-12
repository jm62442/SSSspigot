/*    */ package org.jbsdiff.ui;
/*    */ 
/*    */ import java.io.File;
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
/*    */ public class CLI
/*    */ {
/*    */   public static void main(String[] args) throws Exception {
/* 38 */     if (args.length < 4) {
/* 39 */       System.out.println("Not enough parameters!");
/* 40 */       System.out.println();
/* 41 */       printUsage();
/*    */     } 
/*    */     
/* 44 */     String compression = System.getProperty("jbsdiff.compressor", "bzip2");
/* 45 */     compression = compression.toLowerCase();
/*    */     
/*    */     try {
/* 48 */       String command = args[0].toLowerCase();
/* 49 */       File oldFile = new File(args[1]);
/* 50 */       File newFile = new File(args[2]);
/* 51 */       File patchFile = new File(args[3]);
/*    */       
/* 53 */       if (command.equals("diff")) {
/* 54 */         FileUI.diff(oldFile, newFile, patchFile, compression);
/* 55 */       } else if (command.equals("patch")) {
/* 56 */         FileUI.patch(oldFile, newFile, patchFile);
/*    */       } else {
/* 58 */         printUsage();
/*    */       }
/*    */     
/* 61 */     } catch (Exception e) {
/* 62 */       e.printStackTrace();
/* 63 */       System.exit(1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void printUsage() {
/* 68 */     String nl = System.lineSeparator();
/* 69 */     String usage = "Usage: COMMAND oldfile newfile patchfile" + nl + nl + "Where COMMAND is either 'diff' or 'patch.'" + nl + nl + "The jbsdiff.compressor property can be used to select a different " + nl + "compression scheme at runtime:" + nl + nl + "    java -Djbsdiff.compressor=gz -jar jbsdiff-?.?.jar diff " + "a.bin b.bin patch.gz" + nl + nl + "Supported compression schemes: bzip2 (default), gz, pack200, xz." + nl + nl + "The compression algorithm used will be detected automatically during " + nl + "patch operations.  NOTE: algorithms other than bzip2 are incompatible " + nl + "with the reference implementation of bsdiff!";
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
/* 84 */     System.out.println(usage);
/* 85 */     System.exit(1);
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\org\jbsdif\\ui\CLI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */