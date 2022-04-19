/* date = April 19th 2022 6:56 pm */

#ifndef MEMORY_H
#define MEMORY_H

#include "common.h"
#include <stdlib.h>

#define MemGrowCapacity(Capacity) ((Capacity)<8? 8 : (Capacity)*2)
#define MemGrowArray(type, Ptr, OldCount, NewCount) \
(type*)MemRealloc(Ptr, sizeof(type)*(OldCount),  sizeof(type)*(NewCount))
#define MemFreeArray(type, Ptr, OldCount) MemRealloc(Ptr, sizeof(type)*(OldCount), 0)

void *MemRealloc(void *Ptr, size_t OldSize, size_t NewSize)
{
  if(NewSize == 0)
  {
    free(Ptr);
    return (void *)0;
  }
  
  void *Result = realloc(Ptr, NewSize);
  if(Result == 0) exit(1);
  return Result;
}


#endif //MEMORY_H
