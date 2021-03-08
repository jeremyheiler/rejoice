package net.jloop.rejoice;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

// Inspired by https://doc.rust-lang.org/std/result/enum.Result.html

public interface Result<T, E> extends Iterable<T> {

    static <T, E> Result<T, E> ok(T value) {
        return new Ok<>(value);
    }

    static <T, E> Result<T, E> error(E error) {
        return new Error<>(error);
    }

    boolean isOk();

    boolean isError();

    Optional<T> value();

    Optional<E> error();

    <U> Result<U, E> map(Function<T, U> function);

    <F> Result<T, F> mapError(Function<E, F> function);

    <U> Result<U, E> and(Result<U, E> result);

    <U> Result<U, E> andThen(Function<T, Result<U, E>> function);

    <F> Result<T, F> or(Result<T, F> result);

    <F> Result<T, F> orElse(Function<E, Result<T, F>> function);

    T unwrap();

    T unwrapOr(T other);

    T unwrapOrElse(Function<E, T> function);

    //T unwrapOrDefault(T other);

    E unwrapError();

    T expect(String message);

    E expectError(String message);

    class Ok<T, E> implements Result<T, E> {

        private final T value;

        public Ok(T value) {
            this.value = value;
        }

        @Override
        public boolean isOk() {
            return true;
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public Optional<T> value() {
            return Optional.of(value);
        }

        @Override
        public Optional<E> error() {
            return Optional.empty();
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {

                private boolean done = false;

                @Override
                public boolean hasNext() {
                    return !done;
                }

                @Override
                public T next() {
                    if (done) {
                        throw new NoSuchElementException();
                    } else {
                        done = true;
                        return value;
                    }
                }
            };
        }

        @Override
        public <U> Result<U, E> map(Function<T, U> function) {
            return new Ok<>(function.apply(value));
        }

        @Override
        public <F> Result<T, F> mapError(Function<E, F> function) {
            throw new NoSuchElementException("No error present");
        }

        @Override
        public <U> Result<U, E> and(Result<U, E> result) {
            return result;
        }

        @Override
        public <U> Result<U, E> andThen(Function<T, Result<U, E>> function) {
            return function.apply(value);
        }

        @Override
        public <F> Result<T, F> or(Result<T, F> result) {
            return new Ok<>(value);
        }

        @Override
        public <F> Result<T, F> orElse(Function<E, Result<T, F>> function) {
            return new Ok<>(value);
        }

        @Override
        public T unwrap() {
            return value;
        }

        @Override
        public T unwrapOr(T other) {
            return value;
        }

        @Override
        public T unwrapOrElse(Function<E, T> function) {
            return value;
        }

        @Override
        public E unwrapError() {
            throw new NoSuchElementException("No error present");
        }

        @Override
        public T expect(String message) {
            return value;
        }

        @Override
        public E expectError(String message) {
            throw new NoSuchElementException("No error present");
        }
    }

    class Error<T, E> implements Result<T, E> {

        private final E error;

        public Error(E error) {
            this.error = error;
        }

        @Override
        public boolean isOk() {
            return false;
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public Optional<T> value() {
            return Optional.empty();
        }

        @Override
        public Optional<E> error() {
            return Optional.of(error);
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {

                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public T next() {
                    throw new NoSuchElementException();
                }
            };
        }

        @Override
        public <U> Result<U, E> map(Function<T, U> function) {
            throw new NoSuchElementException("No value present");
        }

        @Override
        public <F> Result<T, F> mapError(Function<E, F> function) {
            return new Error<>(function.apply(error));
        }

        @Override
        public <U> Result<U, E> and(Result<U, E> result) {
            return new Error<>(error);
        }

        @Override
        public <U> Result<U, E> andThen(Function<T, Result<U, E>> function) {
            return new Error<>(error);
        }

        @Override
        public <F> Result<T, F> or(Result<T, F> result) {
            return result;
        }

        @Override
        public <F> Result<T, F> orElse(Function<E, Result<T, F>> function) {
            return function.apply(error);
        }

        @Override
        public T unwrap() {
            throw new NoSuchElementException("No value present");
        }

        @Override
        public T unwrapOr(T other) {
            return other;
        }

        @Override
        public T unwrapOrElse(Function<E, T> function) {
            return function.apply(error);
        }

        @Override
        public E unwrapError() {
            return error;
        }

        @Override
        public T expect(String message) {
            throw new NoSuchElementException(message + ":" + error.toString());
        }

        @Override
        public E expectError(String message) {
            return error;
        }
    }
}
