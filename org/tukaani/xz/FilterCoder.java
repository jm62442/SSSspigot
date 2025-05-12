package org.tukaani.xz;

interface FilterCoder {
  boolean changesSize();
  
  boolean nonLastOK();
  
  boolean lastOK();
}


/* Location:              E:\game\SSSpigotLauncher.jar!\org\tukaani\xz\FilterCoder.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */