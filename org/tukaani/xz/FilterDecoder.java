package org.tukaani.xz;

import java.io.InputStream;

interface FilterDecoder extends FilterCoder {
  int getMemoryUsage();
  
  InputStream getInputStream(InputStream paramInputStream);
}


/* Location:              E:\game\SSSpigotLauncher.jar!\org\tukaani\xz\FilterDecoder.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */