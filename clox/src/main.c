#include <stdlib.h>
#include <stdio.h>
#include "types.h"
#include "memory.h"
#include "value.h"
#include "chunk.h"
#include "debug.h"
#include "vm.h"
// NOTE(MIGUEL): 'value' type is just u64
// NOTE(MIGUEL): at pg.276
int main(int ArgCount, const char *Args)
{
  VMInit();
  chunk Chunk;
  ChunkInit(&Chunk);
  // CONSTANTS
  u32 Const = ChunkAppendConst(&Chunk, 1.2);
  ChunkAppend(&Chunk, Op_Const, 123);
  ChunkAppend(&Chunk, (u8)Const, 123);
  // RETRURN
  ChunkAppend(&Chunk, Op_Return, 123);
  DbgDissasembleChunk(&Chunk, "fuck chunk");
  VMInterpret(&Chunk);
  VMFree();
  ChunkFree(&Chunk);
  
  return 0;
}