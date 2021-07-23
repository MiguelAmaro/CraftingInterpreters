package com.finlang.lang;

import java.util.List;

abstract class FLStmt
{
  interface Visitor<R>
{
    R visitBlockFLStmt(Block flstmt);
    R visitExpressionFLStmt(Expression flstmt);
    R visitIfFLStmt(If flstmt);
    R visitPrintFLStmt(Print flstmt);
    R visitVarFLStmt(Var flstmt);
    R visitWhileFLStmt(While flstmt);
  }

  static class Block extends FLStmt
{
    Block(List<FLStmt> statements)
{
      this.statements = statements;
    }


    @Override
    <R> R accept(Visitor<R> visitor)
{
      return visitor.visitBlockFLStmt(this);
    }

    final List<FLStmt> statements;
  }

  static class Expression extends FLStmt
{
    Expression(FLExpr expression)
{
      this.expression = expression;
    }


    @Override
    <R> R accept(Visitor<R> visitor)
{
      return visitor.visitExpressionFLStmt(this);
    }

    final FLExpr expression;
  }

  static class If extends FLStmt
{
    If(FLExpr condition, FLStmt thenBranch, FLStmt elseBranch)
{
      this.condition = condition;
      this.thenBranch = thenBranch;
      this.elseBranch = elseBranch;
    }


    @Override
    <R> R accept(Visitor<R> visitor)
{
      return visitor.visitIfFLStmt(this);
    }

    final FLExpr condition;
    final FLStmt thenBranch;
    final FLStmt elseBranch;
  }

  static class Print extends FLStmt
{
    Print(FLExpr expression)
{
      this.expression = expression;
    }


    @Override
    <R> R accept(Visitor<R> visitor)
{
      return visitor.visitPrintFLStmt(this);
    }

    final FLExpr expression;
  }

  static class Var extends FLStmt
{
    Var(FLToken name, FLExpr initializer)
{
      this.name = name;
      this.initializer = initializer;
    }


    @Override
    <R> R accept(Visitor<R> visitor)
{
      return visitor.visitVarFLStmt(this);
    }

    final FLToken name;
    final FLExpr initializer;
  }

  static class While extends FLStmt
{
    While(FLExpr condition, FLStmt body)
{
      this.condition = condition;
      this.body = body;
    }


    @Override
    <R> R accept(Visitor<R> visitor)
{
      return visitor.visitWhileFLStmt(this);
    }

    final FLExpr condition;
    final FLStmt body;
  }


  abstract <R> R accept(Visitor<R> visitor);
}

