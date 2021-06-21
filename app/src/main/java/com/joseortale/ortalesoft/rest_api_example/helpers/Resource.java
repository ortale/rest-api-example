package com.joseortale.ortalesoft.rest_api_example.helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Resource<T> {
    @Nullable
    public Status status;
    @Nullable
    public T data;
    @Nullable
    public List<T> dataList;
    @Nullable
    public String message;

    private Resource(@NonNull Status status, @NonNull T data) {
        this.data = data;
    }

    private Resource(@NonNull Status status, @NonNull String message) {
        this.status = status;
        this.message = message;
    }

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    private Resource(@NonNull Status status, @Nullable List<T> dataList) {
        this.status = status;
        this.dataList = dataList;
    }

    public static <T> Resource<T> successWithList(@NonNull List<T> data) {
        return new Resource<>(Status.SUCCESS, data);
    }

    public static <T> Resource<T> successWithObject(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data);
    }

    public static <T> Resource<T> error(@NotNull String msg) {
        return new Resource<>(Status.ERROR, msg);
    }

    public enum Status {SUCCESS, ERROR}
}
