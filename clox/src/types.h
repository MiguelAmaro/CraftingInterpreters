#ifndef TYPES_H
#define TYPES_H

#define fdecl
#define fn
#define global
#define internal static
#define Assert(expression) if(!(expression)){ __debugbreak(); } while (0)
#define DebugTraceExec

#include <stdarg.h>
#include <stdint.h>
#include <float.h>

typedef   uint8_t  u8;
typedef  uint16_t u16;
typedef  uint32_t u32;
typedef  uint64_t u64;
typedef    int8_t  s8;
typedef   int16_t s16;
typedef   int32_t s32;
typedef   int64_t s64;
typedef   uint8_t  b8;
typedef  uint16_t b16;
typedef  uint32_t b32;
typedef  uint64_t b64;
typedef     float f32;
typedef    double f64;

#define F32Max FLT_MAX
#define U32Max UINT32_MAX
#define U64Max UINT64_MAX
#define S32Max INT32_MAX
#define S64Max INT64_MAX

#endif //TYPES_H
