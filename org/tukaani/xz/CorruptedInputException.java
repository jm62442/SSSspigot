package org.tukaani.xz;

public class CorruptedInputException extends XZIOException {
  private static final long serialVersionUID = 3L;
  
  public CorruptedInputException() {
    super("Compressed data is corrupt");
  }
  
  public CorruptedInputException(String paramString) {
    super(paramString);
  }
}


/* Location:              E:\game\SSSpigotLauncher.jar!\org\tukaani\xz\CorruptedInputException.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */