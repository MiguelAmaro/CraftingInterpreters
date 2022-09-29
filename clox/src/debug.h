#ifndef DEBUG_H
#define DEBUG_H

fdecl void DbgDissasembleChunk(chunk *Chunk, u8 *Name);
fdecl u32  DbgDissasembleInstruction(chunk *Chunk, u32 Offset);

fn internal u32 DbgSimpleInstruction(u8 * Name, u32 Offset)
{
  printf("%s\n", Name);
  return (Offset + 1);
}
fn internal u32 DbgConstantInstruction(u8 * Name, chunk *Chunk, u32 Offset)
{
  u8 Const = Chunk->Code[Offset + 1];
  printf("%-16s %4d '", Name, Const);
  ValuePrint(Chunk->Consts.Values[Const]);
  printf("'\n");
  return (Offset + 2); //NOTE:Current Offest + 2Byte offset of this current instruction yield the next insturction location after this.
}
fn u32 DbgDissasembleInstruction(chunk *Chunk, u32 Offset)
{
  printf("%04d ", Offset);
  if((Offset>0) && (Chunk->Lines[Offset] == Chunk->Lines[Offset - 1]))
  {
    printf("   | ");
  }
  else
  {
    printf("%4d ", Chunk->Lines[Offset]);
  }
  u8 Instruction = Chunk->Code[Offset];
  switch(Instruction)
  {
    case Op_Return:
    {
      return DbgSimpleInstruction("OP_RETURN", Offset);
    }break;
    case Op_Const:
    {
      return DbgConstantInstruction("OP_CONSTANT", Chunk, Offset);
    }break;
    default:
    {
      printf("unknown instruction opcode whatever bitch %d\n", Instruction);
      return Offset + 1;
    }break;
  }
}
fn void DbgDissasembleChunk(chunk *Chunk, u8 *Name)
{
  printf("== %s ==\n", Name);
  for(u32 Offset = 0; Offset < Chunk->Count;)
    Offset = DbgDissasembleInstruction(Chunk, Offset);
  return;
}
#endif //DEBUG_H
