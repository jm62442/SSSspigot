package org.tukaani.xz;

import java.io.InputStream;

class DeltaDecoder extends DeltaCoder implements FilterDecoder {
  private final int distance;
  
  DeltaDecoder(byte[] paramArrayOfbyte) throws UnsupportedOptionsException {
    if (paramArrayOfbyte.length != 1)
      throw new UnsupportedOptionsException("Unsupported Delta filter properties"); 
    this.distance = (paramArrayOfbyte[0] & 0xFF) + 1;
  }
  
  public int getMemoryUsage() {
    return 1;
  }
  
  public InputStream getInputStream(InputStream paramInputStream) {
    return new DeltaInputStream(paramInputStream, this.distance);
  }
}


/* Location:              E:\game\SSSpigotLauncher.jar!\org\tukaani\xz\DeltaDecoder.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */