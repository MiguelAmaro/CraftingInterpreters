#ifndef VM_H
#define VM_H

#define VALUE_STACK_MAX (256)
typedef struct vm vm;
struct vm
{
  chunk *Chunk;
  u8 *IP; //Instruction Pointer
  value  Stack[VALUE_STACK_MAX];
  value *StackTop; // NOTE(MIGUEL): Pointer Index is faster. Maybe?
};
typedef enum interpret_res interpret_res;
enum interpret_res
{
  INTERPRET_OK,
  INTERPRET_COMPILE_ERROR,
  INTERPRET_RUNTIME_ERROR,
};

global vm VM;

fdecl internal interpret_res Run(void);
fdecl void VMInit(void);
fdecl void VMFree(void);
fdecl interpret_res VMInterpret(chunk *Chunk);
fdecl void  VMPush(value Value);
fdecl value VMPop(void);

fn internal interpret_res VMRun(void)
{
#define ReadByte() (*VM.IP++)
#define ReadConstant() (VM.Chunk->Consts.Values[ReadByte()])
  for(;;)
  {
#ifdef DebugTraceExec
    DbgDissasembleInstruction(VM.Chunk, (u32)(VM.IP - VM.Chunk->Code));
#endif
    u8 Instruction;
    switch(Instruction = ReadByte())
    {
      case Op_Return:
      {
        return INTERPRET_OK;
      }break;
      case Op_Const:
      {
        value Const = ReadConstant();
        ValuePrint(Const);
        printf("\n");
        break;
      }break;
    }
  }
#undef ReadByte
#undef ReadConstant
}
fn internal void VMStackReset(void)
{
  VM.StackTop = VM.Stack;
  return;
}
fn void VMInit()
{
  VMStackReset();
  return;
}
fn void VMFree()
{
  
  return;
}
fn void  VMPush(value Value)
{
  *VM.StackTop = Value; VM.StackTop++;
  return;
}
fn value VMPop(void)
{
  VM.StackTop--;
  return *VM.StackTop;
}
fn interpret_res VMInterpret(chunk *Chunk)
{
  VM.Chunk = Chunk;
  VM.IP = VM.Chunk->Code;
  return VMRun();
}

#endif //VM_H
