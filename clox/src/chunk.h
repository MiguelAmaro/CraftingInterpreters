/* date = April 19th 2022 6:18 pm */

#ifndef CHUNK_H
#define CHUNK_H

#include "common.h"
#include "debug.h"

typedef enum opcode opcode
enum opcode 
{
  Op_Return;
  Op_Count;
};

typedef struct chunk chunk
struct chunk
{
  u32 Count;
  u32 Capacity;
  u8 *Code;
};

void InitChunk(chunk *Chunk)
{
  Chunk->Count    = 0;
  Chunk->Capacity = 0;
  Chunk->Code     = 0;
  
  
  return;
}

void AddChunk(chunk *Chunk, u8 Byte)
{
  if(Chunk->Capacity < Chunk->Count + 1)
  {
    u32 OldCapacity = Chunk->Capacity;
    Chunk->Capacity = MemGrowCapacity(OldCapacity);
    Chunk->Code = MemGrowArray(u8, Chunk->Code, OldCapacity, Chunk->Capacity);
    Chunk->Code[Chunk->Count] = Byte;
  }
  
  return;
}

void FreeChunk(chunk* Chunk)
{
  MemFreeArray(u8, Chunk->Code, Chunk->Capacity);
  InitChunk(Chunk);
  
  return;
}

#endif //CHUNK_H
