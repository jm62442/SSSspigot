/*    */ package META-INF.versions.9.com.ssspigot.clip;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.instrument.Instrumentation;
/*    */ import java.nio.file.Path;
/*    */ import java.util.jar.JarFile;
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
/*    */ public final class Agent
/*    */ {
/* 19 */   private static Instrumentation inst = null;
/*    */   
/*    */   public static void agentmain(String agentArgs, Instrumentation inst) {
/* 22 */     com.ssspigot.clip.Agent.inst = inst;
/*    */   }
/*    */ 
/*    */   
/*    */   static void addToClassPath(Path paperJar) {
/* 27 */     if (inst == null) {
/* 28 */       System.err.println("Unable to retrieve Instrumentation API to add Paper jar to ClassPath");
/* 29 */       System.exit(1);
/*    */       return;
/*    */     } 
/*    */     try {
/* 33 */       inst.appendToSystemClassLoaderSearch(new JarFile(paperJar.toFile()));
/* 34 */       inst = null;
/* 35 */     } catch (IOException e) {
/* 36 */       System.err.println("Failed to add Paper jar to ClassPath");
/* 37 */       e.printStackTrace();
/* 38 */       System.exit(1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\META-INF\versions\9\com\ssspigot\clip\Agent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */