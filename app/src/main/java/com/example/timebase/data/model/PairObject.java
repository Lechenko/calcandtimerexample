package com.example.timebase.data.model;

import java.util.Objects;

import io.reactivex.Observable;

/**
 * L -> L -> check Operator String oper - number, R -> is number doublev boolean
 * R -> L -> group number - oper , R -> global value
 * @param <L>
 * @param <R>
 */
public class PairObject<L, R> {
    private L left;
    private R right;

    public PairObject() {
    }

    public PairObject(L left, R second) {
        this.left = left;
        this.right = second;
    }

    public PairObject(PairObject<L,R> pair) {
        this.left = pair.getLeft();
        this.right = pair.getRight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PairObject<?, ?> that = (PairObject<?, ?>) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public String toString() {
        return "PairObject{" +
                "left=" + left +
                ", right=" + right +
                '}';
    }

    public L getLeft() {
        return left;
    }

    public void setLeft(L left) {
        this.left = left;
    }

    public R getRight() {
        return right;
    }

    public void setRight(R right) {
        this.right = right;
    }
}
