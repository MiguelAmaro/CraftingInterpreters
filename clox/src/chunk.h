#ifndef CHUNK_H
#define CHUNK_H

typedef enum opcode opcode;
enum opcode 
{
  Op_Const,
  Op_Return,
  Op_Count,
};

typedef struct chunk chunk;
struct chunk
{
  u32 Count;
  u32 Capacity;
  u8 *Code;
  u32 *Lines;
  value_array Consts;
};
fdecl void ChunkInit(chunk *Chunk);
fdecl void ChunkAppend(chunk *Chunk, u8 Byte, u32 Line);
fdecl void ChunkFree(chunk* Chunk);

fn void ChunkInit(chunk *Chunk)
{
  Chunk->Count    = 0;
  Chunk->Capacity = 0;
  Chunk->Code     = NULL;
  Chunk->Lines    = NULL;
  ValueArrayInit(&Chunk->Consts);
  return;
}
fn void ChunkFree(chunk* Chunk)
{
  ArrayFree(u8, Chunk->Code, Chunk->Capacity);
  ArrayFree(u32, Chunk->Lines, Chunk->Capacity);
  ValueArrayFree(&Chunk->Consts);
  ChunkInit(Chunk);
  return;
}
fn void ChunkAppend(chunk *Chunk, u8 Byte, u32 Line)
{
  if(Chunk->Capacity < Chunk->Count + 1)
  {
    u32 OldCapacity = Chunk->Capacity;
    Chunk->Capacity = MemGrowCapacity(OldCapacity);
    Chunk->Code  = ArrayGrow(u8 , Chunk->Code , OldCapacity, Chunk->Capacity);
    Chunk->Lines = ArrayGrow(u32, Chunk->Lines, OldCapacity, Chunk->Capacity);
  }
  Chunk->Code [Chunk->Count] = Byte;
  Chunk->Lines[Chunk->Count] = Line;
  Chunk->Count++;
  return;
}
fn u32 ChunkAppendConst(chunk* Chunk, value Value)
{
  ValueArrayAppend(&Chunk->Consts, Value);
  return Chunk->Consts.Count - 1; //NOTE: Returning the index into the Consts Array for the elm we just added.
}

#endif //CHUNK_H
