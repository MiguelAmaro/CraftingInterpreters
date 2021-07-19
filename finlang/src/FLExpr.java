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
    Binary(FLExpr left, FLToken operator, FLExpr right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitBinaryFLExpr(this);
    }

    final FLExpr left;
    final FLToken operator;
    final FLExpr right;
  }
  static class Grouping extends FLExpr {
    Grouping(FLExpr expression) {
      this.expression = expression;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitGroupingFLExpr(this);
    }

    final FLExpr expression;
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
    Unary(FLToken operator, FLExpr right) {
      this.operator = operator;
      this.right = right;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
      return visitor.visitUnaryFLExpr(this);
    }

    final FLToken operator;
    final FLExpr right;
  }

  abstract <R> R accept(Visitor<R> visitor);
}
