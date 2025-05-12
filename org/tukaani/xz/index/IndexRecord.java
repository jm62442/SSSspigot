package org.tukaani.xz.index;

class IndexRecord {
  final long unpadded;
  
  final long uncompressed;
  
  IndexRecord(long paramLong1, long paramLong2) {
    this.unpadded = paramLong1;
    this.uncompressed = paramLong2;
  }
}


/* Location:              E:\game\SSSpigotLauncher.jar!\org\tukaani\xz\index\IndexRecord.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */