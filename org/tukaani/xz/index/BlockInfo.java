package org.tukaani.xz.index;

import org.tukaani.xz.common.StreamFlags;

public class BlockInfo {
  public StreamFlags streamFlags;
  
  public long compressedOffset;
  
  public long uncompressedOffset;
  
  public long unpaddedSize;
  
  public long uncompressedSize;
}


/* Location:              E:\game\SSSpigotLauncher.jar!\org\tukaani\xz\index\BlockInfo.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */