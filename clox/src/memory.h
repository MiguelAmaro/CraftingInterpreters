#ifndef MEMORY_H
#define MEMORY_H


#define MemGrowCapacity(Capacity) ((Capacity)<8? 8 : (Capacity)*2)
#define ArrayGrow(type, Ptr, OldCount, NewCount) \
(type*)MemRealloc(Ptr, sizeof(type)*(OldCount),  sizeof(type)*(NewCount))
#define ArrayFree(type, Ptr, OldCount) MemRealloc(Ptr, sizeof(type)*(OldCount), 0)

fn void *MemRealloc(void *PtrOld, size_t OldSize, size_t NewSize)
{
  if(NewSize == 0)
  {
    free(PtrOld);
    return (void *)0;
  }
  
  void *Result = realloc(PtrOld, NewSize);
  Assert(Result != NULL);
  return Result;
}


#endif //MEMORY_H
