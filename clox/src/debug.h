/* date = April 19th 2022 7:18 pm */

#ifndef DEBUG_H
#define DEBUG_H

#include "chunk.h"

void DbgDissasembleChunk(Chunk *chunk, u8 *Name);
int  DbgDissasembleInstruction(Chunk *chunk, u32 Offset);

#endif //DEBUG_H
