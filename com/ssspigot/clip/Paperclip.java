/*     */ package com.ssspigot.clip;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.Permission;
/*     */ import java.security.Security;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import org.apache.commons.compress.compressors.CompressorException;
/*     */ import org.jbsdiff.Patch;
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
/*     */ public final class Paperclip
/*     */ {
/*     */   public static void main(String[] args) {
/*  57 */     Security.setProperty("crypto.policy", "unlimited");
/*     */ 
/*     */     
/*  60 */     Path paperJar = setupEnv();
/*  61 */     String main = getMainClass(paperJar);
/*  62 */     Method mainMethod = getMainMethod(paperJar, main);
/*  63 */     patch_v1_16(paperJar);
/*  64 */     Runtime.getRuntime().addShutdownHook(new Thread(() -> {
/*     */             try {
/*     */               Files.delete(paperJar);
/*  67 */             } catch (IOException iOException) {}
/*     */           }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  76 */       mainMethod.invoke(null, new Object[] { args });
/*  77 */     } catch (IllegalAccessException|InvocationTargetException e) {
/*  78 */       System.err.println("Error while running patched jar");
/*  79 */       e.printStackTrace();
/*  80 */       System.exit(1);
/*  81 */       throw new InternalError();
/*     */     } 
/*     */   } private static Path setupEnv() {
/*     */     Config config;
/*     */     MessageDigest digest;
/*     */     PatchData patchData;
/*     */     try {
/*  88 */       config = new Config();
/*  89 */     } catch (IOException e) {
/*  90 */       System.err.println("Error while read the config");
/*  91 */       e.printStackTrace();
/*  92 */       System.exit(1);
/*  93 */       throw new InternalError();
/*     */     } 
/*  95 */     if (config.license.equals("")) {
/*  96 */       System.err.println("Please fill in ssspigot-launcher.conf");
/*  97 */       System.exit(1);
/*  98 */       throw new InternalError();
/*     */     } 
/*     */     
/* 101 */     checkEncryptedPatch(config);
/* 102 */     if (config.updateOnly) {
/* 103 */       System.err.println("Done.");
/* 104 */       System.exit(0);
/* 105 */       throw new InternalError();
/*     */     } 
/* 107 */     HashMap<String, byte[]> bin = readEncryptedBinary(config);
/*     */ 
/*     */     
/*     */     try {
/* 111 */       digest = MessageDigest.getInstance("SHA-256");
/* 112 */     } catch (NoSuchAlgorithmException e) {
/* 113 */       System.err.println("Could not create hashing instance");
/* 114 */       e.printStackTrace();
/* 115 */       System.exit(1);
/* 116 */       throw new InternalError();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 121 */     try(InputStream defaultsInput = new ByteArrayInputStream(bin.get("PROPS")); 
/* 122 */         Reader defaultsReader = new BufferedReader(new InputStreamReader(defaultsInput)); 
/* 123 */         Reader optionalReader = getConfig()) {
/*     */       
/* 125 */       patchData = PatchData.parse(defaultsReader, optionalReader);
/* 126 */     } catch (IOException|IllegalArgumentException e) {
/* 127 */       if (e instanceof IOException) {
/* 128 */         System.err.println("Error reading patch file");
/*     */       } else {
/* 130 */         System.err.println("Invalid patch file");
/*     */       } 
/* 132 */       e.printStackTrace();
/* 133 */       System.exit(1);
/* 134 */       throw new InternalError();
/*     */     } 
/*     */     
/* 137 */     Path paperJar = checkPaperJar(bin.get("PATCH"), digest, patchData);
/*     */     
/* 139 */     return paperJar;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void patch_v1_16(Path paperJar) {
/*     */     try {
/* 145 */       Field getJarFSCacheField = Class.forName("net.minecraft.server.v1_16_R3.ResourcePackVanilla").getDeclaredField("getJarFSCache");
/* 146 */       getJarFSCacheField.setAccessible(true);
/* 147 */       getJarFSCacheField.set(null, FileSystems.newFileSystem(paperJar, (ClassLoader)null));
/* 148 */     } catch (IOException|ClassNotFoundException|NoSuchFieldException|IllegalAccessException e) {
/* 149 */       System.err.println("Failed to patch the jar");
/* 150 */       e.printStackTrace();
/* 151 */       System.exit(1);
/* 152 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static InputStream request(String path) throws IOException {
/* 157 */     IOException lastex = null;
/* 158 */     for (String repo : Params.repos) {
/*     */       try {
/* 160 */         return (new URL(repo + "/" + path)).openStream();
/* 161 */       } catch (IOException ex) {
/* 162 */         lastex = ex;
/*     */       } 
/*     */     } 
/* 165 */     throw lastex;
/*     */   }
/*     */   
/*     */   private static byte[] getPerVersionKey(Config config) throws IOException, GeneralSecurityException {
/* 169 */     try (InputStream stream = request("LICENSE-" + Utils.bytesToHex(Utils.sha1sum(config.license.getBytes())))) {
/* 170 */       byte[] a = Encrypt.decrypt(config.license.getBytes(), readFully(stream));
/* 171 */       return Encrypt.decrypt(Params.sharedKey, a);
/*     */     } 
/*     */   }
/*     */   private static void updateEncryptedPatch(Config config, byte[] perVersionKey) throws IOException, GeneralSecurityException {
/*     */     byte[] result, props;
/* 176 */     Path binPath = Paths.get("ssspigot2-1165.bin", new String[0]);
/* 177 */     Path binPathNew = Paths.get("ssspigot2-1165.bin.new", new String[0]);
/*     */     
/* 179 */     System.out.println("Downloading the binary ... (feel free to interrupt it by pressing Ctrl+C)");
/*     */ 
/*     */     
/* 182 */     try (InputStream stream = request("ssspigot.bin")) {
/* 183 */       result = Encrypt.decrypt(perVersionKey, readFully(stream));
/*     */     } 
/* 185 */     try (InputStream stream = request("ssspigot.bin.props")) {
/* 186 */       props = Encrypt.decrypt(perVersionKey, readFully(stream));
/*     */     } 
/*     */     
/* 189 */     try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
/* 190 */       try (ZipOutputStream zip = new ZipOutputStream(out)) {
/* 191 */         zip.putNextEntry(new ZipEntry("VER"));
/* 192 */         zip.write(Utils.sha1sum(perVersionKey));
/* 193 */         zip.closeEntry();
/*     */         
/* 195 */         zip.putNextEntry(new ZipEntry("PROPS"));
/* 196 */         zip.write(props);
/* 197 */         zip.closeEntry();
/*     */         
/* 199 */         zip.putNextEntry(new ZipEntry("PATCH"));
/* 200 */         zip.write(result);
/* 201 */         zip.closeEntry();
/*     */       } 
/* 203 */       Files.write(binPathNew, Encrypt.encrypt(config.license.getBytes(), out.toByteArray()), new OpenOption[0]);
/* 204 */       if (Files.exists(binPath, new java.nio.file.LinkOption[0])) {
/* 205 */         if (config.enableBackup) {
/* 206 */           String binOld = "ssspigot2-1165.bin.old." + DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now());
/* 207 */           Path binOldPath = Paths.get(binOld, new String[0]);
/* 208 */           Files.move(binPath, binOldPath, new CopyOption[] { StandardCopyOption.ATOMIC_MOVE });
/* 209 */           System.out.println("The old binary is backed up (" + binOld + "). Rename " + binOld + " to " + "ssspigot2-1165.bin" + " and disable auto updating if you want to restore it later.");
/*     */         } else {
/*     */           try {
/* 212 */             Files.delete(binPath);
/* 213 */           } catch (IOException iOException) {}
/*     */         } 
/*     */       }
/*     */       
/* 217 */       Files.move(binPathNew, binPath, new CopyOption[] { StandardCopyOption.ATOMIC_MOVE });
/* 218 */     } catch (IOException e) {
/* 219 */       System.err.println("Failed to load the binary");
/* 220 */       e.printStackTrace();
/* 221 */       System.exit(1);
/* 222 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static HashMap<String, byte[]> readEncryptedBinary(Config config) {
/* 227 */     Path binPath = Paths.get("ssspigot2-1165.bin", new String[0]);
/*     */     
/* 229 */     try (ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(Encrypt.decrypt(config.license.getBytes(), readBytes(binPath))))) {
/* 230 */       HashMap<String, byte[]> result = (HashMap)new HashMap<>();
/* 231 */       ZipEntry entry = null;
/* 232 */       while ((entry = zip.getNextEntry()) != null) {
/* 233 */         result.put(entry.getName(), readFullyWithoutCloseForZip(zip));
/*     */       }
/* 235 */       return result;
/*     */     }
/* 237 */     catch (IOException|GeneralSecurityException e) {
/* 238 */       System.err.println("Failed to verify the license. Please check launcher updates, your network and license");
/* 239 */       e.printStackTrace();
/* 240 */       System.exit(1);
/* 241 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void checkEncryptedPatch(Config config) {
/* 246 */     Path binPath = Paths.get("ssspigot2-1165.bin", new String[0]);
/* 247 */     if (!Files.exists(binPath, new java.nio.file.LinkOption[0])) {
/* 248 */       System.out.println("Getting the information ... (feel free to interrupt it by pressing Ctrl+C)");
/*     */       try {
/* 250 */         byte[] newVersionKey = getPerVersionKey(config);
/* 251 */         updateEncryptedPatch(config, newVersionKey);
/* 252 */       } catch (IOException|GeneralSecurityException e) {
/* 253 */         System.err.println("Failed to download the binary. Please check launcher updates, your network and license");
/* 254 */         e.printStackTrace();
/* 255 */         System.exit(1);
/* 256 */         throw new InternalError();
/*     */       } 
/* 258 */     } else if (config.enableAutoUpdate) {
/*     */       try {
/* 260 */         System.out.println("Checking update ... (feel free to interrupt it by pressing Ctrl+C)\nNote that we use the same update mechanism as paper, ie all versions are published without fully testing due to technical limitations. Please test locally and then manually update SSSpigot on the server or upload the binary (ssspigot2-1165.bin) to the server with automatic updating turned off if possible.");
/* 261 */         byte[] oldVersionKeySum = readEncryptedBinary(config).get("VER");
/* 262 */         byte[] newVersionKey = getPerVersionKey(config);
/* 263 */         if (!Arrays.equals(oldVersionKeySum, Utils.sha1sum(newVersionKey))) {
/* 264 */           updateEncryptedPatch(config, newVersionKey);
/*     */         }
/* 266 */       } catch (IOException|GeneralSecurityException e) {
/* 267 */         System.err.println("Failed to download the binary. Please check launcher updates, your network and license");
/* 268 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Path checkPaperJar(byte[] patch, MessageDigest digest, PatchData patchData) {
/* 278 */     Path paperJar, cache = Paths.get("cache", new String[0]);
/*     */     
/*     */     try {
/* 281 */       paperJar = Files.createTempFile("ssspigot2", ".jar", (FileAttribute<?>[])new FileAttribute[0]);
/* 282 */     } catch (IOException e) {
/* 283 */       System.err.println("Failed to create temp file");
/* 284 */       e.printStackTrace();
/* 285 */       System.exit(1);
/* 286 */       throw new InternalError();
/*     */     } 
/*     */     
/* 289 */     Path vanillaJar = checkVanillaJar(digest, patchData, cache);
/*     */     
/* 291 */     if (Files.exists(paperJar, new java.nio.file.LinkOption[0])) {
/*     */       try {
/* 293 */         Files.delete(paperJar);
/* 294 */       } catch (IOException e) {
/* 295 */         System.err.println("Failed to delete invalid jar " + paperJar.toAbsolutePath());
/* 296 */         e.printStackTrace();
/* 297 */         System.exit(1);
/* 298 */         throw new InternalError();
/*     */       } 
/*     */     }
/*     */     
/* 302 */     System.out.println("Patching upstream jar...");
/* 303 */     byte[] vanillaJarBytes = readBytes(vanillaJar);
/*     */ 
/*     */ 
/*     */     
/* 307 */     try (OutputStream jarOutput = new BufferedOutputStream(
/* 308 */           Files.newOutputStream(paperJar, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING }))) {
/*     */       
/* 310 */       Patch.patch(vanillaJarBytes, patch, jarOutput);
/* 311 */     } catch (CompressorException|org.jbsdiff.InvalidHeaderException|IOException e) {
/* 312 */       System.err.println("Failed to patch upstream jar");
/* 313 */       e.printStackTrace();
/* 314 */       System.exit(1);
/* 315 */       throw new InternalError();
/*     */     } 
/*     */ 
/*     */     
/* 319 */     if (isJarInvalid(digest, paperJar, patchData.patchedHash)) {
/* 320 */       System.err.println("Failed to patch upstream jar, output patched jar is still not valid");
/* 321 */       System.exit(1);
/* 322 */       throw new InternalError();
/*     */     } 
/*     */     
/* 325 */     return paperJar;
/*     */   }
/*     */   
/*     */   private static class InnerSecurityManager extends SecurityManager { static class ExitException extends Exception {
/*     */       final int status;
/*     */       
/*     */       ExitException(int status) {
/* 332 */         super("ExitException " + status);
/* 333 */         this.status = status;
/*     */       }
/*     */     }
/*     */     boolean enabled = true;
/*     */     
/*     */     public void checkExit(int status) {
/* 339 */       if (this.enabled) {
/* 340 */         throw new SecurityException(new ExitException(status));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void checkPermission(Permission perm) {}
/*     */     
/*     */     private InnerSecurityManager() {} }
/*     */ 
/*     */   
/*     */   private static Path checkVanillaJar(MessageDigest digest, PatchData patchData, Path cache) {
/* 351 */     Path vanillaJar = cache.resolve("upstream_" + patchData.version + ".jar");
/* 352 */     Path resultJar = cache.resolve("patched_" + patchData.version + ".jar");
/* 353 */     if (!isJarInvalid(digest, resultJar, patchData.originalHash)) {
/* 354 */       return resultJar;
/*     */     }
/*     */     
/* 357 */     System.out.println("Downloading upstream jar...");
/*     */     try {
/* 359 */       if (!Files.isDirectory(cache, new java.nio.file.LinkOption[0])) {
/* 360 */         Files.createDirectories(cache, (FileAttribute<?>[])new FileAttribute[0]);
/*     */       }
/* 362 */       Files.deleteIfExists(vanillaJar);
/* 363 */       Files.deleteIfExists(resultJar);
/* 364 */     } catch (IOException e) {
/* 365 */       System.err.println("Failed to setup cache directory");
/* 366 */       e.printStackTrace();
/* 367 */       System.exit(1);
/* 368 */       throw new InternalError();
/*     */     } 
/*     */ 
/*     */     
/* 372 */     try(ReadableByteChannel source = Channels.newChannel(patchData.originalUrl.openStream()); 
/* 373 */         FileChannel fileChannel = FileChannel.open(vanillaJar, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING })) {
/*     */       
/* 375 */       fileChannel.transferFrom(source, 0L, Long.MAX_VALUE);
/* 376 */     } catch (IOException e) {
/* 377 */       System.err.println("Failed to download upstream jar");
/* 378 */       e.printStackTrace();
/* 379 */       System.exit(1);
/* 380 */       throw new InternalError();
/*     */     } 
/*     */     
/* 383 */     SecurityManager oldSecurityManager = System.getSecurityManager();
/* 384 */     InnerSecurityManager innerSecurityManager = new InnerSecurityManager();
/* 385 */     System.setSecurityManager(innerSecurityManager);
/* 386 */     System.setProperty("paperclip.patchonly", "true");
/*     */     try {
/*     */       try {
/* 389 */         Class.forName("io.papermc.paperclip.Paperclip", true, new URLClassLoader(new URL[] { vanillaJar.toUri().toURL() })).getMethod("main", new Class[] { String[].class }).invoke(null, new Object[] { { "-version" } });
/* 390 */       } catch (InvocationTargetException rawe) {
/* 391 */         Throwable rawecause = rawe.getCause();
/* 392 */         if (!(rawecause instanceof SecurityException)) throw new InvocationTargetException(rawe); 
/* 393 */         SecurityException e = (SecurityException)rawecause;
/* 394 */         Throwable cause = e.getCause();
/* 395 */         if (!(cause instanceof InnerSecurityManager.ExitException)) throw new InvocationTargetException(rawe); 
/* 396 */         int status = ((InnerSecurityManager.ExitException)cause).status;
/* 397 */         if (0 != status) throw new InvocationTargetException(rawe); 
/*     */       } 
/* 399 */     } catch (MalformedURLException|ClassNotFoundException|NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
/* 400 */       System.err.println("Error while patching the vanilla jar");
/* 401 */       e.printStackTrace();
/* 402 */       System.exit(1);
/* 403 */       throw new InternalError();
/*     */     } 
/* 405 */     innerSecurityManager.enabled = false;
/* 406 */     System.setSecurityManager(oldSecurityManager);
/*     */ 
/*     */     
/* 409 */     if (isJarInvalid(digest, resultJar, patchData.originalHash)) {
/* 410 */       System.err.println("Downloaded upstream jar is not valid");
/* 411 */       System.exit(1);
/* 412 */       throw new InternalError();
/*     */     } 
/*     */     
/* 415 */     return resultJar;
/*     */   } static class ExitException extends Exception {
/*     */     final int status; ExitException(int status) { super("ExitException " + status);
/*     */       this.status = status; } } private static String getMainClass(Path paperJar) {
/* 419 */     return "org.bukkit.craftbukkit.Main";
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
/*     */   private static Method getMainMethod(Path paperJar, String mainClass) {
/* 434 */     Agent.addToClassPath(paperJar);
/*     */     try {
/* 436 */       Class<?> cls = Class.forName(mainClass, true, ClassLoader.getSystemClassLoader());
/* 437 */       return cls.getMethod("main", new Class[] { String[].class });
/* 438 */     } catch (NoSuchMethodException|ClassNotFoundException e) {
/* 439 */       System.err.println("Failed to find main method in patched jar");
/* 440 */       e.printStackTrace();
/* 441 */       System.exit(1);
/* 442 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Reader getConfig() throws IOException {
/* 447 */     Path customPatchInfo = Paths.get("paperclip.properties", new String[0]);
/* 448 */     if (Files.exists(customPatchInfo, new java.nio.file.LinkOption[0])) {
/* 449 */       return Files.newBufferedReader(customPatchInfo);
/*     */     }
/* 451 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] readFullyWithoutCloseForZip(ZipInputStream in) throws IOException {
/* 458 */     byte[] buffer = new byte[16384];
/* 459 */     int off = 0;
/*     */     int read;
/* 461 */     while ((read = in.read(buffer, off, buffer.length - off)) != -1) {
/* 462 */       off += read;
/* 463 */       if (off == buffer.length) {
/* 464 */         buffer = Arrays.copyOf(buffer, buffer.length * 2);
/*     */       }
/*     */     } 
/* 467 */     return Arrays.copyOfRange(buffer, 0, off);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] readFully(InputStream in) throws IOException {
/*     */     try {
/* 474 */       byte[] buffer = new byte[16384];
/* 475 */       int off = 0;
/*     */       int read;
/* 477 */       while ((read = in.read(buffer, off, buffer.length - off)) != -1) {
/* 478 */         off += read;
/* 479 */         if (off == buffer.length) {
/* 480 */           buffer = Arrays.copyOf(buffer, buffer.length * 2);
/*     */         }
/*     */       } 
/* 483 */       return Arrays.copyOfRange(buffer, 0, off);
/*     */     } finally {
/* 485 */       in.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static byte[] readBytes(Path file) {
/*     */     try {
/* 491 */       return readFully(Files.newInputStream(file, new OpenOption[0]));
/* 492 */     } catch (IOException e) {
/* 493 */       System.err.println("Failed to read all of the data from " + file.toAbsolutePath());
/* 494 */       e.printStackTrace();
/* 495 */       System.exit(1);
/* 496 */       throw new InternalError();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean isJarInvalid(MessageDigest digest, Path jar, byte[] hash) {
/* 501 */     if (Files.exists(jar, new java.nio.file.LinkOption[0])) {
/* 502 */       byte[] jarBytes = readBytes(jar);
/* 503 */       return !Arrays.equals(hash, digest.digest(jarBytes));
/*     */     } 
/* 505 */     return true;
/*     */   }
/*     */ }


/* Location:              E:\game\SSSpigotLauncher.jar!\com\ssspigot\clip\Paperclip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */