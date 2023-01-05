package ru.liner.facerapp.engine.state;

/**
 * @author : "Line'R"
 * @mailto : serinity320@mail.com
 * @created : 04.01.2023, среда
 **/
public interface State<T> {
    T get();

    void set(T t);
}