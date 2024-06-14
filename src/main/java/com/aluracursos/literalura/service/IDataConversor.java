package com.aluracursos.literalura.service;

public interface IDataConversor {
    <T> T getData(String json, Class<T> tClass);
}
