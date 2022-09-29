#ifndef VALUE_H
#define VALUE_H

typedef double value;

typedef struct value_array value_array;
struct value_array
{
  u32 MaxCount;
  u32 Count;
  value *Values;
};

fdecl void ValueArrayInit(value_array *Array);
fdecl void ValueArrayAppend(value_array *Array, value Value);
fdecl void ValueArrayFree(value_array *Array);
fdecl void ValuePrint(value Value);

fn void ValuePrint(value Value)
{
  printf("%g", Value);
  return;
}
fn void ValueArrayInit(value_array *Array)
{
  Array->Values = NULL;
  Array->MaxCount = 0;
  Array->Count = 0;
  return;
}
fn void ValueArrayAppend(value_array *Array, value Value)
{
  if(Array->MaxCount < Array->Count + 1)
  {
    u32 OldMaxCount = Array->MaxCount;
    Array->MaxCount = MemGrowCapacity(OldMaxCount);
    Array->Values = ArrayGrow(value, Array->Values, OldMaxCount, Array->MaxCount);
    // TODO(MIGUEL): Find a way to zero initialize unused secions of the realloc
  }
  Array->Values[Array->Count] = Value;
  Array->Count++;
  return;
}
fn void ValueArrayFree(value_array *Array)
{
  ArrayFree(value, Array->Values, Array->MaxCount);
  ValueArrayInit(Array);
  return;
}
#endif //VALUE_H
