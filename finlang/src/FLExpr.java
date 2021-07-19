package com.finlang.lang;

import java.util.List;

abstract class FLExpr {
  interface Visitor<R> {
    R visitBinaryFLExpr(Binary flexpr);
    R visitGroupingFLExpr(Grouping flexpr);
    R visitLiteralFLExpr(Literal flexpr);
    R visitUnaryFLExpr(Unary flexpr);
  }
  static class Binary extends FLExpr {
    Binary(Expr left, Token operator, Expr right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBinaryFLExpr(this);
    }

    final Expr left;
    final Token operator;
    final Expr right;
  }
  static class Grouping extends FLExpr {
    Grouping(Expr expression) {
      this.expression = expression;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitGroupingFLExpr(this);
    }

    final Expr expression;
  }
  static class Literal extends FLExpr {
    Literal(Object value) {
      this.value = value;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitLiteralFLExpr(this);
    }

    final Object value;
  }
  static class Unary extends FLExpr {
    Unary(Token operator, Expr right) {
      this.operator = operator;
      this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitUnaryFLExpr(this);
    }

    final Token operator;
    final Expr right;
  }

  abstract <R> R accept(Visitor<R> visitor);
}
