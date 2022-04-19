#include "common.h"
#include "chunk.h"

int main(int ArgCount, const char *Args)
{
  chunk Chunk;
  InitChunk(&Chunk);
  AddChunk(&Chunk, Op_Return);
  DbgDissasembleChunk();
  FreeChunk(&Chunk);
  
  return 0;
}