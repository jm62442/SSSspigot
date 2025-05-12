package org.tukaani.xz;

interface FilterEncoder extends FilterCoder {
  long getFilterID();
  
  byte[] getFilterProps();
  
  boolean supportsFlushing();
  
  FinishableOutputStream getOutputStream(FinishableOutputStream paramFinishableOutputStream);
}


/* Location:              E:\game\SSSpigotLauncher.jar!\org\tukaani\xz\FilterEncoder.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */